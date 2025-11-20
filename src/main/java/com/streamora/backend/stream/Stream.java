package com.streamora.backend.stream;

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

    // ESTE CAMPO ES EL QUE SPRING NO ENCONTRABA
    @Column(name = "live")
    private boolean live;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    private String category;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}




