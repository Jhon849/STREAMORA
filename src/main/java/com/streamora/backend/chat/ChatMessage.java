package com.streamora.backend.chat;

import com.streamora.backend.user.User;
import com.streamora.backend.stream.Stream;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Stream stream;

    private String originalText;

    private String moderatedText;

    private boolean blocked;

    private LocalDateTime createdAt;
}
