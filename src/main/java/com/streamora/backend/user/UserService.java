package com.streamora.backend.user;

import com.streamora.backend.user.dto.UpdateProfileDTO;
import com.streamora.backend.user.dto.UserProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private String avatarUrl;

public String getAvatarUrl() { return avatarUrl; }
public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }


    public UserProfileDTO getProfile(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDisplayName(),
                user.getRole(),
                null // avatarUrl después con Cloudinary
        );
    }

    public UserProfileDTO updateProfile(String username, UpdateProfileDTO dto) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.displayName != null) user.setDisplayName(dto.displayName);
        if (dto.email != null) user.setEmail(dto.email);
        if (dto.avatarUrl != null) user.setAvatarUrl(dto.avatarUrl);

        userRepository.save(user);

        return new UserProfileDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDisplayName(),
                user.getRole(),
                dto.avatarUrl
        );
    }
}



