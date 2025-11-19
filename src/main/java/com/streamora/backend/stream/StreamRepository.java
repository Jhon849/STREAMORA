package com.streamora.backend.stream;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StreamRepository extends JpaRepository<Stream, Long> {

    // Encuentra stream por usuario
    Optional<Stream> findByUserId(Long userId);

    // Encuentra stream por streamKey
    Optional<Stream> findByStreamKey(String streamKey);

    // Devuelve todos los streams que estén en vivo
    List<Stream> findByLiveTrue();

    // Encuentra el stream activo de un usuario
    Optional<Stream> findByUserIdAndLiveTrue(Long userId);
}






