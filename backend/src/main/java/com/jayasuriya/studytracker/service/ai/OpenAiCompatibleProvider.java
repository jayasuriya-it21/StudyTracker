package com.jayasuriya.studytracker.service.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("openaiAiProvider")
public class OpenAiCompatibleProvider implements StudyAssistantProvider {

    private static final Logger log = LoggerFactory.getLogger(OpenAiCompatibleProvider.class);

    @Value("${ai.base-url:https://api.openai.com/v1}")
    private String baseUrl;

    @Value("${ai.api-key:}")
    private String apiKey;

    @Value("${ai.model:gpt-4o-mini}")
    private String model;

    @Value("${ai.timeout-seconds:30}")
    private int timeoutSeconds;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ProviderResponse generateResponse(List<PromptMessage> messages, String systemInstruction) {
        if (!isAvailable()) {
            return new ProviderResponse(null, model, 0, false, "OpenAI provider API key is not configured");
        }

        try {
            List<Map<String, String>> requestMessages = new ArrayList<>();
            if (systemInstruction != null && !systemInstruction.isBlank()) {
                requestMessages.add(Map.of("role", "system", "content", systemInstruction));
            }
            for (PromptMessage pm : messages) {
                requestMessages.add(Map.of("role", pm.role(), "content", pm.content()));
            }

            Map<String, Object> body = new HashMap<>();
            body.put("model", model);
            body.put("messages", requestMessages);
            body.put("temperature", 0.7);

            String jsonPayload = objectMapper.writeValueAsString(body);

            String endpoint = baseUrl.endsWith("/") ? baseUrl + "chat/completions" : baseUrl + "/chat/completions";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .timeout(Duration.ofSeconds(timeoutSeconds))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                JsonNode root = objectMapper.readTree(response.body());
                String content = root.path("choices").path(0).path("message").path("content").asText();
                int tokens = root.path("usage").path("total_tokens").asInt(0);
                return new ProviderResponse(content, model, tokens, true, null);
            } else {
                log.warn("OpenAI API returned non-200 code: {}", response.statusCode());
                return new ProviderResponse(null, model, 0, false, "Provider returned HTTP " + response.statusCode());
            }

        } catch (Exception e) {
            log.error("Failed to query OpenAI-compatible provider", e);
            return new ProviderResponse(null, model, 0, false, "Error communicating with AI provider: " + e.getMessage());
        }
    }

    @Override
    public boolean isAvailable() {
        return apiKey != null && !apiKey.trim().isEmpty();
    }

    @Override
    public String getProviderName() {
        return "openai";
    }

    @Override
    public String getModelName() {
        return model;
    }
}
