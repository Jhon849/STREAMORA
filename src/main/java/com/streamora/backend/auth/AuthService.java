package com.streamora.backend.auth;

import com.streamora.backend.security.jwt.JwtProvider;
import com.streamora.backend.user.User;
import com.streamora.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtProvider jwtProvider;

    public AuthResponse register(RegisterRequest req) {

        if (userRepo.existsByUsername(req.username)) {
            throw new RuntimeException("Username already taken");
        }

        if (userRepo.existsByEmail(req.email)) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setUsername(req.username);
        user.setEmail(req.email);
        user.setPassword(encoder.encode(req.password));
        user.setRole("USER");

        userRepo.save(user);

        String token = jwtProvider.generateToken(user.getUsername());

        return new AuthResponse("User registered successfully", token);
    }

    public AuthResponse login(LoginRequest req) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username, req.password)
        );

        String token = jwtProvider.generateToken(req.username);

        return new AuthResponse("Login successful", token);
    }
}
