package com.streamora.backend.video;

import com.streamora.backend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String url;

    private String thumbnail;

    private String category;

    private Long views;

    private Long likes;

    private Long dislikes;

    private boolean isPublic;

    private LocalDateTime createdAt;

    @ElementCollection
    private List<String> tags;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
}
