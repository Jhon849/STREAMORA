package com.streamora.backend.video;

import com.streamora.backend.user.User;
import com.streamora.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    public Video upload(Video video, Long userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        video.setOwner(owner);
        video.setCreatedAt(LocalDateTime.now());
        video.setViews(0);
        video.setLikes(0);
        video.setDislikes(0);
        video.setPublic(true);

        return videoRepository.save(video);
    }

    public List<Video> getAllPublic() {
        return videoRepository.findAll()
                .stream()
                .filter(Video::isPublic)
                .toList();
    }

    public Optional<Video> getById(String id) {
        return videoRepository.findById(id);
    }

    public List<Video> search(String title) {
        return videoRepository.findByTitleContainingIgnoreCase(title);
    }

    public void delete(String id) {
        videoRepository.deleteById(id);
    }

    public Video addView(String id) {
        Video v = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        v.setViews(v.getViews() + 1);
        return videoRepository.save(v);
    }

    public Video like(String id) {
        Video v = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        v.setLikes(v.getLikes() + 1);
        return videoRepository.save(v);
    }

    public Video dislike(String id) {
        Video v = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        v.setDislikes(v.getDislikes() + 1);
        return videoRepository.save(v);
    }
}

