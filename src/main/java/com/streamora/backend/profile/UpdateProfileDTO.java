package com.streamora.backend.profile;

public class UpdateProfileDTO {

    private String displayName;
    private String email;
    private String role;

    public UpdateProfileDTO() {}

    public UpdateProfileDTO(String displayName, String email, String role) {
        this.displayName = displayName;
        this.email = email;
        this.role = role;
    }

    public String getDisplayName() { return displayName; }

    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }
}
