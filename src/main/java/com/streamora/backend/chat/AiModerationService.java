package com.streamora.backend.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AiModerationService {

    @Value("${AI_API_KEY}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public ModerationResult moderateMessage(String message) {

        try {
            // Llamada FAKE por ahora (luego la cambiamos a OpenAI)
            String lower = message.toLowerCase();

            if (lower.contains("hp") || lower.contains("puta") || lower.contains("mierda")) {
                return new ModerationResult(
                        false,
                        "Mensaje ofensivo detectado",
                        90,
                        "Por favor evita lenguaje ofensivo",
                        null
                );
            }

            return new ModerationResult(
                    true,
                    "OK",
                    0,
                    "Mensaje permitido",
                    message
            );

        } catch (Exception e) {
            log.error("Error moderando mensaje: {}", e.getMessage());
            return new ModerationResult(true, "ERROR", 0, "Error IA", message);
        }
    }
}
