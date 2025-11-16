package com.streamora.backend.stream;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stream")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StreamConfigController {

    private final StreamConfigService service;

    @GetMapping("/{userId}")
    public ResponseEntity<StreamConfig> getConfig(@PathVariable Long userId) {
        return ResponseEntity.ok(service.createOrGetConfig(userId));
    }

    @PutMapping("/{userId}/title")
    public ResponseEntity<StreamConfig> updateTitle(
            @PathVariable Long userId,
            @RequestParam String title
    ) {
        return ResponseEntity.ok(service.updateTitle(userId, title));
    }

    @PutMapping("/{userId}/category")
    public ResponseEntity<StreamConfig> updateCategory(
            @PathVariable Long userId,
            @RequestParam String category
    ) {
        return ResponseEntity.ok(service.updateCategory(userId, category));
    }
}

