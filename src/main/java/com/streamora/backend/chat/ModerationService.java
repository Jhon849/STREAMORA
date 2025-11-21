package com.streamora.backend.chat;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModerationService {

    // Palabras prohibidas (se pueden agregar más)
    private final List<String> bannedWords = List.of(
            "puta", "marica", "malparido", "pendejo",
            "fuck", "shit", "bitch",
            "sexo", "porn", "xxx",
            "nazi", "hitler",
            "suicidio", "matarme"
    );

    public boolean isAllowed(String message) {
        if (message == null) return false;

        String clean = message.toLowerCase();

        // Regla 1: palabras prohibidas
        for (String bad : bannedWords) {
            if (clean.contains(bad)) {
                return false;
            }
        }

        // Regla 2: evitar SPAM repetido
        if (clean.length() > 250)
            return false;

        // Regla 3: bloquear links peligrosos
        if (clean.contains("http://") || clean.contains("https://"))
            return false;

        return true;
    }
}
