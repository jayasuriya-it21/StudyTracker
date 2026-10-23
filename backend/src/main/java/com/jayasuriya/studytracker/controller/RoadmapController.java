package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.service.RoadmapService;
import com.jayasuriya.studytracker.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Roadmap & Tasks", description = "52-week curriculum navigation, weekly plans, and tasks management")
public class RoadmapController {

    private final RoadmapService roadmapService;
    private final TaskService taskService;

    public RoadmapController(RoadmapService roadmapService, TaskService taskService) {
        this.roadmapService = roadmapService;
        this.taskService = taskService;
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

    @PostMapping("/tasks")
    @Operation(summary = "Create a new custom study task for a week")
    public ResponseEntity<TaskDto> createTask(@AuthenticationPrincipal User user, @Valid @RequestBody CreateTaskRequest req) {
        return ResponseEntity.ok(taskService.createTask(user, req));
    }

    @PutMapping("/tasks/{id}")
    @Operation(summary = "Update an existing task")
    public ResponseEntity<TaskDto> updateTask(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody UpdateTaskRequest req) {
        return ResponseEntity.ok(taskService.updateTask(user, id, req));
    }

    @PatchMapping("/tasks/{id}/toggle")
    @Operation(summary = "Toggle task completion status")
    public ResponseEntity<TaskDto> toggleTask(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return ResponseEntity.ok(taskService.toggleTask(user, id));
    }

    @DeleteMapping("/tasks/{id}")
    @Operation(summary = "Delete a task")
    public ResponseEntity<Void> deleteTask(@AuthenticationPrincipal User user, @PathVariable Long id) {
        taskService.deleteTask(user, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/tasks/{id}/carry-forward")
    @Operation(summary = "Carry forward an incomplete task to next week with audit trail")
    public ResponseEntity<TaskDto> carryForwardTask(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @RequestBody CarryForwardRequest req) {
        return ResponseEntity.ok(taskService.carryForwardTask(user, id, req.targetWeekId()));
    }
}
