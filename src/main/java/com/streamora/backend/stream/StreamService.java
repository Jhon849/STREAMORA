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
    //   START STREAM
    // ========================
    public Stream startStream(Long userId, String title, String description) {

        User user = userService.getUser(userId);

        // If user.createdAt is null → fix it
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }

        // Check if the user already has a live stream
        Stream active = streamRepository.findByUserIdAndLiveTrue(userId).orElse(null);

        if (active != null) {
            active.setTitle(title);
            active.setDescription(description);

            if (active.getCreatedAt() == null) {
                active.setCreatedAt(LocalDateTime.now());
            }

            return streamRepository.save(active);
        }

        // Create a new stream
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
    //   STOP STREAM
    // ========================
    public Stream stopStream(Long userId) {

        Stream stream = streamRepository.findByUserIdAndLiveTrue(userId)
                .orElseThrow(() -> new RuntimeException("User is not currently live"));

        stream.setLive(false);

        return streamRepository.save(stream);
    }

    // ========================
    //   GET LIVE STREAMS
    // ========================
    public List<Stream> getActiveStreams() {
        List<Stream> streams = streamRepository.findByLiveTrue();

        for (Stream s : streams) {

            // Fix stream createdAt null
            if (s.getCreatedAt() == null) {
                s.setCreatedAt(LocalDateTime.now());
            }

            // Fix user createdAt null
            if (s.getUser() != null && s.getUser().getCreatedAt() == null) {
                s.getUser().setCreatedAt(LocalDateTime.now());
            }
        }

        return streams;
    }
}







