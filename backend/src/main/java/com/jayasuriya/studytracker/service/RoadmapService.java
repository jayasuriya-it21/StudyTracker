package com.jayasuriya.studytracker.service;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.exception.Exceptions.ResourceNotFoundException;
import com.jayasuriya.studytracker.model.*;
import com.jayasuriya.studytracker.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoadmapService {

    private final RoadmapRepository roadmapRepository;
    private final PhaseRepository phaseRepository;
    private final WeekPlanRepository weekPlanRepository;
    private final StudyTaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMilestoneRepository milestoneRepository;
    private final WeeklyReviewRepository reviewRepository;
    private final StudySessionRepository sessionRepository;

    public RoadmapService(
            RoadmapRepository roadmapRepository,
            PhaseRepository phaseRepository,
            WeekPlanRepository weekPlanRepository,
            StudyTaskRepository taskRepository,
            ProjectRepository projectRepository,
            ProjectMilestoneRepository milestoneRepository,
            WeeklyReviewRepository reviewRepository,
            StudySessionRepository sessionRepository) {
        this.roadmapRepository = roadmapRepository;
        this.phaseRepository = phaseRepository;
        this.weekPlanRepository = weekPlanRepository;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.milestoneRepository = milestoneRepository;
        this.reviewRepository = reviewRepository;
        this.sessionRepository = sessionRepository;
    }

    @Transactional
    public void initializeUserRoadmap(User user) {
        // Find template roadmap
        Roadmap template = roadmapRepository.findFirstByIsTemplateTrue()
                .orElseGet(() -> {
                    // Create default template if missing
                    Roadmap tmpl = new Roadmap();
                    tmpl.setTitle("AI-Ready TCS-to-Product-Company Career Plan (2026–2027)");
                    tmpl.setStartDate(LocalDate.of(2026, 11, 2));
                    tmpl.setEndDate(LocalDate.of(2027, 10, 31));
                    tmpl.setTargetRole("AI-augmented Software Engineer → Secure Product Engineer");
                    tmpl.setTemplate(true);
                    return roadmapRepository.save(tmpl);
                });

        // Create user's personal roadmap
        Roadmap userRoadmap = new Roadmap();
        userRoadmap.setOwner(user);
        userRoadmap.setTitle(template.getTitle());
        userRoadmap.setStartDate(user.getRoadmapStartDate() != null ? user.getRoadmapStartDate() : template.getStartDate());
        userRoadmap.setEndDate(userRoadmap.getStartDate().plusDays(52 * 7 - 1));
        userRoadmap.setTargetRole(template.getTargetRole());
        userRoadmap.setTargetDsaMin(template.getTargetDsaMin());
        userRoadmap.setTargetDsaMax(template.getTargetDsaMax());
        userRoadmap.setTargetWeeklyHours(user.getWeeklyTargetHours());
        userRoadmap.setTemplate(false);
        userRoadmap.setActive(true);
        userRoadmap = roadmapRepository.save(userRoadmap);

        // Copy Phases
        List<Phase> templatePhases = phaseRepository.findByRoadmapIdOrderByDisplayOrderAsc(template.getId());
        Map<String, Phase> userPhaseMap = new HashMap<>();
        for (Phase tp : templatePhases) {
            Phase up = new Phase();
            up.setRoadmap(userRoadmap);
            up.setCode(tp.getCode());
            up.setTitle(tp.getTitle());
            up.setDescription(tp.getDescription());
            up.setStartWeek(tp.getStartWeek());
            up.setEndWeek(tp.getEndWeek());
            up.setColor(tp.getColor());
            up.setIcon(tp.getIcon());
            up.setDisplayOrder(tp.getDisplayOrder());
            up = phaseRepository.save(up);
            userPhaseMap.put(up.getCode(), up);
        }

        // Copy Weeks & Tasks
        List<WeekPlan> templateWeeks = weekPlanRepository.findByRoadmapIdOrderByWeekNumberAsc(template.getId());
        LocalDate planStart = userRoadmap.getStartDate();

        for (WeekPlan tw : templateWeeks) {
            WeekPlan uw = new WeekPlan();
            uw.setRoadmap(userRoadmap);
            uw.setWeekNumber(tw.getWeekNumber());
            LocalDate weekStart = planStart.plusDays((tw.getWeekNumber() - 1) * 7L);
            uw.setStartDate(weekStart);
            uw.setEndDate(weekStart.plusDays(6));
            if (tw.getPhase() != null) {
                uw.setPhase(userPhaseMap.get(tw.getPhase().getCode()));
            }
            uw.setEngineeringFocus(tw.getEngineeringFocus());
            uw.setAiReadinessDeliverable(tw.getAiReadinessDeliverable());
            uw.setMilestoneDescription(tw.getMilestoneDescription());
            uw.setPlannedHours(tw.getPlannedHours());
            uw.setStatus(Enums.TaskStatus.NOT_STARTED);
            uw.setCompletionPercentage(0);
            uw = weekPlanRepository.save(uw);

            // Copy Tasks for this week
            List<StudyTask> templateTasks = taskRepository.findByWeekPlanIdAndOwnerIsNullOrderBySortOrderAsc(tw.getId());
            for (StudyTask tt : templateTasks) {
                StudyTask ut = new StudyTask();
                ut.setWeekPlan(uw);
                ut.setOwner(user);
                ut.setTitle(tt.getTitle());
                ut.setDescription(tt.getDescription());
                ut.setCategory(tt.getCategory());
                ut.setPriority(tt.getPriority());
                ut.setEstimateMinutes(tt.getEstimateMinutes());
                ut.setActualMinutes(0);
                ut.setDueDate(uw.getEndDate());
                ut.setStatus(Enums.TaskStatus.NOT_STARTED);
                ut.setSource(Enums.TaskSource.SEEDED);
                ut.setSortOrder(tt.getSortOrder());
                taskRepository.save(ut);
            }
        }

        // Initialize 3 default portfolio projects
        seedDefaultProjectsForUser(user);
    }

    private void seedDefaultProjectsForUser(User user) {
        // Project 1: Flagship Secure Credit Workflow Copilot
        Project p1 = new Project();
        p1.setOwner(user);
        p1.setTitle("Secure Credit Workflow Copilot");
        p1.setProjectType("Flagship");
        p1.setDescription("RAG-powered fictional credit application explanation copilot using Spring AI, PostgreSQL/pgvector, and Flutter. Restricts document retrieval strictly by backend user permissions.");
        p1.setTechStack("Java 21, Spring Boot 3, Spring AI, PostgreSQL, pgvector, Flutter, Docker");
        p1.setStatus(Enums.ProjectStatus.NOT_STARTED);
        p1.setProgressPercentage(0);
        p1 = projectRepository.save(p1);

        createMilestone(p1, "Synthetic Data Model & Spring Boot API", LocalDate.of(2027, 4, 30));
        createMilestone(p1, "Document Chunking & pgvector Retrieval", LocalDate.of(2027, 5, 15));
        createMilestone(p1, "Grounded QA with Citation Snippets", LocalDate.of(2027, 5, 31));
        createMilestone(p1, "Flutter Client & Role-Based Access Control", LocalDate.of(2027, 6, 15));
        createMilestone(p1, "Automated Hallucination & Accuracy Evaluations", LocalDate.of(2027, 6, 30));

        // Project 2: AI-Assisted Defect Triage Lab
        Project p2 = new Project();
        p2.setOwner(user);
        p2.setTitle("AI-Assisted Defect Triage Lab");
        p2.setProjectType("Secondary");
        p2.setDescription("Categorizes synthetic bug reports, predicts affected components, and proposes regression test suites with human-in-the-loop review.");
        p2.setTechStack("Java 21, Spring Boot, JUnit 5, Mockito, LLM API");
        p2.setStatus(Enums.ProjectStatus.NOT_STARTED);
        p2.setProgressPercentage(0);
        projectRepository.save(p2);

        // Project 3: Secure Mobile/API Demo
        Project p3 = new Project();
        p3.setOwner(user);
        p3.setTitle("Secure Mobile/API Demo");
        p3.setProjectType("Secondary");
        p3.setDescription("Hardened Flutter client with JWT/OAuth2, encrypted secure storage, and CI/CD security scanning pipeline.");
        p3.setTechStack("Flutter, Java/Spring Boot, OWASP ZAP, GitHub Actions");
        p3.setStatus(Enums.ProjectStatus.NOT_STARTED);
        p3.setProgressPercentage(0);
        projectRepository.save(p3);
    }

    private void createMilestone(Project project, String title, LocalDate dueDate) {
        ProjectMilestone m = new ProjectMilestone();
        m.setProject(project);
        m.setTitle(title);
        m.setDueDate(dueDate);
        m.setStatus(Enums.MilestoneStatus.PENDING);
        milestoneRepository.save(m);
    }

    public RoadmapDto getRoadmapForUser(User user) {
        Roadmap roadmap = roadmapRepository.findByOwnerIdAndIsActiveTrue(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active roadmap not found for user"));

        List<Phase> phases = phaseRepository.findByRoadmapIdOrderByDisplayOrderAsc(roadmap.getId());
        List<WeekPlan> weeks = weekPlanRepository.findByRoadmapIdOrderByWeekNumberAsc(roadmap.getId());

        Map<Long, List<WeekPlan>> weeksByPhase = weeks.stream()
                .filter(w -> w.getPhase() != null)
                .collect(Collectors.groupingBy(w -> w.getPhase().getId()));

        List<PhaseDto> phaseDtos = phases.stream().map(p -> {
            List<WeekPlan> phaseWeeks = weeksByPhase.getOrDefault(p.getId(), Collections.emptyList());
            int totalTasks = 0;
            int completedTasks = 0;
            for (WeekPlan pw : phaseWeeks) {
                List<StudyTask> tasks = taskRepository.findByWeekPlanIdAndOwnerIdOrderBySortOrderAsc(pw.getId(), user.getId());
                totalTasks += tasks.size();
                completedTasks += (int) tasks.stream().filter(t -> t.getStatus() == Enums.TaskStatus.COMPLETED).count();
            }
            int pct = totalTasks > 0 ? (int) Math.round((double) completedTasks / totalTasks * 100) : 0;
            return new PhaseDto(
                    p.getId(),
                    p.getCode(),
                    p.getTitle(),
                    p.getDescription(),
                    p.getStartWeek(),
                    p.getEndWeek(),
                    p.getColor(),
                    p.getIcon(),
                    p.getDisplayOrder(),
                    pct
            );
        }).collect(Collectors.toList());

        return new RoadmapDto(
                roadmap.getId(),
                roadmap.getTitle(),
                roadmap.getStartDate(),
                roadmap.getEndDate(),
                roadmap.getTargetRole(),
                roadmap.getTargetDsaMin(),
                roadmap.getTargetDsaMax(),
                roadmap.getTargetWeeklyHours(),
                phaseDtos
        );
    }

    public List<WeekPlanDto> getWeeks(User user) {
        Roadmap roadmap = roadmapRepository.findByOwnerIdAndIsActiveTrue(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active roadmap not found for user"));

        List<WeekPlan> weeks = weekPlanRepository.findByRoadmapIdOrderByWeekNumberAsc(roadmap.getId());

        return weeks.stream().map(w -> {
            List<StudyTask> tasks = taskRepository.findByWeekPlanIdAndOwnerIdOrderBySortOrderAsc(w.getId(), user.getId());
            int total = tasks.size();
            int completed = (int) tasks.stream().filter(t -> t.getStatus() == Enums.TaskStatus.COMPLETED).count();
            int pct = total > 0 ? (int) Math.round((double) completed / total * 100) : 0;

            return new WeekPlanDto(
                    w.getId(),
                    w.getWeekNumber(),
                    w.getStartDate(),
                    w.getEndDate(),
                    w.getPhase() != null ? w.getPhase().getCode() : "A",
                    w.getPhase() != null ? w.getPhase().getTitle() : "",
                    w.getEngineeringFocus(),
                    w.getAiReadinessDeliverable(),
                    w.getMilestoneDescription(),
                    w.getPlannedHours(),
                    w.getStatus(),
                    pct,
                    total,
                    completed
            );
        }).collect(Collectors.toList());
    }

    public WeekDetailDto getWeekDetail(User user, int weekNumber) {
        Roadmap roadmap = roadmapRepository.findByOwnerIdAndIsActiveTrue(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active roadmap not found for user"));

        WeekPlan week = weekPlanRepository.findByRoadmapIdAndWeekNumber(roadmap.getId(), weekNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Week " + weekNumber + " not found"));

        List<StudyTask> tasks = taskRepository.findByWeekPlanIdAndOwnerIdOrderBySortOrderAsc(week.getId(), user.getId());
        List<TaskDto> taskDtos = tasks.stream().map(this::toTaskDto).collect(Collectors.toList());

        int total = tasks.size();
        int completed = (int) tasks.stream().filter(t -> t.getStatus() == Enums.TaskStatus.COMPLETED).count();
        int pct = total > 0 ? (int) Math.round((double) completed / total * 100) : 0;

        WeekPlanDto weekPlanDto = new WeekPlanDto(
                week.getId(),
                week.getWeekNumber(),
                week.getStartDate(),
                week.getEndDate(),
                week.getPhase() != null ? week.getPhase().getCode() : "A",
                week.getPhase() != null ? week.getPhase().getTitle() : "",
                week.getEngineeringFocus(),
                week.getAiReadinessDeliverable(),
                week.getMilestoneDescription(),
                week.getPlannedHours(),
                week.getStatus(),
                pct,
                total,
                completed
        );

        WeeklyReviewDto reviewDto = reviewRepository.findByOwnerIdAndWeekPlanId(user.getId(), week.getId())
                .map(this::toReviewDto)
                .orElse(null);

        Integer totalMins = sessionRepository.getTotalMinutesBetween(user.getId(), week.getStartDate(), week.getEndDate());
        BigDecimal loggedHours = BigDecimal.valueOf(totalMins != null ? totalMins : 0)
                .divide(BigDecimal.valueOf(60), 1, RoundingMode.HALF_UP);

        return new WeekDetailDto(weekPlanDto, taskDtos, reviewDto, loggedHours);
    }

    public TaskDto toTaskDto(StudyTask t) {
        return new TaskDto(
                t.getId(),
                t.getWeekPlan().getId(),
                t.getWeekPlan().getWeekNumber(),
                t.getTitle(),
                t.getDescription(),
                t.getCategory(),
                t.getPriority(),
                t.getEstimateMinutes(),
                t.getActualMinutes(),
                t.getDueDate(),
                t.getStatus(),
                t.getCompletedAt(),
                t.getSource(),
                t.getSortOrder()
        );
    }

    public WeeklyReviewDto toReviewDto(WeeklyReview r) {
        return new WeeklyReviewDto(
                r.getId(),
                r.getWeekPlan().getId(),
                r.getWeekPlan().getWeekNumber(),
                r.getWhatStudied(),
                r.getWhatCompleted(),
                r.getWhatAiGotRight(),
                r.getWhatAiGotWrong(),
                r.getWhatVerifiedIndependently(),
                r.getWins(),
                r.getBlockers(),
                r.getAreasToImprove(),
                r.getNextWeekPriorities(),
                r.getSelfRating(),
                r.getTotalActualHours(),
                r.getStatus(),
                r.getFinalizedAt(),
                r.getUpdatedAt()
        );
    }
}
