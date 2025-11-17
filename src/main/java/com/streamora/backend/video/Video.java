package com.streamora.backend.video;

import com.streamora.backend.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String url;

    private LocalDateTime createdAt;

    private int views;
    private int likes;
    private int dislikes;

    private boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Video() {
        this.createdAt = LocalDateTime.now();
        this.isPublic = true;
    }

    // GETTERS & SETTERS
    // igual que antes pero sin Neo4j
}




