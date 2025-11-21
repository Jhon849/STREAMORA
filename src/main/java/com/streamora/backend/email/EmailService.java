package com.streamora.backend.email;

import com.resend.Resend;
import com.resend.services.emails.Emails;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Value("${RESEND_API_KEY}")
    private String apiKey;

    public void sendVerificationCode(String to, String code) {
        String subject = "Verifica tu correo — Streamora";
        String html = getVerificationTemplate(code);
        sendEmail(to, subject, html);
    }

    public void sendResetToken(String to, String token) {
        String subject = "Restablece tu contraseña — Streamora";
        String link = "https://streamora-space.vercel.app/reset-password?token=" + token;
        String html = getResetTemplate(link);
        sendEmail(to, subject, html);
    }

    private void sendEmail(String to, String subject, String html) {
        try {
            Resend resend = new Resend(apiKey);
            Emails emails = resend.emails();

            CreateEmailOptions request = CreateEmailOptions.builder()
                    .from("onboarding@resend.dev")   // IMPORTANTE: sin alias
                    .to(to)
                    .subject(subject)
                    .html(html)
                    .build();

            CreateEmailResponse response = emails.send(request);

            log.info("📧 Email enviado a {} | ID: {}", to, response.getId());

        } catch (Exception e) {
            log.error("❌ Error enviando correo: {}", e.getMessage());
        }
    }

    private String getVerificationTemplate(String code) {
        return """
        <div style='font-family: Arial; background: #0f0f1a; padding: 40px; color: white;'>
            <h2 style='color:#c084fc;'>streamora<span style='color:#a855f7;'>.space</span></h2>
            <p>Tu código de verificación:</p>

            <div style='font-size: 32px; font-weight: bold; background: #1e1b2e;
                        padding: 15px; width: 160px; text-align:center; border-radius: 8px;'>
                %s
            </div>

            <p style='margin-top:20px; opacity: 0.8;'>Este código vence en <strong>15 minutos</strong>.</p>
        </div>
        """.formatted(code);
    }

    private String getResetTemplate(String link) {
        return """
        <div style='font-family: Arial; background:#0f0f1a; padding:40px; color:white;'>
            <h2 style='color:#c084fc;'>Restablecer contraseña</h2>

            <p>Haz clic en el botón para cambiar tu contraseña:</p>

            <a href='%s' style='display:inline-block; padding:12px 25px; 
                background:#8b5cf6; color:white; text-decoration:none; 
                border-radius:6px; font-weight:bold; margin-top:10px;'>
                Restablecer contraseña
            </a>

            <p style='margin-top:20px; opacity:0.7;'>Si no solicitaste esto, ignóralo.</p>
        </div>
        """.formatted(link);
    }
}











