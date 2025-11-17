package com.streamora.backend.stream;

import com.streamora.backend.cloud.CloudinaryService;
import com.streamora.backend.stream.dto.CreateStreamRequest;
import com.streamora.backend.stream.dto.StreamResponse;
import com.streamora.backend.user.User;
import com.streamora.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StreamService {

    private final StreamRepository streamRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    private final String RTMP_BASE = "rtmp://your-rtmp-server/live";
    private final String HLS_BASE  = "https://your-rtmp-server/hls/";

    public StreamResponse createStream(Long userId, CreateStreamRequest request) {

        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElse(null);

        Stream stream = Stream.builder()
                .user(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .thumbnailUrl(null)
                .streamKey(StreamKeyGenerator.generate())
                .live(false)
                .createdAt(LocalDateTime.now())
                .build();

        streamRepository.save(stream);

        return mapToResponse(stream);
    }

    public StreamResponse getStreamByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Stream stream = streamRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Stream not found"));

        return mapToResponse(stream);
    }

    public List<StreamResponse> getAllStreams() {
        return streamRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ⭐ Subir thumbnail del stream
    public StreamResponse uploadThumbnail(Long streamId, MultipartFile file) throws IOException {
        Stream stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new RuntimeException("Stream no encontrado"));

        String url = cloudinaryService.upload(file);
        stream.setThumbnailUrl(url);

        streamRepository.save(stream);

        return mapToResponse(stream);
    }

    private StreamResponse mapToResponse(Stream stream) {
        return StreamResponse.builder()
                .id(stream.getId())
                .title(stream.getTitle())
                .description(stream.getDescription())
                .category(stream.getCategory())
                .thumbnailUrl(stream.getThumbnailUrl())
                .streamKey(stream.getStreamKey())
                .live(stream.isLive())
                .rtmpUrl(RTMP_BASE)
                .hlsUrl(HLS_BASE + stream.getStreamKey() + ".m3u8")
                .build();
    }
}


