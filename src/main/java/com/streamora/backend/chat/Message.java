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

    private Long streamId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;

    private String content;

    // 🔥 Nuevo: Rol del usuario en el chat (streamer, mod, vip, founder, etc.)
    @Enumerated(EnumType.STRING)
    private ChatRole role;

    private String badge; // mod, sub, vip, founder

    private String color; // color del usuario

    private LocalDateTime timestamp;

    private boolean moderated; // true si IA lo eliminó
}

