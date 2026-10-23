package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.service.StudySessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
@Tag(name = "Study Sessions", description = "Track preparation hours, stopwatches, and focused sessions")
public class StudySessionController {

    private final StudySessionService sessionService;

    public StudySessionController(StudySessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    @Operation(summary = "Log completed study session or timer output")
    public ResponseEntity<SessionDto> logSession(@AuthenticationPrincipal User user, @Valid @RequestBody CreateSessionRequest req) {
        return ResponseEntity.ok(sessionService.logSession(user, req));
    }

    @GetMapping
    @Operation(summary = "Get study session history")
    public ResponseEntity<List<SessionDto>> getSessions(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(sessionService.getSessions(user));
    }

    @GetMapping("/stats")
    @Operation(summary = "Get aggregated session hours breakdown")
    public ResponseEntity<SessionStatsDto> getStats(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(sessionService.getStats(user));
    }
}
