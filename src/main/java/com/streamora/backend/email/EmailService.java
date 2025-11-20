package com.streamora.backend.email;

import com.sendgrid.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.sendgrid.SendGrid;
import com.sendgrid.Request;
import com.sendgrid.Method;
import com.sendgrid.Response;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Content;

import java.io.IOException;

@Service
@Slf4j
public class EmailService {

    @Value("${SENDGRID_API_KEY}")
    private String apiKey;

    // ================================
    // VERIFICATION EMAIL
    // ================================
    public void sendVerificationCode(String to, String code) {
        String subject = "Verifica tu correo — Streamora.space";
        String html = getVerificationTemplate(code);

        sendEmail(to, subject, html);
    }

    // ================================
    // RESET PASSWORD EMAIL
    // ================================
    public void sendResetToken(String to, String token) {
        String subject = "Restablece tu contraseña — Streamora.space";

        String link = "https://streamora-space.vercel.app/reset-password?token=" + token;

        String html = getResetTemplate(link);

        sendEmail(to, subject, html);
    }

    // ================================
    // CORE SEND EMAIL
    // ================================
    private void sendEmail(String to, String subject, String htmlContent) {
        Email from = new Email("no-reply@streamora.space", "Streamora");
        Email recipient = new Email(to);

        Content content = new Content("text/html", htmlContent);
        Mail mail = new Mail(from, subject, recipient, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            log.info("📧 Email enviado a {} | Status: {}", to, response.getStatusCode());

        } catch (IOException e) {
            log.error("❌ Error enviando email: {}", e.getMessage());
        }
    }

    // ================================
    // HTML TEMPLATES
    // ================================

    private String getVerificationTemplate(String code) {
        return """
        <div style="font-family: Arial; background: #0f0f1a; padding: 40px; color: white;">
            <h2 style="color:#c084fc;">streamora<span style="color:#a855f7;">.space</span></h2>
            <p>Tu código de verificación:</p>

            <div style="font-size: 32px; font-weight: bold; background: #1e1b2e; 
                        padding: 15px; width: 160px; text-align:center; border-radius: 8px;">
                %s
            </div>

            <p style="margin-top:20px; opacity: 0.8;">
                Este código vence en <strong>15 minutos</strong>.
            </p>
        </div>
        """.formatted(code);
    }

    private String getResetTemplate(String link) {
        return """
        <div style="font-family: Arial; background:#0f0f1a; padding:40px; color:white;">
            <h2 style="color:#c084fc;">Restablecer contraseña</h2>

            <p>Haz clic en el siguiente botón para cambiar tu contraseña:</p>

            <a href="%s" style="display:inline-block; padding:12px 25px; 
                background:#8b5cf6; color:white; text-decoration:none; 
                border-radius:6px; font-weight:bold; margin-top:10px;">
                Restablecer contraseña
            </a>

            <p style="margin-top:20px; opacity:0.7;">
                Si no solicitaste esto, ignora este mensaje.
            </p>
        </div>
        """.formatted(link);
    }
}










