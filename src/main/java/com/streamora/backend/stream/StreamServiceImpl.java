package com.streamora.backend.stream;

import com.streamora.backend.stream.dto.StartStreamRequest;
import com.streamora.backend.user.User;
import com.streamora.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StreamServiceImpl implements StreamService {

    private final StreamRepository streamRepository;
    private final UserRepository userRepository;

    @Override
    public Stream startStream(String userId, StartStreamRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Buscar si ya hay un stream activo
        Stream existing = streamRepository.findByUserIdAndLiveTrue(userId);

        if (existing != null) {
            existing.setTitle(request.getTitle());
            existing.setDescription(request.getDescription());
            existing.setCategory(request.getCategory());
            existing.setThumbnailUrl(request.getThumbnailUrl());
            existing.setLive(true);
            existing.setStatus(StreamStatus.LIVE);
            return streamRepository.save(existing);
        }

        // Crear uno nuevo
        Stream stream = Stream.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .thumbnailUrl(request.getThumbnailUrl())
                .streamKey(UUID.randomUUID().toString())
                .viewerCount(0)
                .live(true)
                .status(StreamStatus.LIVE)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        return streamRepository.save(stream);
    }

    @Override
    public Stream stopStream(String userId) {

        Stream stream = streamRepository.findByUserIdAndLiveTrue(userId);

        if (stream == null) {
            throw new RuntimeException("No active stream found");
        }

        stream.setLive(false);
        stream.setViewerCount(0);
        stream.setStatus(StreamStatus.ENDED);

        return streamRepository.save(stream);
    }

    @Override
    public Stream getStreamById(Long id) {
        return streamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stream not found"));
    }

    @Override
    public Stream addViewer(Long id) {
        Stream stream = getStreamById(id);
        stream.setViewerCount(stream.getViewerCount() + 1);
        return streamRepository.save(stream);
    }

    @Override
    public Stream removeViewer(Long id) {
        Stream stream = getStreamById(id);

        if (stream.getViewerCount() > 0) {
            stream.setViewerCount(stream.getViewerCount() - 1);
        }

        return streamRepository.save(stream);
    }

    @Override
    public List<Stream> getActiveStreams() {
        return streamRepository.findByLiveTrue();
    }
}

