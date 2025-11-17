package com.streamora.backend.stream;

import com.streamora.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StreamRepository extends JpaRepository<Stream, Long> {

    Optional<Stream> findByUser(User user);

    Optional<Stream> findByStreamKey(String streamKey);

    boolean existsByStreamKey(String streamKey);
}
