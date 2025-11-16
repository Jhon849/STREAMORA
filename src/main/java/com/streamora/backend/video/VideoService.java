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
        video.setViews(0L);
        video.setLikes(0L);
        video.setDislikes(0L);
        video.setCreatedAt(LocalDateTime.now());

        return videoRepository.save(video);
    }

    public List<Video> getAllPublic() {
        return videoRepository.findAll().stream()
                .filter(Video::isPublic)
                .toList();
    }

    public Optional<Video> getById(Long id) {
        return videoRepository.findById(id);
    }

    public List<Video> search(String title) {
        return videoRepository.findByTitleContainingIgnoreCase(title);
    }

    public void delete(Long id) {
        videoRepository.deleteById(id);
    }

    public Video addView(Long id) {
        Video v = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        v.setViews(v.getViews() + 1);
        return videoRepository.save(v);
    }

    public Video like(Long id) {
        Video v = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        v.setLikes(v.getLikes() + 1);
        return videoRepository.save(v);
    }

    public Video dislike(Long id) {
        Video v = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        v.setDislikes(v.getDislikes() + 1);
        return videoRepository.save(v);
    }
}
