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

    public Stream startStream(Long userId, String title) {

        User user = userService.getUser(userId);

        String streamKey = UUID.randomUUID().toString();

        Stream stream = Stream.builder()
                .title(title)
                .streamKey(streamKey)
                .isLive(true)
                .startedAt(LocalDateTime.now())
                .user(user)
                .build();

        return streamRepository.save(stream);
    }

    public Stream stopStream(Long userId) {
        Stream stream = streamRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User is not live"));

        stream.setLive(false);

        return streamRepository.save(stream);
    }

    public List<Stream> getActiveStreams() {
        return streamRepository.findByIsLiveTrue();
    }
}



