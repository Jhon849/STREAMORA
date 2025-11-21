package com.streamora.backend.chat;

import com.streamora.backend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "moderation_logs")
public class ModerationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String inputMessage;

    private String action; // "ALLOWED", "REWRITTEN", "BLOCKED"

    @Column(columnDefinition = "TEXT")
    private String aiExplanation;

    private int toxicityScore; // 0-100

    private LocalDateTime createdAt;
}
