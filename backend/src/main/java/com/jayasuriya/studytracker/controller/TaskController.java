package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks", description = "Create, toggle, update, carry forward, and delete study deliverables")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Operation(summary = "Create a new custom study task for a week")
    public ResponseEntity<TaskDto> createTask(@AuthenticationPrincipal User user, @Valid @RequestBody CreateTaskRequest req) {
        return ResponseEntity.ok(taskService.createTask(user, req));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing task")
    public ResponseEntity<TaskDto> updateTask(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody UpdateTaskRequest req) {
        return ResponseEntity.ok(taskService.updateTask(user, id, req));
    }

    @PatchMapping("/{id}/toggle")
    @Operation(summary = "Toggle task completion status")
    public ResponseEntity<TaskDto> toggleTask(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return ResponseEntity.ok(taskService.toggleTask(user, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task")
    public ResponseEntity<Void> deleteTask(@AuthenticationPrincipal User user, @PathVariable Long id) {
        taskService.deleteTask(user, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/carry-forward")
    @Operation(summary = "Carry forward an incomplete task to next week with audit trail")
    public ResponseEntity<TaskDto> carryForwardTask(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @RequestBody CarryForwardRequest req) {
        return ResponseEntity.ok(taskService.carryForwardTask(user, id, req.targetWeekId()));
    }
}
