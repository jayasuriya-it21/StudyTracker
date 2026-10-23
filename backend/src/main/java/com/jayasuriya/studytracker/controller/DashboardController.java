package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.service.*;
import com.jayasuriya.studytracker.service.ai.AiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Dashboard, Analytics & System", description = "Metrics, full-text search, data backup, and AI coach")
public class DashboardController {

    private final DashboardService dashboardService;
    private final AnalyticsService analyticsService;
    private final SearchService searchService;
    private final DataPortabilityService portabilityService;
    private final AiService aiService;

    public DashboardController(
            DashboardService dashboardService,
            AnalyticsService analyticsService,
            SearchService searchService,
            DataPortabilityService portabilityService,
            AiService aiService) {
        this.dashboardService = dashboardService;
        this.analyticsService = analyticsService;
        this.searchService = searchService;
        this.portabilityService = portabilityService;
        this.aiService = aiService;
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Get high-level dashboard metrics, today's tasks, overdue items, and countdown")
    public ResponseEntity<DashboardSummaryDto> getDashboard(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dashboardService.getDashboardSummary(user));
    }

    @GetMapping("/analytics")
    @Operation(summary = "Get detailed analytics, planned vs actual velocity, category distribution, and streak")
    public ResponseEntity<AnalyticsDto> getAnalytics(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(analyticsService.getAnalytics(user));
    }

    @GetMapping("/search")
    @Operation(summary = "Unified search across tasks, DSA problems, projects, and applications")
    public ResponseEntity<SearchResultDto> search(@AuthenticationPrincipal User user, @RequestParam("q") String query) {
        return ResponseEntity.ok(searchService.search(user, query));
    }

    @GetMapping("/portability/export/json")
    @Operation(summary = "Export complete user data graph as JSON backup")
    public ResponseEntity<Map<String, Object>> exportJson(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(portabilityService.exportUserJson(user));
    }

    @PostMapping("/portability/import/json")
    @Operation(summary = "Import data from a previous JSON backup")
    public ResponseEntity<Map<String, Object>> importJson(@AuthenticationPrincipal User user, @RequestBody Map<String, Object> data) {
        return ResponseEntity.ok(portabilityService.importUserJson(user, data));
    }

    @GetMapping(value = "/portability/export/sessions/csv", produces = "text/csv")
    @Operation(summary = "Export study sessions as CSV")
    public ResponseEntity<String> exportSessionsCsv(@AuthenticationPrincipal User user) {
        String csv = portabilityService.exportSessionsCsv(user);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"study_sessions.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }

    @GetMapping(value = "/portability/export/dsa/csv", produces = "text/csv")
    @Operation(summary = "Export DSA problems as CSV")
    public ResponseEntity<String> exportDsaCsv(@AuthenticationPrincipal User user) {
        String csv = portabilityService.exportDsaCsv(user);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"dsa_problems.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }

    @GetMapping("/ai/status")
    @Operation(summary = "Check AI Assistant availability and remaining quota")
    public ResponseEntity<AiStatusDto> getAiStatus(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(aiService.getStatus(user.getId()));
    }

    @PostMapping("/ai/chat")
    @Operation(summary = "Send query to AI study assistant with hints-first and safety controls")
    public ResponseEntity<AiChatResponse> chatAi(@AuthenticationPrincipal User user, @Valid @RequestBody AiChatRequest req) {
        return ResponseEntity.ok(aiService.chat(user, req));
    }

    @PostMapping("/ai/messages/{id}/rate")
    @Operation(summary = "Rate an AI message for the verification journal (VERIFIED, PARTIALLY_CORRECT, INCORRECT)")
    public ResponseEntity<Void> rateAiMessage(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @RequestBody RateAiMessageRequest req) {
        aiService.rateMessage(id, user.getId(), req.rating());
        return ResponseEntity.noContent().build();
    }
}
