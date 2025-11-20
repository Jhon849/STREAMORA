package com.streamora.backend.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String refreshToken;
    private Long id;
    private String email;
    private String username;
    private String avatarUrl;
    private String role;
}

