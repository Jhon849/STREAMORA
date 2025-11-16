package com.streamora.backend.stream;

import com.streamora.backend.user.User;
import com.streamora.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class StreamConfigService {

    private final StreamConfigRepository streamConfigRepository;
    private final UserRepository userRepository;

    private String generateStreamKey() {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public StreamConfig initStreamConfig(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        return streamConfigRepository.findByUser(user)
                .orElseGet(() -> streamConfigRepository.save(
                        StreamConfig.builder()
                                .user(user)
                                .streamKey(generateStreamKey())
                                .title("My Stream")
                                .category("Just Chatting")
                                .live(false)
                                .build()
                ));
    }

    public StreamConfig updateStream(Long userId, String title, String category) {
        StreamConfig config = initStreamConfig(userId);
        config.setTitle(title);
        config.setCategory(category);
        return streamConfigRepository.save(config);
    }

    public StreamConfig regenerateStreamKey(Long userId) {
        StreamConfig config = initStreamConfig(userId);
        config.setStreamKey(generateStreamKey());
        return streamConfigRepository.save(config);
    }

    public StreamConfig setLiveState(Long userId, boolean live) {
        StreamConfig config = initStreamConfig(userId);
        config.setLive(live);
        return streamConfigRepository.save(config);
    }
}
