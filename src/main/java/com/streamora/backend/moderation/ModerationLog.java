package com.streamora.backend.moderation;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "moderation_logs")
public class ModerationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long streamId;
    private String userId;

    @Column(columnDefinition = "TEXT")
    private String originalMessage;

    private String reason;

    private LocalDateTime timestamp;
}
