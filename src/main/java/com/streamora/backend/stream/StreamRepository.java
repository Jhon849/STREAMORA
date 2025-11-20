package com.streamora.backend.stream;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StreamRepository extends JpaRepository<Stream, Long> {

    Optional<Stream> findByUserIdAndLiveTrue(Long userId);

    List<Stream> findByLiveTrue();
}









