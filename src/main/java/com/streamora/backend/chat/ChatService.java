package com.streamora.backend.chat;

import com.streamora.backend.moderation.IAModerationService;
import com.streamora.backend.moderation.ModerationLog;
import com.streamora.backend.moderation.ModerationLogRepository;
import com.streamora.backend.user.User;
import com.streamora.backend.user.UserRole;
import com.streamora.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository messageRepository;
    private final ModerationLogRepository logRepository;
    private final IAModerationService moderationService;
    private final UserService userService;
    private final SimpMessagingTemplate messaging;

    public void sendMessage(Long streamId, String userId, String content) {

        // IA revisa el mensaje
        String moderationResult = moderationService.checkMessage(content);

        if (moderationResult != null) {

            // registrar el mensaje moderado
            logRepository.save(
                ModerationLog.builder()
                        .streamId(streamId)
                        .userId(userId)
                        .originalMessage(content)
                        .reason(moderationResult)
                        .timestamp(LocalDateTime.now())
                        .build()
            );

            return; // ❌ no enviar el mensaje
        }

        // obtener usuario
        User user = userService.getUser(userId);

        // =============================
        // 🔥 DETERMINAR ROL DEL CHAT
        // =============================
        ChatRole role;

        if (user.getRole() == UserRole.STREAMER) {
            role = ChatRole.STREAMER;
        } else if (user.getRole() == UserRole.ADMIN) {
            role = ChatRole.MODERATOR;
        } else {
            role = ChatRole.VIEWER; // Default si no tienes subs/vip aún
        }

        // =============================
        // 🔥 BADGE AUTOMÁTICO
        // =============================
        String badge = switch (role) {
            case STREAMER -> "streamer";
            case MODERATOR -> "mod";
            case VIP -> "vip";
            case FOUNDER -> "founder";
            case SUBSCRIBER -> "sub";
            default -> null;
        };

        // =============================
        // 🔥 COLOR AUTOMÁTICO
        // =============================
        String color = switch (role) {
            case STREAMER -> "#ff0000";     // rojo streamer
            case MODERATOR -> "#22c55e";    // verde mod
            case VIP -> "#8b5cf6";          // morado
            case FOUNDER -> "#eab308";      // dorado
            case SUBSCRIBER -> "#3b82f6";   // azul
            default -> "#ffffff";           // blanco viewer
        };

        // =============================
        // 🔥 CREAR MENSAJE
        // =============================
        Message msg = Message.builder()
                .streamId(streamId)
                .sender(user)
                .content(content)
                .role(role)
                .badge(badge)
                .color(color)
                .timestamp(LocalDateTime.now())
                .moderated(false)
                .build();

        // guardar mensaje
        messageRepository.save(msg);

        // 🔥 enviar por WebSocket
        messaging.convertAndSend("/topic/chat/" + streamId, msg);
    }

    public List<Message> getMessages(Long streamId) {
        return messageRepository.findByStreamIdOrderByTimestampAsc(streamId);
    }
}



