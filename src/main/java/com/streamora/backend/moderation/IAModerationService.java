package com.streamora.backend.moderation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class IAModerationService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate rest = new RestTemplate();

    public ModerationResult moderateText(String text) {

        try {
            String url = "https://api.openai.com/v1/moderations";

            JSONObject body = new JSONObject();
            body.put("model", "omni-moderation-latest");
            body.put("input", text);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

            ResponseEntity<String> response = rest.exchange(url, HttpMethod.POST, request, String.class);

            JSONObject json = new JSONObject(response.getBody());
            JSONObject result = json.getJSONArray("results").getJSONObject(0);

            boolean flagged = result.getBoolean("flagged");
            String categories = result.getJSONObject("categories").toString();

            return new ModerationResult(flagged, categories);

        } catch (Exception e) {
            return new ModerationResult(false, "error");
        }
    }
}

