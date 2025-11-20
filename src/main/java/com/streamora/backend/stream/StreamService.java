package com.streamora.backend.stream;

import com.streamora.backend.user.User;
import com.streamora.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StreamService {

    private final StreamRepository streamRepository;
    private final UserService userService;

    // ========================
    //   INICIAR STREAM
    // ========================
    public Stream startStream(Long userId, String title, String description) {

        User user = userService.getUser(userId);

        // Buscar si ya tiene un stream activo
        Stream active = streamRepository.findByUserIdAndLiveTrue(userId).orElse(null);

        if (active != null) {
            active.setTitle(title);
            active.setDescription(description);
            return streamRepository.save(active);
        }

        // Crear nuevo stream
        Stream stream = Stream.builder()
                .title(title)
                .description(description)
                .streamKey(UUID.randomUUID().toString())
                .live(true)
                .createdAt(LocalDateTime.now())
                .thumbnailUrl("https://res.cloudinary.com/demo/image/upload/sample.jpg")
                .category("GAMING")
                .user(user)
                .build();

        return streamRepository.save(stream);
    }

    // ========================
    //   DETENER STREAM
    // ========================
    public Stream stopStream(Long userId) {

        Stream stream = streamRepository.findByUserIdAndLiveTrue(userId)
                .orElseThrow(() -> new RuntimeException("User is not currently live"));

        stream.setLive(false);

        return streamRepository.save(stream);
    }

    // ========================
    //   OBTENER STREAMS EN VIVO
    // ========================
    public List<Stream> getActiveStreams() {
        return streamRepository.findByLiveTrue();
    }
}






