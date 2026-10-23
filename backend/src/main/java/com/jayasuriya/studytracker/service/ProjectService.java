package com.jayasuriya.studytracker.service;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.exception.Exceptions.BadRequestException;
import com.jayasuriya.studytracker.exception.Exceptions.ResourceNotFoundException;
import com.jayasuriya.studytracker.model.*;
import com.jayasuriya.studytracker.repository.ProjectMilestoneRepository;
import com.jayasuriya.studytracker.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMilestoneRepository milestoneRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            ProjectMilestoneRepository milestoneRepository) {
        this.projectRepository = projectRepository;
        this.milestoneRepository = milestoneRepository;
    }

    public List<ProjectDto> getProjects(User user) {
        return projectRepository.findByOwnerIdOrderByCreatedAtAsc(user.getId())
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ProjectDto createProject(User user, CreateProjectRequest req) {
        Project p = new Project();
        p.setOwner(user);
        p.setTitle(req.title());
        p.setProjectType(req.projectType() != null ? req.projectType() : "Secondary");
        p.setDescription(req.description());
        p.setTechStack(req.techStack());
        p.setStartDate(req.startDate());
        p.setTargetDate(req.targetDate());
        p.setRepositoryUrl(req.repositoryUrl());
        p.setDemoUrl(req.demoUrl());
        p.setNotes(req.notes());
        p.setStatus(Enums.ProjectStatus.NOT_STARTED);
        p.setProgressPercentage(0);

        p = projectRepository.save(p);
        return toDto(p);
    }

    @Transactional
    public ProjectDto updateProject(User user, Long id, UpdateProjectRequest req) {
        Project p = projectRepository.findByIdAndOwnerId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        if (req.title() != null && !req.title().isBlank()) p.setTitle(req.title());
        if (req.projectType() != null) p.setProjectType(req.projectType());
        if (req.description() != null) p.setDescription(req.description());
        if (req.techStack() != null) p.setTechStack(req.techStack());
        if (req.status() != null) p.setStatus(req.status());
        if (req.progressPercentage() != null) p.setProgressPercentage(req.progressPercentage());
        if (req.repositoryUrl() != null) p.setRepositoryUrl(req.repositoryUrl());
        if (req.demoUrl() != null) p.setDemoUrl(req.demoUrl());
        if (req.startDate() != null) p.setStartDate(req.startDate());
        if (req.targetDate() != null) p.setTargetDate(req.targetDate());
        if (req.notes() != null) p.setNotes(req.notes());

        p = projectRepository.save(p);
        return toDto(p);
    }

    @Transactional
    public ProjectMilestoneDto createMilestone(User user, Long projectId, CreateMilestoneRequest req) {
        Project p = projectRepository.findByIdAndOwnerId(projectId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        ProjectMilestone m = new ProjectMilestone();
        m.setProject(p);
        m.setTitle(req.title());
        m.setDescription(req.description());
        m.setDueDate(req.dueDate());
        m.setStatus(Enums.MilestoneStatus.PENDING);

        m = milestoneRepository.save(m);
        return toMilestoneDto(m);
    }

    @Transactional
    public ProjectMilestoneDto toggleMilestone(User user, Long milestoneId) {
        ProjectMilestone m = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Milestone not found"));

        if (!m.getProject().getOwner().getId().equals(user.getId())) {
            throw new BadRequestException("You do not own this milestone");
        }

        if (m.getStatus() == Enums.MilestoneStatus.COMPLETED) {
            m.setStatus(Enums.MilestoneStatus.PENDING);
            m.setCompletedDate(null);
        } else {
            m.setStatus(Enums.MilestoneStatus.COMPLETED);
            m.setCompletedDate(LocalDate.now());
        }

        m = milestoneRepository.save(m);
        return toMilestoneDto(m);
    }

    public ProjectDto toDto(Project p) {
        List<ProjectMilestone> milestones = milestoneRepository.findByProjectId(p.getId());
        List<ProjectMilestoneDto> milestoneDtos = milestones.stream().map(this::toMilestoneDto).collect(Collectors.toList());

        return new ProjectDto(
                p.getId(),
                p.getTitle(),
                p.getProjectType(),
                p.getDescription(),
                p.getTechStack(),
                p.getStatus(),
                p.getProgressPercentage(),
                p.getRepositoryUrl(),
                p.getDemoUrl(),
                p.getStartDate(),
                p.getTargetDate(),
                p.getNotes(),
                milestoneDtos
        );
    }

    public ProjectMilestoneDto toMilestoneDto(ProjectMilestone m) {
        return new ProjectMilestoneDto(
                m.getId(),
                m.getProject().getId(),
                m.getTitle(),
                m.getDescription(),
                m.getDueDate(),
                m.getStatus(),
                m.getCompletedDate()
        );
    }
}
