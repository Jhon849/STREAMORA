package com.streamora.backend.email;

import lombok.Data;

@Data
public class EmailSendResponse {
    private String id;
    private String successMessage;

    public EmailSendResponse(String id, String successMessage) {
        this.id = id;
        this.successMessage = successMessage;
    }
}
