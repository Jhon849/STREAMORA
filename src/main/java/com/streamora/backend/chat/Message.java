package com.streamora.backend.chat;

import com.streamora.backend.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    private Long roomId;
}
