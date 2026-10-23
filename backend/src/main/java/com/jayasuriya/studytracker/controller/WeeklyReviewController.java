package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.service.WeeklyReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@Tag(name = "Weekly Reviews & Journal", description = "Reflections, learnings, and AI verification logs")
public class WeeklyReviewController {

    private final WeeklyReviewService reviewService;

    public WeeklyReviewController(WeeklyReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{weekId}")
    @Operation(summary = "Get weekly review and verification journal for a week")
    public ResponseEntity<WeeklyReviewDto> getReview(@AuthenticationPrincipal User user, @PathVariable Long weekId) {
        WeeklyReviewDto review = reviewService.getReview(user, weekId);
        return review != null ? ResponseEntity.ok(review) : ResponseEntity.noContent().build();
    }

    @PostMapping
    @Operation(summary = "Save or finalize a weekly review and AI verification log")
    public ResponseEntity<WeeklyReviewDto> saveReview(@AuthenticationPrincipal User user, @Valid @RequestBody SaveReviewRequest req) {
        return ResponseEntity.ok(reviewService.saveReview(user, req));
    }
}
