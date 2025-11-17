package com.streamora.backend.stream;

import com.streamora.backend.stream.dto.CreateStreamRequest;
import com.streamora.backend.stream.dto.StreamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/streams")
@RequiredArgsConstructor
public class StreamController {

    private final StreamService streamService;

    @PostMapping("/create/{userId}")
    public StreamResponse createStream(
            @PathVariable Long userId,
            @RequestBody CreateStreamRequest request
    ) {
        return streamService.createStream(userId, request);
    }

    @GetMapping("/{userId}")
    public StreamResponse getStreamByUser(@PathVariable Long userId) {
        return streamService.getStreamByUser(userId);
    }

    @GetMapping
    public List<StreamResponse> getAllStreams() {
        return streamService.getAllStreams();
    }
}


