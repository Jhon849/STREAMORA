package com.streamora.backend.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private Long id;
    private String username;
    private String email;
    private String displayName;
    private String role;
    private String token;
}
