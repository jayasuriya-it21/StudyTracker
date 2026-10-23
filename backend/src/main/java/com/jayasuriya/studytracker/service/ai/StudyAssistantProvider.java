package com.jayasuriya.studytracker.service.ai;

import java.util.List;

public interface StudyAssistantProvider {

    record PromptMessage(String role, String content) {}

    record ProviderResponse(
            String content,
            String model,
            int tokensUsed,
            boolean isSuccess,
            String errorMessage
    ) {}

    ProviderResponse generateResponse(List<PromptMessage> messages, String systemInstruction);

    boolean isAvailable();

    String getProviderName();

    String getModelName();
}
