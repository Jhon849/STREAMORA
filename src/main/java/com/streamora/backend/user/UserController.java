package com.streamora.backend.user;

import com.streamora.backend.user.dto.UpdateProfileDTO;
import com.streamora.backend.user.dto.UserProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public UserProfileDTO getMyInfo(@RequestAttribute("username") String username) {

        User u = userService.getUser(username);

        return new UserProfileDTO(
                u.getUsername(),
                u.getEmail(),
                u.getAvatarUrl()
        );
    }

    @PutMapping("/me")
    public UserProfileDTO updateMyInfo(@RequestAttribute("username") String username,
                                       @RequestBody UpdateProfileDTO dto) {

        if (dto.email != null) {
            userService.updateEmail(username, dto.email);
        }

        if (dto.avatarUrl != null) {
            userService.updateAvatar(username, dto.avatarUrl);
        }

        User u = userService.getUser(username);

        return new UserProfileDTO(
                u.getUsername(),
                u.getEmail(),
                u.getAvatarUrl()
        );
    }
}
