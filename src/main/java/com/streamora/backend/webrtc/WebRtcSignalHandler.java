package com.streamora.backend.webrtc;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;

@Controller
@RequiredArgsConstructor
public class WebRtcSignalHandler {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/webrtc")
    public void signaling(WebRtcSignal signal) {
        // Redistribuir señal WebRTC a los clientes del room
        messagingTemplate.convertAndSend("/topic/webrtc/" + signal.getRoomId(), signal);
    }
}

