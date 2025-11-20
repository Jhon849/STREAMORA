package com.streamora.backend.chat;

import lombok.Data;

@Data
public class ChatMessageDto {
    private Long userId;
    private Long roomId;
    private String content;
}

