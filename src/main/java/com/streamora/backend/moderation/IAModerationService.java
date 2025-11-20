package com.streamora.backend.moderation;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IAModerationService {

    private final List<String> forbiddenWords = List.of(
            "puta", "puto", "gay", "negro", "marica",
            "sexo", "porno", "violación",
            "matar", "suicidio", "bomba",
            "mierda", "pendejo", "idiota",
            "http://", "https://"
    );

    public String checkMessage(String msg) {
        if (msg.length() > 200) return "Mensaje demasiado largo";
        if (msg.equals(msg.toUpperCase()) && msg.length() > 10)
            return "Demasiadas mayúsculas";

        for (String word : forbiddenWords) {
            if (msg.toLowerCase().contains(word)) {
                return "Palabra prohibida: " + word;
            }
        }

        return null; // mensaje aprobado
    }
}


