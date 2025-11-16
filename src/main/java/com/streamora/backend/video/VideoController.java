package com.streamora.backend.video;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/upload/{userId}")
    public ResponseEntity<Video> upload(
            @PathVariable Long userId,
            @RequestBody Video video
    ) {
        return ResponseEntity.ok(videoService.upload(video, userId));
    }

    @GetMapping
    public ResponseEntity<List<Video>> all() {
        return ResponseEntity.ok(videoService.getAllPublic());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> get(@PathVariable Long id) {
        return videoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<Video>> search(@PathVariable String title) {
        return ResponseEntity.ok(videoService.search(title));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        videoService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/view")
    public ResponseEntity<Video> addView(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.addView(id));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Video> like(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.like(id));
    }

    @PostMapping("/{id}/dislike")
    public ResponseEntity<Video> dislike(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.dislike(id));
    }
}
