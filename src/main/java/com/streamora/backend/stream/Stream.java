package com.streamora.backend.stream;

import com.streamora.backend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stream {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String streamKey;

    private boolean isLive;

    private LocalDateTime startedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
