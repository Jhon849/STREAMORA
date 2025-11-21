package com.streamora.backend.video;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/upload")
    public Video uploadVideo(
            @RequestParam String userId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        return videoService.uploadVideo(userId, title, description, file);
    }

    @GetMapping("/user/{userId}")
    public List<Video> getUserVideos(@PathVariable String userId) {
        return videoService.getUserVideos(userId);
    }

    @GetMapping("/all")
    public List<Video> getAllVideos() {
        return videoService.getAllVideos();
    }
}


