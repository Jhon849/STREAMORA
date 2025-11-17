package com.streamora.backend.auth;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequest {
    private String email;
    private String password;
    private String avatarUrl;
}
