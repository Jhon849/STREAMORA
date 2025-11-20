package com.streamora.backend.moderation;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "moderation_logs")
public class ModerationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long roomId;

    private String message;
    private String result;   // allowed | blocked
    private String category; // hate, sexual, violence ...

    private String createdAt = java.time.Instant.now().toString();
}

