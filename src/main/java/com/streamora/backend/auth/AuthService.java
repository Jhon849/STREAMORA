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

    // ============================
    // REGISTER
    // ============================
    public AuthResponse register(RegisterRequest request) {

        if (userService.emailExists(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userService.usernameExists(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        // Crear usuario
        User user = userService.createUser(
                request.getUsername(),
                request.getEmail(),
                encryptedPassword,
                UserRole.USER
        );

        // ⚠️ IMPORTANTE: NO retornar token aún (el usuario no está verificado)
        return new AuthResponse("PENDING_VERIFICATION");
    }

    // ============================
    // LOGIN
    // ============================
    public AuthResponse login(LoginRequest request) {

        User user = userService.getByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // Verificar contraseña
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 🚨 VERIFICAR EMAIL
        if (!user.isEmailVerified()) {
            throw new RuntimeException("Email not verified");
        }

        // Login OK → generar token
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}



