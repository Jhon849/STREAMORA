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

    // ===========================
    // VALIDACIONES
    // ===========================

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // ===========================
    // CREAR USUARIO
    // ===========================

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

    // ===========================
    // LOGIN
    // ===========================

    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::fixCreatedAtSafely);
    }

    // ===========================
    // OBTENER USER POR ID
    // ===========================

    public User getUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return fixCreatedAtSafely(user);
    }

    // ===========================
    // TODOS LOS USUARIOS
    // ===========================

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.forEach(this::fixCreatedAtSafely);
        return users;
    }

    // ===========================
    // ARREGLAR createdAt si está null
    // ===========================

    private User fixCreatedAtSafely(User user) {
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
        }
        return user;
    }

    // ===========================
    // SUBIR AVATAR
    // ===========================

    public User uploadAvatar(String id, MultipartFile file) throws IOException {
        User user = getUser(id);
        String url = cloudinaryService.upload(file);
        user.setAvatarUrl(url);
        return userRepository.save(user);
    }

    // ===========================
    // SUBIR BANNER
    // ===========================

    public User uploadBanner(String id, MultipartFile file) throws IOException {
        User user = getUser(id);
        String url = cloudinaryService.upload(file);
        user.setBannerUrl(url);
        return userRepository.save(user);
    }

    // ===========================
    // EDITAR BIO
    // ===========================

    public User updateBio(String id, String bio) {
        User user = getUser(id);
        user.setBio(bio);
        return userRepository.save(user);
    }

    // ===========================
    // NUEVO: guardar cambios del perfil
    // ===========================

    // ===========================
    // GUARDAR USER (lo usaremos mucho)
    // ===========================
    public User saveUser(User user) {
        return userRepository.save(user);
    }

   // ===========================
   // BUSCAR POR RESET TOKEN
   // ===========================
    public Optional<User> getByResetToken(String token) {
        return userRepository.findByResetToken(token)
                .map(this::fixCreatedAtSafely);
    }

    // ===========================
    // BUSCAR POR CÓDIGO DE VERIFICACIÓN
    // ===========================
public Optional<User> getByVerificationCode(String code) {
    return userRepository.findByVerificationCode(code)
            .map(this::fixCreatedAtSafely);
}

}



