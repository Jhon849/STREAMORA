package com.streamora.backend.stream;

import com.streamora.backend.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stream_configs")
public class StreamConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(nullable = false)
    private String streamKey;

    private String title;

    private String category;

    @Column(nullable = false)
    private boolean isLive;

    private String ingestUrl;   // RTMP ingest
    private String playbackUrl; // HLS playback
}

