package com.streamora.backend.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModerationResult {

    private boolean allowed;
    private String status;
    private int toxicity;
    private String explanation;
    private String rewrittenText;
}
