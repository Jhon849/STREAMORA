package com.streamora.backend.chat;

import com.streamora.backend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "chat_messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔥 FIX: Evita que Hibernate cree dos columnas (stream_id y streamId)
    @Column(name = "stream_id", nullable = false)
    private Long streamId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;

    private String content; // texto final mostrado al público

    // 🔥 Nuevo: texto original antes de la moderación IA
    @Column(columnDefinition = "TEXT")
    private String originalContent;

    // 🔥 Nuevo: razón de moderación (explicación IA)
    @Column(columnDefinition = "TEXT")
    private String moderationReason;

    // 🔥 Nuevo: puntuación de toxicidad (0-100)
    private Integer toxicityScore;

    // 🔥 Ya existía: si IA lo eliminó o alteró
    private boolean moderated;

    // 🔥 Ya existía: Roles
    @Enumerated(EnumType.STRING)
    private ChatRole role;

    private String badge;

    private String color;

    private LocalDateTime timestamp;
}



