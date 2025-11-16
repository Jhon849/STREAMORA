package com.streamora.backend.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping
    public ResponseEntity<?> getMyProfile(Authentication auth) {
        String username = auth.getName();
        return ResponseEntity.ok(userProfileService.getProfile(username));
    }

    @PutMapping
    public ResponseEntity<?> updateMyProfile(
            Authentication auth,
            @RequestBody UpdateProfileDTO dto
    ) {
        String username = auth.getName();
        return ResponseEntity.ok(userProfileService.updateProfile(username, dto));
    }
}
