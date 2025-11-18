package com.streamora.backend.stream;

import com.streamora.backend.stream.dto.StartStreamRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/streams")
@RequiredArgsConstructor
public class StreamController {

    private final StreamService streamService;

    @PostMapping("/start")
    public Stream startStream(
            @RequestParam Long userId,
            @RequestBody StartStreamRequest request
    ) {
        return streamService.startStream(
                userId,
                request.getTitle(),
                request.getDescription()
        );
    }

    @PostMapping("/stop")
    public Stream stopStream(@RequestParam Long userId) {
        return streamService.stopStream(userId);
    }

    @GetMapping("/active")
    public List<Stream> getActiveStreams() {
        return streamService.getActiveStreams();
    }
}




