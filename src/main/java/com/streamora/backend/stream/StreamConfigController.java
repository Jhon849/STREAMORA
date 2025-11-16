package com.streamora.backend.stream;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stream")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StreamConfigController {

    private final StreamConfigService streamConfigService;

    @PostMapping("/{userId}/init")
    public ResponseEntity<StreamConfig> init(@PathVariable Long userId) {
        return ResponseEntity.ok(streamConfigService.initStreamConfig(userId));
    }

    @PutMapping("/{userId}/settings")
    public ResponseEntity<StreamConfig> updateSettings(
            @PathVariable Long userId,
            @RequestBody StreamConfig req
    ) {
        return ResponseEntity.ok(
                streamConfigService.updateStream(userId, req.getTitle(), req.getCategory())
        );
    }

    @PutMapping("/{userId}/regenerate-key")
    public ResponseEntity<StreamConfig> regenerateKey(@PathVariable Long userId) {
        return ResponseEntity.ok(streamConfigService.regenerateStreamKey(userId));
    }

    @PutMapping("/{userId}/live/{state}")
    public ResponseEntity<StreamConfig> setLiveState(
            @PathVariable Long userId,
            @PathVariable boolean state
    ) {
        return ResponseEntity.ok(streamConfigService.setLiveState(userId, state));
    }
}
