package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.service.CareerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/career")
@Tag(name = "Career & Interviews", description = "Track job applications, referrals, and mock interviews")
public class CareerController {

    private final CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @GetMapping("/applications")
    @Operation(summary = "Get all career applications")
    public ResponseEntity<List<CareerApplicationDto>> getApplications(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(careerService.getApplications(user));
    }

    @PostMapping("/applications")
    @Operation(summary = "Record a job application")
    public ResponseEntity<CareerApplicationDto> createApplication(@AuthenticationPrincipal User user, @Valid @RequestBody CreateApplicationRequest req) {
        return ResponseEntity.ok(careerService.createApplication(user, req));
    }

    @PutMapping("/applications/{id}")
    @Operation(summary = "Update application status and notes")
    public ResponseEntity<CareerApplicationDto> updateApplication(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody UpdateApplicationRequest req) {
        return ResponseEntity.ok(careerService.updateApplication(user, id, req));
    }

    @DeleteMapping("/applications/{id}")
    @Operation(summary = "Delete an application")
    public ResponseEntity<Void> deleteApplication(@AuthenticationPrincipal User user, @PathVariable Long id) {
        careerService.deleteApplication(user, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/interviews")
    @Operation(summary = "Get mock interview history")
    public ResponseEntity<List<MockInterviewDto>> getMockInterviews(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(careerService.getMockInterviews(user));
    }

    @PostMapping("/interviews")
    @Operation(summary = "Record a mock interview outcome")
    public ResponseEntity<MockInterviewDto> createMockInterview(@AuthenticationPrincipal User user, @Valid @RequestBody CreateMockInterviewRequest req) {
        return ResponseEntity.ok(careerService.createMockInterview(user, req));
    }
}
