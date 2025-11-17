package com.streamora.backend.stream;

import com.streamora.backend.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "stream_config")
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

    public StreamConfig() {}

    // Getters & setters...
}



