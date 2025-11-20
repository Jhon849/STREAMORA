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

    // =======================================================
    //  VALIDACIONES
    // =======================================================

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // =======================================================
    //  CREAR USUARIO (siempre con createdAt)
    // =======================================================

    public User createUser(String username, String email, String encryptedPassword, UserRole role) {

        User user = User.builder()
                .username(username)
                .email(email)
                .password(encryptedPassword)
                .role(role)
                .createdAt(LocalDateTime.now()) // <-- siempre creado
                .build();

        return userRepository.save(user);
    }

    // =======================================================
    //  LOGIN
    // =======================================================

    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::fixCreatedAtSafely);
    }

    // =======================================================
    //  OBTENER USUARIO POR ID  (ARREGLA createdAt)
    // =======================================================

    public User getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return fixCreatedAtSafely(user);
    }

    // =======================================================
    //  OBTENER TODOS LOS USUARIOS (ARREGLA createdAt)
    // =======================================================

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.forEach(this::fixCreatedAtSafely);
        return users;
    }

    // =======================================================
    //  MÉTODO UNIVERSAL PARA ARREGLAR createdAt == null
    // =======================================================

    private User fixCreatedAtSafely(User user) {
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
        }
        return user;
    }

    // =======================================================
    //  SUBIR AVATAR
    // =======================================================

    public User uploadAvatar(Long id, MultipartFile file) throws IOException {
        User user = getUser(id); // <-- ya pasa por el fix

        String url = cloudinaryService.upload(file);
        user.setAvatarUrl(url);

        return userRepository.save(user);
    }

    // =======================================================
    //  SUBIR BANNER
    // =======================================================

    public User uploadBanner(Long id, MultipartFile file) throws IOException {
        User user = getUser(id); // <-- ya pasa por el fix

        String url = cloudinaryService.upload(file);
        user.setBannerUrl(url);

        return userRepository.save(user);
    }

}



