package com.streamora.backend.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    public void sendVerificationCode(String to, String code) {
        log.info("📧 [FAKE EMAIL] Enviando código de verificación {} a {}", code, to);
    }

    public void sendResetToken(String to, String token) {
        log.info("📧 [FAKE EMAIL] Enviando token de reset {} a {}", token, to);
    }
}


