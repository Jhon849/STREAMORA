package com.streamora.backend.profile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileDTO {
    private Long id;
    private String username;
    private String email;
    private String displayName;
    private String role;
}
