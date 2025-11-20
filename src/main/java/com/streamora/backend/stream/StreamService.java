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

        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }

        // Check if existing live stream
        Stream active = streamRepository.findByUserIdAndLiveTrue(userId).orElse(null);

        if (active != null) {
            active.setTitle(title);
            active.setDescription(description);

            if (active.getCreatedAt() == null) {
                active.setCreatedAt(LocalDateTime.now());
            }

            return streamRepository.save(active);
        }

        // New stream
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

    // ========================
    //   STOP STREAM
    // ========================
    public Stream stopStream(Long userId) {

        Stream stream = streamRepository.findByUserIdAndLiveTrue(userId)
                .orElseThrow(() -> new RuntimeException("User is not currently live"));

        stream.setLive(false);
        stream.setViewerCount(0); // reset viewers

        return streamRepository.save(stream);
    }

    // ========================
    //   GET LIVE STREAMS
    // ========================
    public List<Stream> getActiveStreams() {
        List<Stream> streams = streamRepository.findByLiveTrue();

        for (Stream s : streams) {

            if (s.getCreatedAt() == null) {
                s.setCreatedAt(LocalDateTime.now());
            }

            if (s.getUser() != null && s.getUser().getCreatedAt() == null) {
                s.getUser().setCreatedAt(LocalDateTime.now());
            }

            if (s.getViewerCount() < 0) {
                s.setViewerCount(0);
            }
        }

        return streams;
    }

    // ========================
    //   ADD VIEWER
    // ========================
    public Stream addViewer(Long streamId) {
        Stream stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new RuntimeException("Stream not found"));

        stream.setViewerCount(stream.getViewerCount() + 1);

        return streamRepository.save(stream);
    }

    // ========================
    //   REMOVE VIEWER
    // ========================
    public Stream removeViewer(Long streamId) {
        Stream stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new RuntimeException("Stream not found"));

        int newCount = Math.max(0, stream.getViewerCount() - 1);
        stream.setViewerCount(newCount);

        return streamRepository.save(stream);
    }
}







