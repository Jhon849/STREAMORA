package com.streamora.backend.stream;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/streams")
@RequiredArgsConstructor
public class StreamController {

    private final StreamService streamService;

    @PostMapping("/start")
    public Stream start(@RequestParam Long userId, @RequestParam String title) {
        return streamService.startStream(userId, title);
    }

    @PostMapping("/stop")
    public Stream stop(@RequestParam Long userId) {
        return streamService.stopStream(userId);
    }

    @GetMapping("/active")
    public List<Stream> activeStreams() {
        return streamService.getActiveStreams();
    }
}



