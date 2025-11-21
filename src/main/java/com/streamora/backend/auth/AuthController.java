package com.streamora.backend.auth;

import com.streamora.backend.auth.dto.ForgotPasswordRequest;
import com.streamora.backend.auth.dto.ResetPasswordRequest;
import com.streamora.backend.auth.dto.VerifyEmailRequest;
import com.streamora.backend.security.JwtService;
import com.streamora.backend.user.User;
import com.streamora.backend.user.UserService;
import com.streamora.backend.email.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    // ================================
    //             REGISTER
    // ================================
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {

        log.info("Nuevo registro solicitado para email: {}", request.getEmail());
        return authService.register(request);
    }

    // ================================
    //              LOGIN
    // ================================
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        log.info("Intento de login para: {}", request.getEmail());
        return authService.login(request);
    }

    // ================================
    //          VERIFY EMAIL
    // ================================
    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestBody VerifyEmailRequest req) {

        log.info("Intento de verificación de email: {}", req.getEmail());

        var opt = userService.getByEmail(req.getEmail());
        if (opt.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Invalid email or code"));
        }

        User user = opt.get();

        if (user.getVerificationCode() == null ||
            user.getVerificationExpiresAt() == null ||
            user.getVerificationExpiresAt().isBefore(LocalDateTime.now()) ||
            !user.getVerificationCode().equals(req.getCode())) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Invalid or expired code"));
        }

        user.setEmailVerified(true);
        user.setVerificationCode(null);
        user.setVerificationExpiresAt(null);
        userService.saveUser(user);

        log.info("Email verificado correctamente: {}", req.getEmail());

        return ResponseEntity.ok(Map.of("status", "VERIFIED"));
    }

    // ================================
    //        FORGOT PASSWORD
    // ================================
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest req) {

        log.info("Solicitud de recuperación de contraseña para: {}", req.getEmail());

        var opt = userService.getByEmail(req.getEmail());

        // Respuesta segura
        if (opt.isEmpty()) {
            log.warn("Intento de recuperación para email no registrado: {}", req.getEmail());
            return ResponseEntity.ok(Map.of("status", "OK"));
        }

        User user = opt.get();

        String token = java.util.UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiresAt(LocalDateTime.now().plusHours(1));

        userService.saveUser(user);

        emailService.sendResetToken(user.getEmail(), token);

        return ResponseEntity.ok(Map.of("status", "RESET_EMAIL_SENT"));
    }

    // ================================
    //        RESET PASSWORD
    // ================================
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest req) {

        log.info("Intento de reset password");

        var opt = userService.getByResetToken(req.getToken());
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid or expired token"));
        }

        User user = opt.get();

        if (user.getResetTokenExpiresAt() == null ||
            user.getResetTokenExpiresAt().isBefore(LocalDateTime.now())) {

            return ResponseEntity.badRequest().body(Map.of("error", "Invalid or expired token"));
        }

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiresAt(null);

        userService.saveUser(user);

        log.info("Contraseña cambiada correctamente para: {}", user.getEmail());

        return ResponseEntity.ok(Map.of("status", "PASSWORD_RESET_SUCCESS"));
    }

    // ================================
    //                 ME
    // ================================
    @GetMapping("/me")
    public User me(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String email = jwtService.extractEmail(token);

        return userService.getByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ================================
    //                TEST
    // ================================
    @GetMapping("/test")
    public String test() {
        return "Auth working!";
    }
}






