package com.streamora.backend.user.dto;

public class UserProfileDTO {

    public String username;
    public String email;
    public String avatarUrl;

    public UserProfileDTO(String username, String email, String avatarUrl) {
        this.username = username;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }
}
