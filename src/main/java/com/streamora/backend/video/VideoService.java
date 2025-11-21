package com.streamora.backend.video;

import com.streamora.backend.cloud.CloudinaryService;
import com.streamora.backend.user.User;
import com.streamora.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;

    public Video uploadVideo(String userId, String title, String description, MultipartFile file) throws Exception {

        User user = userService.getUser(userId);

        // 🔥 Subir video Cloudinary
        String videoUrl = cloudinaryService.uploadVideo(file);

        // 🔥 Generar thumbnail automático
        String thumbnail = cloudinaryService.generateThumbnail(videoUrl);

        Video video = Video.builder()
                .title(title)
                .description(description)
                .videoUrl(videoUrl)
                .thumbnailUrl(thumbnail)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        return videoRepository.save(video);
    }

    public List<Video> getUserVideos(String userId) {
        User user = userService.getUser(userId);
        return videoRepository.findByUser(user);
    }

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }
}

