package com.streamora.backend.video;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/upload/{userId}")
    public ResponseEntity<Video> upload(@RequestBody Video video, @PathVariable Long userId) {
        return ResponseEntity.ok(videoService.upload(video, userId));
    }

    @GetMapping("/public")
    public ResponseEntity<List<Video>> getPublicVideos() {
        return ResponseEntity.ok(videoService.getAllPublic());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getById(@PathVariable String id) {
        return videoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Video>> search(@RequestParam String title) {
        return ResponseEntity.ok(videoService.search(title));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        videoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/view")
    public ResponseEntity<Video> addView(@PathVariable String id) {
        return ResponseEntity.ok(videoService.addView(id));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Video> like(@PathVariable String id) {
        return ResponseEntity.ok(videoService.like(id));
    }

    @PostMapping("/{id}/dislike")
    public ResponseEntity<Video> dislike(@PathVariable String id) {
        return ResponseEntity.ok(videoService.dislike(id));
    }
}

