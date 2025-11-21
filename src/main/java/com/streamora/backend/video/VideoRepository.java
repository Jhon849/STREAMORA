package com.streamora.backend.video;

import com.streamora.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByUser(User user);
}

