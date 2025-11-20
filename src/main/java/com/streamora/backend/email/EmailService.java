package com.streamora.backend.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    public void sendVerificationCode(String to, String code) {
        log.info("📧 [FAKE EMAIL] Código de verificación enviado a {} -> {}", to, code);
    }

    public void sendResetToken(String to, String token) {
        log.info("📧 [FAKE EMAIL] Token de recuperación enviado a {} -> {}", to, token);
    }
}

