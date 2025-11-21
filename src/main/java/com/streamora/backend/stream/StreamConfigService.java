package com.streamora.backend.stream;

import com.streamora.backend.user.User;
import com.streamora.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StreamConfigService {

    private final StreamConfigRepository repository;
    private final UserService userService;

    public StreamConfig getMyConfig(String userId) {
        User user = userService.getUser(userId);

        return repository.findByUser(user)
                .orElseGet(() -> createDefaultConfig(user));
    }

    private StreamConfig createDefaultConfig(User user) {
        StreamConfig config = StreamConfig.builder()
                .user(user)
                .streamKey(UUID.randomUUID().toString())
                .title("Mi Stream")
                .description("Bienvenido a mi transmisión")
                .category("General")
                .thumbnailUrl(null)
                .build();

        return repository.save(config);
    }

    public StreamConfig updateConfig(String userId, StreamConfig request) {
        StreamConfig config = getMyConfig(userId);

        // Evitar valores nulos
        if (request.getTitle() != null) 
            config.setTitle(request.getTitle());

        if (request.getDescription() != null)
            config.setDescription(request.getDescription());

        if (request.getCategory() != null)
            config.setCategory(request.getCategory());

        if (request.getThumbnailUrl() != null)
            config.setThumbnailUrl(request.getThumbnailUrl());

        return repository.save(config);
    }
}

