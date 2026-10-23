package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.dto.Dtos.ResourceDto;
import com.jayasuriya.studytracker.model.LearningResource;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.repository.LearningResourceRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resources")
@Tag(name = "Learning Resources", description = "Articles, guides, documentation, and external references")
public class LearningResourceController {

    private final LearningResourceRepository resourceRepository;

    public LearningResourceController(LearningResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @GetMapping
    @Operation(summary = "Get user's saved learning resources")
    public ResponseEntity<List<ResourceDto>> getResources(@AuthenticationPrincipal User user) {
        List<ResourceDto> dtos = resourceRepository.findByOwnerIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(r -> new ResourceDto(
                        r.getId(),
                        r.getTitle(),
                        r.getUrl(),
                        r.getResourceType(),
                        r.getCategory(),
                        r.getStatus(),
                        r.getNotes()
                ))
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    @Operation(summary = "Save a new learning resource")
    public ResponseEntity<ResourceDto> createResource(
            @AuthenticationPrincipal User user,
            @RequestBody ResourceDto req) {
        LearningResource r = new LearningResource();
        r.setOwner(user);
        r.setTitle(req.title());
        r.setUrl(req.url());
        if (req.resourceType() != null) r.setResourceType(req.resourceType());
        r.setCategory(req.category() != null ? req.category() : "General");
        if (req.status() != null) r.setStatus(req.status());
        r.setNotes(req.notes());

        r = resourceRepository.save(r);
        return ResponseEntity.ok(new ResourceDto(
                r.getId(), r.getTitle(), r.getUrl(), r.getResourceType(), r.getCategory(), r.getStatus(), r.getNotes()
        ));
    }
}
