package com.streamora.backend.stream;

import com.streamora.backend.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stream_config")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StreamConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String streamKey;

    private String title;

    private String category;

    private boolean live;
}
