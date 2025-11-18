package com.streamora.backend.chat;

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

    @MessageMapping("/send")
    public void sendMessage(ChatMessageDto dto) {
        Message saved = chatService.saveMessage(dto.getUserId(), dto.getRoomId(), dto.getContent());

        messagingTemplate.convertAndSend("/topic/room/" + dto.getRoomId(), saved);
    }

    @GetMapping("/room/{roomId}")
    public List<Message> getMessages(@PathVariable Long roomId) {
        return chatService.getRoomMessages(roomId);
    }
}
