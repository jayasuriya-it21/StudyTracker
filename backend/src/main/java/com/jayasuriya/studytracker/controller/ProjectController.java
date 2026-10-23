package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@Tag(name = "Portfolio Projects", description = "Track flagship and secondary portfolio projects and milestones")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    @Operation(summary = "Get all portfolio projects and milestones")
    public ResponseEntity<List<ProjectDto>> getProjects(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(projectService.getProjects(user));
    }

    @PostMapping
    @Operation(summary = "Create a new project")
    public ResponseEntity<ProjectDto> createProject(@AuthenticationPrincipal User user, @Valid @RequestBody CreateProjectRequest req) {
        return ResponseEntity.ok(projectService.createProject(user, req));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update project status and progress percentage")
    public ResponseEntity<ProjectDto> updateProject(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody UpdateProjectRequest req) {
        return ResponseEntity.ok(projectService.updateProject(user, id, req));
    }

    @PostMapping("/{id}/milestones")
    @Operation(summary = "Add a milestone to a project")
    public ResponseEntity<ProjectMilestoneDto> createMilestone(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @RequestBody CreateMilestoneRequest req) {
        return ResponseEntity.ok(projectService.createMilestone(user, id, req));
    }

    @PatchMapping("/milestones/{milestoneId}/toggle")
    @Operation(summary = "Toggle milestone completed status")
    public ResponseEntity<ProjectMilestoneDto> toggleMilestone(@AuthenticationPrincipal User user, @PathVariable Long milestoneId) {
        return ResponseEntity.ok(projectService.toggleMilestone(user, milestoneId));
    }
}
