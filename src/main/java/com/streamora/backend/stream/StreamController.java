package com.streamora.backend.stream;

import com.streamora.backend.stream.dto.StartStreamRequest;
import com.streamora.backend.stream.dto.StreamEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/streams")
@RequiredArgsConstructor
public class StreamController {

    private final SimpMessagingTemplate messagingTemplate;
    private final StreamService streamService;
    private final StreamConfigService streamConfigService;
    private final LivepushService livepushService;

    // ===========================
    // 🔥 Start Stream
    // ===========================
    @PostMapping("/start")
    public Stream startStream(
            @RequestParam String userId,
            @RequestBody StartStreamRequest request
    ) {
        if (request == null || request.getTitle() == null || request.getDescription() == null) {
            throw new RuntimeException("Title and description are required");
        }

        return streamService.startStream(userId, request);
    }

    // ===========================
    // 🔥 Stop Stream
    // ===========================
    @PostMapping("/stop")
    public Stream stopStream(@RequestParam String userId) {
        return streamService.stopStream(userId);
    }

    @PostMapping("/end")
    public Stream endStream(@RequestParam String userId) {
        return streamService.stopStream(userId);
    }

    // ===========================
    // 🔥 Live streams
    // ===========================
    @GetMapping("/live")
    public List<Stream> getLiveStreams() {
        return streamService.getActiveStreams();
    }

    @GetMapping("/active")
    public List<Stream> getActiveStreams() {
        return streamService.getActiveStreams();
    }

    // ===========================
    // 🔥 Get stream by ID
    // ===========================
    @GetMapping("/{id}")
    public Stream getStream(@PathVariable Long id) {
        return streamService.getStreamById(id);
    }

    // ===========================
    // 🔥 Viewers
    // ===========================
    @PostMapping("/{id}/view")
    public Stream addViewer(@PathVariable Long id) {
        Stream updated = streamService.addViewer(id);
        messagingTemplate.convertAndSend("/topic/streams/" + id + "/viewers", updated.getViewerCount());
        return updated;
    }

    @PostMapping("/{id}/leave")
    public Stream removeViewer(@PathVariable Long id) {
        Stream updated = streamService.removeViewer(id);
        messagingTemplate.convertAndSend("/topic/streams/" + id + "/viewers", updated.getViewerCount());
        return updated;
    }

    // ===========================
    // 🔥 NEW: RTMP + Player endpoints for frontend
    // ===========================
    @GetMapping("/{userId}/endpoints")
    public StreamEndpoints getEndpoints(@PathVariable String userId) {

        StreamConfig config = streamConfigService.getMyConfig(userId);

        return livepushService.getEndpoints(
                userId,
                config.getStreamKey()
        );
    }
}

















