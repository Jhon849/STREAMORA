package com.streamora.backend.stream;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stream-config")
@RequiredArgsConstructor
public class StreamConfigController {

    private final StreamConfigService streamConfigService;

    // ✔ El frontend usa este endpoint
    @GetMapping("/me")
    public StreamConfig getConfig(@RequestParam String userId) {
        return streamConfigService.getMyConfig(userId);
    }

    // ✔ El frontend usa este endpoint
    @PutMapping("/update")
    public StreamConfig updateConfig(
            @RequestParam String userId,
            @RequestBody StreamConfig request
    ) {
        return streamConfigService.updateConfig(userId, request);
    }
}


