package com.streamora.backend.webrtc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebRtcSignalHandler webRtcSignalHandler;

    public WebSocketConfig(WebRtcSignalHandler webRtcSignalHandler) {
        this.webRtcSignalHandler = webRtcSignalHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webRtcSignalHandler, "/ws/signal")
                .setAllowedOrigins("*");
    }
}
