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

    private String description; // added for stream info

    private String streamKey;

    // replaced boolean with enum for more clarity
    @Enumerated(EnumType.STRING)
    private StreamStatus status = StreamStatus.OFFLINE;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt; // added for tracking stream duration

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

