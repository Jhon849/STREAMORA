package com.streamora.backend.chat;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles real-time chat communication between viewers and streamers.
 */
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    // Store active chat sessions
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        System.out.println("Chat connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession sender, TextMessage message) throws Exception {
        String payload = message.getPayload();

        System.out.println("Chat message: " + payload);

        // Broadcast to all other clients
        for (WebSocketSession session : sessions.values()) {
            if (session.isOpen() && !session.getId().equals(sender.getId())) {
                session.sendMessage(new TextMessage(payload));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        System.out.println("Chat disconnected: " + session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("Chat WebSocket error: " + exception.getMessage());
    }
}
