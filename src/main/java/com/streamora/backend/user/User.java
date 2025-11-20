package com.streamora.backend.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private String avatarUrl;
    private String bannerUrl;

    private String bio;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime createdAt;

    // Email verificado o no
    private boolean emailVerified = false;

    // Verificación de email
    private String verificationCode;
    private LocalDateTime verificationExpiresAt;

    // Recuperación de contraseña
    private String resetToken;
    private LocalDateTime resetTokenExpiresAt;

}


