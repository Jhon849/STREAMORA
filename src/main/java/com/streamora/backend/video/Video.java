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

    // Getters & setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public int getViews() { return views; }

    public void setViews(int views) { this.views = views; }

    public int getLikes() { return likes; }

    public void setLikes(int likes) { this.likes = likes; }

    public int getDislikes() { return dislikes; }

    public void setDislikes(int dislikes) { this.dislikes = dislikes; }

    public boolean isPublic() { return isPublic; }

    public void setPublic(boolean aPublic) { isPublic = aPublic; }

    public User getOwner() { return owner; }

    public void setOwner(User owner) { this.owner = owner; }
}





