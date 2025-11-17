package com.streamora.backend.stream;

import com.streamora.backend.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "streams")
public class StreamConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private boolean live;
    private String streamKey;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public StreamConfig() {
        this.live = false;
    }

    // Getters & setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public boolean isLive() { return live; }

    public void setLive(boolean live) { this.live = live; }

    public String getStreamKey() { return streamKey; }

    public void setStreamKey(String streamKey) { this.streamKey = streamKey; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}




