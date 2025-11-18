package com.streamora.backend.webrtc;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles incoming WebRTC signaling messages (offer, answer, ICE candidates)
 * and relays them to other connected clients.
 */
@Component
public class WebRtcSignalHandler extends TextWebSocketHandler {

    // Store active WebSocket sessions
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        System.out.println("WebSocket connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession senderSession, TextMessage message) throws Exception {
        String payload = message.getPayload();

        // Debugging
        System.out.println("Received WebRTC signal: " + payload);

        // Broadcast signaling messages to all other users except the sender
        for (Map.Entry<String, WebSocketSession> entry : sessions.entrySet()) {
            WebSocketSession session = entry.getValue();

            if (!session.getId().equals(senderSession.getId()) && session.isOpen()) {
                session.sendMessage(new TextMessage(payload));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        System.out.println("WebSocket disconnected: " + session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("WebSocket transport error on session " + session.getId());
        exception.printStackTrace();
    }
}
