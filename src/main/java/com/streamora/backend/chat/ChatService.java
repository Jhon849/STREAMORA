package com.streamora.backend.chat;

import com.streamora.backend.moderation.IAModerationService;
import com.streamora.backend.moderation.ModerationLog;
import com.streamora.backend.moderation.ModerationLogRepository;
import com.streamora.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository messageRepository;
    private final ModerationLogRepository logRepository;
    private final IAModerationService moderationService;
    private final UserService userService;
    private final SimpMessagingTemplate messaging;

    public void sendMessage(Long streamId, String userId, String content) {

        // IA revisa el mensaje
        String moderationResult = moderationService.checkMessage(content);

        if (moderationResult != null) {
            // mensaje eliminado
            logRepository.save(
                    ModerationLog.builder()
                            .streamId(streamId)
                            .userId(userId)
                            .originalMessage(content)
                            .reason(moderationResult)
                            .timestamp(LocalDateTime.now())
                            .build()
            );
            return; // ❌ no enviar el mensaje
        }

        var user = userService.getUser(userId);

        Message msg = Message.builder()
                .streamId(streamId)
                .sender(user)
                .content(content)
                .badge("sub")
                .color("#8b5cf6")
                .timestamp(LocalDateTime.now())
                .moderated(false)
                .build();

        messageRepository.save(msg);

        // enviar al chat WS
        messaging.convertAndSend("/topic/chat/" + streamId, msg);
    }

    public List<Message> getMessages(Long streamId) {
        return messageRepository.findByStreamIdOrderByTimestampAsc(streamId);
    }
}


