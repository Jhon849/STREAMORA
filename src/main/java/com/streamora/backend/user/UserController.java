package com.streamora.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    // 🔥 NEW: List all users (important for knowing IDs)
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
