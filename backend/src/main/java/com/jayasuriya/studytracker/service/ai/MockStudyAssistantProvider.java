package com.jayasuriya.studytracker.service.ai;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("mockAiProvider")
public class MockStudyAssistantProvider implements StudyAssistantProvider {

    @Override
    public ProviderResponse generateResponse(List<PromptMessage> messages, String systemInstruction) {
        String lastMessage = messages.isEmpty() ? "" : messages.get(messages.size() - 1).content().toLowerCase();

        String response;
        if (lastMessage.contains("hint")) {
            response = """
                    💡 **Progressive Hint 1:**
                    Think about the constraints of your input.
                    - What happens when the array is empty or contains negative numbers?
                    - Can you achieve an O(N) time complexity by maintaining a hash map of frequencies or two pointer boundaries?
                    
                    *Next step:* Try drawing the state transitions on paper before asking for full code!
                    """;
        } else if (lastMessage.contains("quiz")) {
            response = """
                    📝 **Quick Concept Quiz:**
                    1. In Spring Framework, what is the default bean scope, and what threading risks does it present?
                    2. In PostgreSQL, what index type is default for B-Tree vs GIN, and when would you use EXPLAIN ANALYZE?
                    3. Explain the difference between optimistic locking (@Version) and pessimistic locking in JPA.
                    
                    Try answering these without looking up documentation, then verify your answers!
                    """;
        } else if (lastMessage.contains("edge case") || lastMessage.contains("test")) {
            response = """
                    🧪 **Suggested Edge Cases to Test:**
                    1. Empty input / Null collection.
                    2. Extreme boundary values (Integer.MIN_VALUE, Integer.MAX_VALUE).
                    3. Duplicate elements causing unexpected hash collisions or infinite loops.
                    4. Concurrent modifications during traversal.
                    
                    Write explicit JUnit 5 `@ParameterizedTest` cases to verify these behaviors!
                    """;
        } else if (lastMessage.contains("interview")) {
            response = """
                    👔 **Technical Interview Practice Question:**
                    "Imagine you are designing an idempotent credit application submission endpoint in Spring Boot. A client retries the same request 3 times due to network timeouts. How do you prevent duplicate application records in the database, and what HTTP status code do you return?"
                    
                    Structure your answer:
                    1. Functional & non-functional requirements
                    2. Database transaction & idempotency key strategy
                    3. Concurrency edge cases and race conditions
                    """;
        } else {
            response = """
                    🤖 **AI Study Coach:**
                    I analyzed your query: "%s".
                    
                    Here are key considerations based on your roadmap:
                    - Ensure you can explain the time and space complexity (Big-O) independently.
                    - Always write unit tests with Mockito / AssertJ to prove your hypothesis.
                    - Keep the golden rule in mind: Direct the AI, inspect the output, and verify everything with tests.
                    """.formatted(lastMessage.length() > 50 ? lastMessage.substring(0, 50) + "..." : lastMessage);
        }

        return new ProviderResponse(response.trim(), "mock-study-assistant-v1", 120, true, null);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getProviderName() {
        return "mock";
    }

    @Override
    public String getModelName() {
        return "mock-study-assistant-v1";
    }
}
