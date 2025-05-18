package com.example.springboot.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class LogAnalyzerService {
    @Value("${openrouter.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public LogAnalyzerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String analyzeLog(String logContent) {
        String apiUrl = "https://openrouter.ai/api/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> message = Map.of(
                "role", "user",
                "content", "Вот лог из Java Spring Boot приложения. Проанализируй его и опиши, что пошло не так и как можно исправить:\n\n" + logContent
        );

        Map<String, Object> body = Map.of(
                "model", "deepseek/deepseek-r1:free",
                "messages", List.of(message)
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);

        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
        return (String) ((Map<String, Object>) choices.get(0).get("message")).get("content");
    }
}
