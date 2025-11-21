package com.streamora.backend.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    public void sendVerificationCode(String to, String code) {

        log.warn("⚠️ (DEBUG) SIMULANDO EMAIL — NO SE ENVIA CORREO");
        log.warn("📧 Enviar verificación a: {}", to);
        log.warn("🔐 CÓDIGO DE VERIFICACIÓN: {}", code);
    }

    public void sendResetToken(String to, String token) {

        log.warn("⚠️ (DEBUG) SIMULANDO EMAIL — NO SE ENVIA CORREO");
        log.warn("📧 Enviar reset a: {}", to);
        log.warn("🔐 TOKEN DE RESET: {}", token);
    }
}













