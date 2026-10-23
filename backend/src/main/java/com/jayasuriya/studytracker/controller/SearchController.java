package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.dto.Dtos.SearchResultDto;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search")
@Tag(name = "Search", description = "Unified full-text search across all user entities")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    @Operation(summary = "Unified search across tasks, DSA problems, projects, and applications")
    public ResponseEntity<SearchResultDto> search(
            @AuthenticationPrincipal User user,
            @RequestParam("q") String query) {
        return ResponseEntity.ok(searchService.search(user, query));
    }
}
