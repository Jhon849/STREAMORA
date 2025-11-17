package com.streamora.backend.auth;

import com.streamora.backend.user.User;

public class AuthResponse {

    private String token;
    private String username;
    private String email;
    private String role;

    public AuthResponse() {}

    // constructor solo con token
    public AuthResponse(String token) {
        this.token = token;
    }

    // constructor con todos los campos
    public AuthResponse(String token, String username, String email, String role) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // constructor con token + user
    public AuthResponse(String token, User user) {
        this.token = token;
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}




