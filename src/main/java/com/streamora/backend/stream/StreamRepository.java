package com.streamora.backend.stream;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StreamRepository extends JpaRepository<Stream, Long> {

    Optional<Stream> findByUserId(Long userId);

    Optional<Stream> findByStreamKey(String streamKey);

    List<Stream> findByStatus(StreamStatus status);

    Optional<Stream> findByUserIdAndStatus(Long userId, StreamStatus status);
}





