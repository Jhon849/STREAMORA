package com.streamora.backend.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/{streamId}/send")
    public void sendMessage(
            @PathVariable Long streamId,
            @RequestParam Long userId,
            @RequestParam String message
    ) {
        chatService.sendMessage(streamId, userId, message);
    }

    @GetMapping("/{streamId}")
    public List<Message> getMessages(@PathVariable Long streamId) {
        return chatService.getMessages(streamId);
    }
}


