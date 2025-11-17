package com.streamora.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUser(email));
    }

    @PutMapping("/{email}/email")
    public ResponseEntity<User> updateEmail(
            @PathVariable String email,
            @RequestParam String newEmail) {
        return ResponseEntity.ok(userService.updateEmail(email, newEmail));
    }

    @PutMapping("/{email}/avatar")
    public ResponseEntity<User> updateAvatar(
            @PathVariable String email,
            @RequestParam String url) {
        return ResponseEntity.ok(userService.updateAvatar(email, url));
    }
}

