package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.service.DsaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dsa")
@Tag(name = "DSA Tracker", description = "Track problem solving progress toward the 120-160 goal")
public class DsaController {

    private final DsaService dsaService;

    public DsaController(DsaService dsaService) {
        this.dsaService = dsaService;
    }

    @PostMapping
    @Operation(summary = "Record a solved or attempted DSA problem")
    public ResponseEntity<DsaProblemDto> createProblem(@AuthenticationPrincipal User user, @Valid @RequestBody CreateDsaRequest req) {
        return ResponseEntity.ok(dsaService.createProblem(user, req));
    }

    @GetMapping
    @Operation(summary = "Get all recorded DSA problems")
    public ResponseEntity<List<DsaProblemDto>> getProblems(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dsaService.getProblems(user));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a DSA problem record")
    public ResponseEntity<DsaProblemDto> updateProblem(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody UpdateDsaRequest req) {
        return ResponseEntity.ok(dsaService.updateProblem(user, id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a DSA problem")
    public ResponseEntity<Void> deleteProblem(@AuthenticationPrincipal User user, @PathVariable Long id) {
        dsaService.deleteProblem(user, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    @Operation(summary = "Get DSA statistics, weak patterns, and progress to target")
    public ResponseEntity<DsaStatsDto> getStats(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dsaService.getStats(user));
    }

    @GetMapping("/revision-queue")
    @Operation(summary = "Get problems scheduled for spaced-repetition review")
    public ResponseEntity<List<DsaProblemDto>> getRevisionQueue(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dsaService.getRevisionQueue(user));
    }
}
