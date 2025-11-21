package com.streamora.backend.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class EmailService {

    @Value("${resend.api.key}")
    private String apiKey;

    @Value("${resend.from.email}")
    private String fromEmail;

    private final RestTemplate restTemplate = new RestTemplate();

    // 🔥 MÉTODO QUE NECESITABA EL AuthService
    public void sendVerificationCode(String email, String code) {
        sendVerificationEmail(email, "Usuario", code);
    }

    // 🔥 Tu método original
    public void sendVerificationEmail(String to, String username, String token) {

        String htmlBody = """
            <div style="font-family: 'Inter', Arial, sans-serif; 
                        background:#fafafa; 
                        color:#111; 
                        padding:40px; 
                        border-radius:12px; 
                        max-width:480px; 
                        margin:auto;
                        border:1px solid #eee;">

                <h2 style="text-align:center; 
                           font-size:26px; 
                           margin-bottom:10px;
                           color:#7c3aed;">
                    Bienvenido a Streamora ✨
                </h2>

                <p style="font-size:15px; line-height:1.6; text-align:center; margin-bottom:30px;">
                    ¡Gracias por registrarte, %s! Antes de continuar, necesitamos confirmar tu correo electrónico.
                </p>

                <div style="text-align:center; margin:30px 0;">
                    <div style="font-size:34px;
                                letter-spacing:4px;
                                font-weight:700;
                                padding:14px 0;
                                background:#7c3aed;
                                color:white;
                                border-radius:10px;">
                        %s
                    </div>
                </div>

                <p style="font-size:14px; text-align:center; color:#555;">
                    Este código expirará en <strong>15 minutos</strong>.
                </p>

                <hr style="margin:35px 0; border:none; border-top:1px solid #ddd;">

                <p style="font-size:12px; text-align:center; color:#888;">
                    Si no creaste esta cuenta, puedes ignorar este mensaje.  
                    <br>© Streamora.space
                </p>
            </div>
        """.formatted(username, token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
                "from", fromEmail,
                "to", to,
                "subject", "Verifica tu cuenta — Streamora",
                "html", htmlBody
        );

        restTemplate.postForEntity(
                "https://api.resend.com/emails",
                new HttpEntity<>(body, headers),
                String.class
        );
    }
}

















