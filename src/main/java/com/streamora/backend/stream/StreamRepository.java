package com.streamora.backend.stream;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface StreamRepository extends JpaRepository<Stream, Long> {

    Optional<Stream> findByUserId(Long userId);

    Optional<Stream> findByStreamKey(String streamKey);

    List<Stream> findByIsLiveTrue();
}

