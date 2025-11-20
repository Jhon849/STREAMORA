package com.streamora.backend.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
    private Long id;
    private String email;
    private String username;
    private String avatarUrl;
    private String bannerUrl;
    private String bio;
    private String role;
}
