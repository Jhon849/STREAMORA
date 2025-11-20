package com.streamora.backend.email;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailSendRequest {
    private String from;
    private String to;
    private String subject;
    private String html;
}
