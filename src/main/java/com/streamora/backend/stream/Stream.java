package com.streamora.backend.stream;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.streamora.backend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "streams")
public class Stream {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(name = "stream_key")
    private String streamKey;

    @Column(name = "viewer_count")
    private int viewerCount = 0;

    private boolean live;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StreamStatus status;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    private String category;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}







