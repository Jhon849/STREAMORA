package com.streamora.backend.stream;

import com.streamora.backend.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "streams")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stream {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String category;

    private String thumbnailUrl;

    private String streamKey; // Unique key for OBS / RTMP

    private boolean live;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
