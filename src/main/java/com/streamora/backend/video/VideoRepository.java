package com.streamora.backend.video;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByCategory(String category);

    List<Video> findByTitleContainingIgnoreCase(String title);

    List<Video> findByOwnerId(Long userId);

}
