package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.service.DataPortabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/portability")
@Tag(name = "Data Portability", description = "Complete JSON backup export, import, and CSV ledger downloads")
public class DataPortabilityController {

    private final DataPortabilityService portabilityService;

    public DataPortabilityController(DataPortabilityService portabilityService) {
        this.portabilityService = portabilityService;
    }

    @GetMapping("/export/json")
    @Operation(summary = "Export complete user data graph as JSON backup")
    public ResponseEntity<Map<String, Object>> exportJson(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(portabilityService.exportUserJson(user));
    }

    @PostMapping("/import/json")
    @Operation(summary = "Import data from a previous JSON backup")
    public ResponseEntity<Map<String, Object>> importJson(
            @AuthenticationPrincipal User user,
            @RequestBody Map<String, Object> data) {
        return ResponseEntity.ok(portabilityService.importUserJson(user, data));
    }

    @GetMapping(value = "/export/sessions/csv", produces = "text/csv")
    @Operation(summary = "Export study sessions as CSV")
    public ResponseEntity<String> exportSessionsCsv(@AuthenticationPrincipal User user) {
        String csv = portabilityService.exportSessionsCsv(user);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"study_sessions.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }

    @GetMapping(value = "/export/dsa/csv", produces = "text/csv")
    @Operation(summary = "Export DSA problems as CSV")
    public ResponseEntity<String> exportDsaCsv(@AuthenticationPrincipal User user) {
        String csv = portabilityService.exportDsaCsv(user);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"dsa_problems.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }
}
