package com.streamora.backend.user;

import com.streamora.backend.cloud.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

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
