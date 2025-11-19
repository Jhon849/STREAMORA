package com.streamora.backend.auth;

import com.streamora.backend.security.JwtService;
import com.streamora.backend.user.User;
import com.streamora.backend.user.UserRole;
import com.streamora.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {

        if (userService.emailExists(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userService.usernameExists(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        User user = userService.createUser(
                request.getUsername(),
                request.getEmail(),
                encryptedPassword,
                UserRole.USER
        );

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {

        User user = userService.getByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}


