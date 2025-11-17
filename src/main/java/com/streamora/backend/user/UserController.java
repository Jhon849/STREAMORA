package com.streamora.backend.user;

import lombok.RequiredArgsConstructor;
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
        return "User module working!";
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    // ⭐ SUBIR AVATAR
    @PostMapping("/{id}/avatar")
    public User uploadAvatar(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return userService.uploadAvatar(id, file);
    }

    // ⭐ SUBIR BANNER
    @PostMapping("/{id}/banner")
    public User uploadBanner(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return userService.uploadBanner(id, file);
    }
}
