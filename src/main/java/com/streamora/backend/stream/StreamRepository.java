package com.streamora.backend.stream;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface StreamRepository extends JpaRepository<Stream, Long> {

    Optional<Stream> findByUserId(Long userId);

    Optional<Stream> findByStreamKey(String streamKey);

    // replaced the old boolean method
    List<Stream> findByStatus(StreamStatus status);

    // useful helper: check if user has an active stream
    Optional<Stream> findByUserIdAndStatus(Long userId, StreamStatus status);
}


