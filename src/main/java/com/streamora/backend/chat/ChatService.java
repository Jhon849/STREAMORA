package com.streamora.backend.chat;

import com.streamora.backend.user.User;
import com.streamora.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    public Message saveMessage(Long userId, Long roomId, String content) {
        User user = userService.getUser(userId);

        Message msg = Message.builder()
                .content(content)
                .timestamp(LocalDateTime.now())
                .sender(user)
                .roomId(roomId)
                .build();

        return messageRepository.save(msg);
    }

    public List<Message> getRoomMessages(Long roomId) {
        return messageRepository.findByRoomIdOrderByTimestampAsc(roomId);
    }
}
