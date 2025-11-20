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
    //  USER CREATION VALIDATIONS
    // =======================================================

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // =======================================================
    //  CREATE USER
    // =======================================================

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

    // =======================================================
    //  LOGIN
    // =======================================================

    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // =======================================================
    //  GET ALL USERS
    // =======================================================

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // =======================================================
    //  GET USER BY ID  (fixed createdAt null issue)
    // =======================================================

    public User getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 FIX: Ensure createdAt is never null
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
        }

        return user;
    }

    // =======================================================
    //  UPLOAD AVATAR
    // =======================================================

    public User uploadAvatar(Long id, MultipartFile file) throws IOException {
        User user = getUser(id);

        String url = cloudinaryService.upload(file);
        user.setAvatarUrl(url);

        return userRepository.save(user);
    }

    // =======================================================
    //  UPLOAD BANNER
    // =======================================================

    public User uploadBanner(Long id, MultipartFile file) throws IOException {
        User user = getUser(id);

        String url = cloudinaryService.upload(file);
        user.setBannerUrl(url);

        return userRepository.save(user);
    }

}



