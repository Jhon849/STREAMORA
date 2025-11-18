package com.streamora.backend.user;

import com.streamora.backend.cloud.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    // =============================
    // 🔍 VALIDACIONES PARA REGISTER
    // =============================
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // =============================
    // 🧩 CREACIÓN DE USUARIO
    // =============================
    public User createUser(String username, String email, String encryptedPassword, UserRole role) {

        User user = User.builder()
                .username(username)
                .email(email)
                .password(encryptedPassword)
                .role(role)
                .createdAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    // =============================
    // 🔍 LOGIN (usa Optional)
    // =============================
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // =======================================================
    // MÉTODOS QUE YA TENÍAS (NO SE BORRAN)
    // =======================================================

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public User uploadAvatar(Long id, MultipartFile file) throws IOException {
        User user = getUser(id);

        String url = cloudinaryService.upload(file);
        user.setAvatarUrl(url);

        return userRepository.save(user);
    }

    public User uploadBanner(Long id, MultipartFile file) throws IOException {
        User user = getUser(id);

        String url = cloudinaryService.upload(file);
        user.setBannerUrl(url);

        return userRepository.save(user);
    }
}


