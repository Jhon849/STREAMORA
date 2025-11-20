package com.streamora.backend.auth;

import com.streamora.backend.email.EmailService;
import com.streamora.backend.security.JwtService;
import com.streamora.backend.user.User;
import com.streamora.backend.user.UserRole;
import com.streamora.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService; // 🔥 Necesario para enviar correos

    // ============================
    //        REGISTER
    // ============================
    public AuthResponse register(RegisterRequest request) {

        if (userService.emailExists(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userService.usernameExists(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Crear la contraseña cifrada
        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        // Crear usuario
        User user = userService.createUser(
                request.getUsername(),
                request.getEmail(),
                encryptedPassword,
                UserRole.USER
        );

        // =============================
        //    GENERAR CÓDIGO EMAIL
        // =============================
        String code = String.format("%06d", new java.util.Random().nextInt(999999));

        user.setVerificationCode(code);
        user.setVerificationExpiresAt(LocalDateTime.now().plusMinutes(15));
        user.setEmailVerified(false);

        userService.saveUser(user);

        // =============================
        //    ENVIAR CÓDIGO POR EMAIL
        // =============================
        emailService.sendVerificationCode(user.getEmail(), code);

        // No devolver JWT todavía
        return new AuthResponse("PENDING_VERIFICATION");
    }

    // ============================
    //        LOGIN
    // ============================
    public AuthResponse login(LoginRequest request) {

        User user = userService.getByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 🚨 Bloquear login si NO está verificado
        if (!user.isEmailVerified()) {
            throw new RuntimeException("Email not verified");
        }

        // Login OK
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}





