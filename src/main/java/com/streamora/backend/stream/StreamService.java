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

    public Stream startStream(String userId, String title, String description) {

        User user = userService.getUser(userId);

        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }

        Stream active = streamRepository.findByUserIdAndLiveTrue(userId).orElse(null);

        if (active != null) {
            active.setTitle(title);
            active.setDescription(description);

            if (active.getCreatedAt() == null) {
                active.setCreatedAt(LocalDateTime.now());
            }

            return streamRepository.save(active);
        }

        Stream stream = Stream.builder()
                .title(title)
                .description(description)
                .streamKey(UUID.randomUUID().toString())
                .live(true)
                .createdAt(LocalDateTime.now())
                .viewerCount(0)
                .thumbnailUrl("https://res.cloudinary.com/demo/image/upload/sample.jpg")
                .category("GAMING")
                .user(user)
                .build();

        return streamRepository.save(stream);
    }

    public Stream stopStream(String userId) {
        Stream stream = streamRepository.findByUserIdAndLiveTrue(userId)
                .orElseThrow(() -> new RuntimeException("User is not currently live"));

        stream.setLive(false);
        stream.setViewerCount(0);

        return streamRepository.save(stream);
    }

    public List<Stream> getActiveStreams() {
        List<Stream> streams = streamRepository.findByLiveTrue();

        streams.forEach(s -> {
            if (s.getCreatedAt() == null) s.setCreatedAt(LocalDateTime.now());
            if (s.getUser() != null && s.getUser().getCreatedAt()==null)
                s.getUser().setCreatedAt(LocalDateTime.now());
            if (s.getViewerCount() < 0) s.setViewerCount(0);
        });

        return streams;
    }

    public Stream addViewer(Long streamId) {
        Stream stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new RuntimeException("Stream not found"));
        stream.setViewerCount(stream.getViewerCount() + 1);
        return streamRepository.save(stream);
    }

    public Stream removeViewer(Long streamId) {
        Stream stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new RuntimeException("Stream not found"));
        stream.setViewerCount(Math.max(0, stream.getViewerCount() - 1));
        return streamRepository.save(stream);
    }

    // 🔥 Nuevo método necesario
    public Stream getStreamById(Long id) {
        return streamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stream not found"));
    }
}









