package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.dto.Dtos.DashboardSummaryDto;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard", description = "High-level metrics, sprint countdown, and active week focus")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @Operation(summary = "Get high-level dashboard metrics, today's tasks, overdue items, and countdown")
    public ResponseEntity<DashboardSummaryDto> getDashboard(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dashboardService.getDashboardSummary(user));
    }
}
