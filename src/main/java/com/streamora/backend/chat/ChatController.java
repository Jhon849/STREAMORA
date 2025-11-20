package com.streamora.backend.chat;

import com.streamora.backend.moderation.IAModerationService;
import com.streamora.backend.moderation.ModerationLog;
import com.streamora.backend.moderation.ModerationLogRepository;
import com.streamora.backend.moderation.ModerationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final IAModerationService moderationService;
    private final ModerationLogRepository moderationRepo;


    @MessageMapping("/send")
    public void sendMessage(ChatMessageDto dto) {

        // Step 1: AI moderation
        ModerationResult result = moderationService.moderateText(dto.getContent());

        if (result.isFlagged()) {

            // Step 2: save the moderation incident
            ModerationLog log = new ModerationLog();
            log.setUserId(dto.getUserId());
            log.setRoomId(dto.getRoomId());
            log.setMessage(dto.getContent());
            log.setResult("blocked");
            log.setCategory(result.getCategories());
            moderationRepo.save(log);

            // Step 3: send warning only to the user
            messagingTemplate.convertAndSend(
                    "/topic/room/" + dto.getRoomId() + "/user/" + dto.getUserId(),
                    "⚠️ Your message was blocked by AI moderation."
            );

            return;
        }

        // Step 4: allowed → normal flow
        Message saved = chatService.saveMessage(dto.getUserId(), dto.getRoomId(), dto.getContent());
        messagingTemplate.convertAndSend("/topic/room/" + dto.getRoomId(), saved);
    }


    @GetMapping("/room/{roomId}")
    public List<Message> getMessages(@PathVariable Long roomId) {
        return chatService.getRoomMessages(roomId);
    }
}
