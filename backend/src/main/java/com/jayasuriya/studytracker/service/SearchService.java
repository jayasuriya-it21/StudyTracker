package com.jayasuriya.studytracker.service;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.*;
import com.jayasuriya.studytracker.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final StudyTaskRepository taskRepository;
    private final DsaProblemRepository dsaRepository;
    private final ProjectRepository projectRepository;
    private final CareerApplicationRepository applicationRepository;
    private final LearningResourceRepository resourceRepository;
    private final RoadmapService roadmapService;
    private final DsaService dsaService;
    private final ProjectService projectService;
    private final CareerService careerService;

    public SearchService(
            StudyTaskRepository taskRepository,
            DsaProblemRepository dsaRepository,
            ProjectRepository projectRepository,
            CareerApplicationRepository applicationRepository,
            LearningResourceRepository resourceRepository,
            RoadmapService roadmapService,
            DsaService dsaService,
            ProjectService projectService,
            CareerService careerService) {
        this.taskRepository = taskRepository;
        this.dsaRepository = dsaRepository;
        this.projectRepository = projectRepository;
        this.applicationRepository = applicationRepository;
        this.resourceRepository = resourceRepository;
        this.roadmapService = roadmapService;
        this.dsaService = dsaService;
        this.projectService = projectService;
        this.careerService = careerService;
    }

    public SearchResultDto search(User user, String query) {
        if (query == null || query.trim().length() < 2) {
            return new SearchResultDto(List.of(), List.of(), List.of(), List.of(), List.of());
        }

        String q = query.trim();

        List<TaskDto> tasks = taskRepository.searchTasks(user.getId(), q)
                .stream().map(roadmapService::toTaskDto).collect(Collectors.toList());

        List<DsaProblemDto> dsa = dsaRepository.searchDsa(user.getId(), q)
                .stream().map(dsaService::toDto).collect(Collectors.toList());

        List<ProjectDto> projects = projectRepository.searchProjects(user.getId(), q)
                .stream().map(projectService::toDto).collect(Collectors.toList());

        List<CareerApplicationDto> applications = applicationRepository.searchApplications(user.getId(), q)
                .stream().map(careerService::toDto).collect(Collectors.toList());

        List<ResourceDto> resources = resourceRepository.searchResources(user.getId(), q)
                .stream().map(r -> new ResourceDto(r.getId(), r.getTitle(), r.getUrl(), r.getResourceType(), r.getCategory(), r.getStatus(), r.getNotes()))
                .collect(Collectors.toList());

        return new SearchResultDto(tasks, dsa, projects, applications, resources);
    }
}
