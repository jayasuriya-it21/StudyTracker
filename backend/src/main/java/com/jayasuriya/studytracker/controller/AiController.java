package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.service.ai.AiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@Tag(name = "AI Study Coach", description = "Educational AI assistant with Socratic hints-first mode, quotas, and verification ratings")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/status")
    @Operation(summary = "Check AI Assistant availability, active provider, and daily quota")
    public ResponseEntity<AiStatusDto> getAiStatus(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(aiService.getStatus(user.getId()));
    }

    @PostMapping("/chat")
    @Operation(summary = "Send query to AI study assistant with hints-first pedagogy and safety sanitization")
    public ResponseEntity<AiChatResponse> chatAi(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody AiChatRequest req) {
        return ResponseEntity.ok(aiService.chat(user, req));
    }

    @PostMapping("/messages/{id}/rate")
    @Operation(summary = "Rate an AI assistant message for the verification journal (VERIFIED, PARTIALLY_CORRECT, INCORRECT)")
    public ResponseEntity<Void> rateAiMessage(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @RequestBody RateAiMessageRequest req) {
        aiService.rateMessage(id, user.getId(), req.rating());
        return ResponseEntity.noContent().build();
    }
}
