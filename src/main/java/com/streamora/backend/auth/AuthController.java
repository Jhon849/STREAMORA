package com.streamora.backend.auth;

import com.streamora.backend.security.jwt.JwtProvider;
import com.streamora.backend.user.User;
import com.streamora.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest req) {

        User u = new User();
        u.setUsername(req.username);
        u.setEmail(req.email);
        u.setPassword(encoder.encode(req.password));
        u.setRole("USER");

        userRepo.save(u);

        String token = jwtProvider.generateToken(u.getUsername());

        return new AuthResponse("User registered", token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username, req.password)
        );

        String token = jwtProvider.generateToken(req.username);

        return new AuthResponse("Login successful", token);
    }

}
