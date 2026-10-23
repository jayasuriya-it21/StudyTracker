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

@Component("ollamaAiProvider")
public class OllamaProvider implements StudyAssistantProvider {

    private static final Logger log = LoggerFactory.getLogger(OllamaProvider.class);

    @Value("${ai.base-url:http://localhost:11434}")
    private String baseUrl;

    @Value("${ai.model:llama3.2}")
    private String model;

    @Value("${ai.timeout-seconds:60}")
    private int timeoutSeconds;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ProviderResponse generateResponse(List<PromptMessage> messages, String systemInstruction) {
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
            body.put("stream", false);

            String jsonPayload = objectMapper.writeValueAsString(body);

            String endpoint = baseUrl.endsWith("/") ? baseUrl + "api/chat" : baseUrl + "/api/chat";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .timeout(Duration.ofSeconds(timeoutSeconds))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                JsonNode root = objectMapper.readTree(response.body());
                String content = root.path("message").path("content").asText();
                int tokens = root.path("prompt_eval_count").asInt(0) + root.path("eval_count").asInt(0);
                return new ProviderResponse(content, model, tokens, true, null);
            } else {
                return new ProviderResponse(null, model, 0, false, "Ollama returned HTTP " + response.statusCode());
            }

        } catch (Exception e) {
            log.warn("Failed to reach Ollama endpoint at {}", baseUrl, e);
            return new ProviderResponse(null, model, 0, false, "Local Ollama is unreachable at " + baseUrl + ": " + e.getMessage());
        }
    }

    @Override
    public boolean isAvailable() {
        try {
            String checkUrl = baseUrl.endsWith("/") ? baseUrl + "api/tags" : baseUrl + "/api/tags";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(checkUrl))
                    .timeout(Duration.ofSeconds(2))
                    .GET()
                    .build();
            HttpResponse<Void> resp = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            return resp.statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getProviderName() {
        return "ollama";
    }

    @Override
    public String getModelName() {
        return model;
    }
}
