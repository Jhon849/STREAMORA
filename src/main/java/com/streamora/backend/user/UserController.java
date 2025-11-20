package com.streamora.backend.user;

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

    @GetMapping("/test")
    public String test() {
        return "User module ready!";
    }

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
}


