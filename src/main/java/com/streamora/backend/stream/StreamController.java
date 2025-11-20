package com.streamora.backend.stream;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.streamora.backend.stream.dto.StartStreamRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/streams")
@RequiredArgsConstructor
public class StreamController {

    private final SimpMessagingTemplate messagingTemplate;
    private final StreamService streamService;

    @PostMapping("/start")
    public Stream startStream(
            @RequestParam String userId,
            @RequestBody StartStreamRequest request
    ) {
        return streamService.startStream(
                userId,
                request.getTitle(),
                request.getDescription()
        );
    }

    @PostMapping("/stop")
    public Stream stopStream(@RequestParam String userId) {
        return streamService.stopStream(userId);
    }

    // 🔥 Alias para el frontend
    @PostMapping("/end")
    public Stream endStream(@RequestParam String userId) {
        return streamService.stopStream(userId);
    }

    @GetMapping("/live")
    public List<Stream> getLiveStreams() {
        return streamService.getActiveStreams();
    }

    @GetMapping("/active")
    public List<Stream> getActiveStreams() {
        return streamService.getActiveStreams();
    }

    // 🔥 Obtener stream por ID (lo pide frontend)
    @GetMapping("/{id}")
    public Stream getStream(@PathVariable Long id) {
        return streamService.getStreamById(id);
    }

    @PostMapping("/{id}/view")
    public Stream addViewer(@PathVariable Long id) {
        Stream updated = streamService.addViewer(id);

        messagingTemplate.convertAndSend(
                "/topic/streams/" + id + "/viewers",
                updated.getViewerCount()
        );

        return updated;
    }

    @PostMapping("/{id}/leave")
    public Stream removeViewer(@PathVariable Long id) {
        Stream updated = streamService.removeViewer(id);

        messagingTemplate.convertAndSend(
                "/topic/streams/" + id + "/viewers",
                updated.getViewerCount()
        );

        return updated;
    }
}











