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

    public Stream startStream(Long userId, String title, String description) {

        User user = userService.getUser(userId);

        // Buscar si ya está online
        Stream active = streamRepository
                .findByUserIdAndStatus(userId, StreamStatus.ONLINE)
                .orElse(null);

        // Si ya estaba online, actualiza título y descripción
        if (active != null) {
            active.setTitle(title);
            active.setDescription(description);
            return streamRepository.save(active);
        }

        // Crear stream nuevo
        Stream stream = Stream.builder()
                .title(title)
                .description(description)
                .streamKey(UUID.randomUUID().toString())
                .status(StreamStatus.ONLINE)
                .startedAt(LocalDateTime.now())
                .user(user)
                .build();

        return streamRepository.save(stream);
    }

    public Stream stopStream(Long userId) {

        Stream stream = streamRepository
                .findByUserIdAndStatus(userId, StreamStatus.ONLINE)
                .orElseThrow(() -> new RuntimeException("User is not currently live"));

        stream.setStatus(StreamStatus.OFFLINE);
        stream.setEndedAt(LocalDateTime.now());

        return streamRepository.save(stream);
    }

    public List<Stream> getActiveStreams() {
        return streamRepository.findByStatus(StreamStatus.ONLINE);
    }
}





