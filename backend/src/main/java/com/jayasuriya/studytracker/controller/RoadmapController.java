package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.service.RoadmapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Roadmap & Curriculum", description = "52-week curriculum navigation, weekly plans, and phase progression")
public class RoadmapController {

    private final RoadmapService roadmapService;

    public RoadmapController(RoadmapService roadmapService) {
        this.roadmapService = roadmapService;
    }

    @GetMapping("/roadmap")
    @Operation(summary = "Get user's complete roadmap and phase hierarchy")
    public ResponseEntity<RoadmapDto> getRoadmap(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(roadmapService.getRoadmapForUser(user));
    }

    @GetMapping("/weeks")
    @Operation(summary = "Get summary of all 52 roadmap weeks")
    public ResponseEntity<List<WeekPlanDto>> getWeeks(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(roadmapService.getWeeks(user));
    }

    @GetMapping("/weeks/{weekNumber}")
    @Operation(summary = "Get detailed plan, deliverables, and reflection for a specific week (1-52)")
    public ResponseEntity<WeekDetailDto> getWeekDetail(@AuthenticationPrincipal User user, @PathVariable int weekNumber) {
        return ResponseEntity.ok(roadmapService.getWeekDetail(user, weekNumber));
    }
}
