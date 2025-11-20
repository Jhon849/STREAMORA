package com.streamora.backend.user;

import com.streamora.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyData(Authentication auth) {
        String email = auth.getName();
        User user = userService.getByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    // 🔥 NUEVO: profile (lo usa el frontend)
    @GetMapping("/profile")
    public User getProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtService.extractEmail(token);

        return userService.getByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 🔥 NUEVO: update (lo usa el frontend)
    @PutMapping("/update")
    public User updateProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody User req
    ) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtService.extractEmail(token);

        User user = userService.getByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Solo actualiza lo que venga
        if (req.getUsername() != null) user.setUsername(req.getUsername());
        if (req.getBio() != null) user.setBio(req.getBio());
        if (req.getAvatarUrl() != null) user.setAvatarUrl(req.getAvatarUrl());
        if (req.getBannerUrl() != null) user.setBannerUrl(req.getBannerUrl());

        return userService.saveUser(user);
    }

    @PostMapping("/{id}/avatar")
    public User uploadAvatar(@PathVariable String id, @RequestParam("file") MultipartFile file) throws IOException {
        return userService.uploadAvatar(id, file);
    }

    @PostMapping("/{id}/banner")
    public User uploadBanner(@PathVariable String id, @RequestParam("file") MultipartFile file) throws IOException {
        return userService.uploadBanner(id, file);
    }

    @PutMapping("/{id}/bio")
    public User updateBio(@PathVariable String id, @RequestBody String bio) {
        return userService.updateBio(id, bio);
    }

    // 🔥 FRONTEND LO PIDE
    @PostMapping("/{id}/follow")
    public String follow(@PathVariable String id) {
        return "Follow OK";
    }

    @PostMapping("/{id}/unfollow")
    public String unfollow(@PathVariable String id) {
        return "Unfollow OK";
    }
}




