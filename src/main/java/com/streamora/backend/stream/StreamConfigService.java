package com.streamora.backend.stream;

import com.streamora.backend.user.User;
import com.streamora.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StreamConfigService {

    private final StreamConfigRepository repository;
    private final UserRepository userRepository;

    public StreamConfig createOrGetConfig(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return repository.findByUser(user).orElseGet(() -> {
            StreamConfig cfg = StreamConfig.builder()
                    .user(user)
                    .streamKey(UUID.randomUUID().toString().replace("-", "").substring(0, 25))
                    .title("Nuevo directo")
                    .category("General")
                    .isLive(false)
                    .ingestUrl("rtmp://stream.streamora.space/live")
                    .playbackUrl("https://stream.streamora.space/hls/" + userId + ".m3u8")
                    .build();

            return repository.save(cfg);
        });
    }

    public StreamConfig updateTitle(Long userId, String title) {
        StreamConfig cfg = createOrGetConfig(userId);
        cfg.setTitle(title);
        return repository.save(cfg);
    }

    public StreamConfig updateCategory(Long userId, String category) {
        StreamConfig cfg = createOrGetConfig(userId);
        cfg.setCategory(category);
        return repository.save(cfg);
    }
}

