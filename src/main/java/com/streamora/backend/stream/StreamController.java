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
            @RequestParam String userId,   // <-- CAMBIO IMPORTANTE
            @RequestBody StartStreamRequest request
    ) {
        return streamService.startStream(
                userId,
                request.getTitle(),
                request.getDescription()
        );
    }

    @PostMapping("/stop")
    public Stream stopStream(@RequestParam String userId) {  // <-- CAMBIO
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

    // ========================
    //   VIEWER COUNTER REALTIME
    // ========================

    @PostMapping("/{id}/view")
    public Stream addViewer(@PathVariable Long id) {

        Stream updated = streamService.addViewer(id);

        // 🔥 Notificar en tiempo real por WebSocket
        messagingTemplate.convertAndSend(
                "/topic/streams/" + id + "/viewers",
                updated.getViewerCount()
        );

        return updated;
    }

    @PostMapping("/{id}/leave")
    public Stream removeViewer(@PathVariable Long id) {

        Stream updated = streamService.removeViewer(id);

        // 🔥 Notificar en tiempo real por WebSocket
        messagingTemplate.convertAndSend(
                "/topic/streams/" + id + "/viewers",
                updated.getViewerCount()
        );

        return updated;
    }
}










