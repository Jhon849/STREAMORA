package com.streamora.backend.auth;

import com.streamora.backend.user.User;
import com.streamora.backend.user.UserRepository;
import com.streamora.backend.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    public String register(RegisterRequest request) {

        if (userRepo.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email already exists");

        User user = User.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .avatarUrl(request.getAvatarUrl())
                .build();

        userRepo.save(user);

        return jwtProvider.generateToken(user.getEmail());
    }

    public String login(LoginRequest request) {

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid password");

        return jwtProvider.generateToken(user.getEmail());
    }
}


