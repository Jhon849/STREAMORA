package com.streamora.backend.stream;

import com.streamora.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StreamConfigRepository extends JpaRepository<StreamConfig, Long> {
    Optional<StreamConfig> findByUser(User user);
    Optional<StreamConfig> findByStreamKey(String streamKey);
}
