package com.streamora.backend.moderation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModerationResult {
    private boolean flagged;
    private String categories;
}
