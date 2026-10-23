package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.dto.Dtos.AnalyticsDto;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analytics")
@Tag(name = "Analytics", description = "Performance analytics, velocity, time allocation, and funnel metrics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping
    @Operation(summary = "Get detailed analytics, planned vs actual velocity, category distribution, and streak")
    public ResponseEntity<AnalyticsDto> getAnalytics(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(analyticsService.getAnalytics(user));
    }
}
