package com.streamora.backend.auth;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthRequest {
    private String usernameOrEmail;
    private String password;
}
