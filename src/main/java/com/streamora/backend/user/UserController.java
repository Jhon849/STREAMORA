package com.streamora.backend.user;

import com.streamora.backend.user.dto.UpdateProfileDTO;
import com.streamora.backend.user.dto.UserProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public UserProfileDTO getProfile(@RequestAttribute("username") String username) {
        return userService.getProfile(username);
    }

    @PutMapping("/me")
    public UserProfileDTO updateProfile(@RequestAttribute("username") String username,
                                        @RequestBody UpdateProfileDTO dto) {
        return userService.updateProfile(username, dto);
    }
}
