package com.jayasuriya.studytracker;

import com.jayasuriya.studytracker.dto.Dtos.AiChatRequest;
import com.jayasuriya.studytracker.dto.Dtos.AiChatResponse;
import com.jayasuriya.studytracker.dto.Dtos.AuthResponse;
import com.jayasuriya.studytracker.dto.Dtos.RegisterRequest;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.repository.UserRepository;
import com.jayasuriya.studytracker.service.AuthService;
import com.jayasuriya.studytracker.service.ai.AiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AiAssistantTests {

    @Autowired
    private AuthService authService;

    @Autowired
    private AiService aiService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Verify deterministic mock AI provider with hints-first mode and verification notice")
    void testMockAiAssistant() {
        AuthService.LoginResult auth = authService.register(new RegisterRequest(
                "AI Learner", "ailearner", "ai@example.com", "password123",
                "Asia/Kolkata", LocalDate.of(2026, 11, 2)
        ));
        User user = userRepository.findById(auth.user().id()).orElseThrow();

        AiChatResponse response = aiService.chat(user, new AiChatRequest(
                null, "Can you give me a hint for two sum?", "HINTS_FIRST", "TASK", null
        ));

        assertNotNull(response);
        assertNotNull(response.conversationId());
        assertTrue(response.reply().contains("Progressive Hint"), "Should provide progressive hint");
        assertTrue(response.reply().contains("Verification Notice"), "Should include verification disclaimer");
        assertEquals("mock-study-assistant-v1", response.model());
    }
}
