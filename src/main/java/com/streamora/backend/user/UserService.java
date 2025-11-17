package com.streamora.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(String username, String email, String encryptedPassword, UserRole role) {

        User user = User.builder()
                .username(username)
                .email(email)
                .password(encryptedPassword)
                .role(role)
                .avatarUrl(null)
                .createdAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // 🔥 ADDED: List all users (to know user IDs and debug)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
