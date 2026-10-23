package com.jayasuriya.studytracker.service;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.*;
import com.jayasuriya.studytracker.repository.*;
import com.jayasuriya.studytracker.service.ai.AiService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final RoadmapRepository roadmapRepository;
    private final WeekPlanRepository weekPlanRepository;
    private final StudyTaskRepository taskRepository;
    private final DsaProblemRepository dsaRepository;
    private final StudySessionRepository sessionRepository;
    private final ProjectService projectService;
    private final RoadmapService roadmapService;
    private final AiService aiService;

    public DashboardService(
            RoadmapRepository roadmapRepository,
            WeekPlanRepository weekPlanRepository,
            StudyTaskRepository taskRepository,
            DsaProblemRepository dsaRepository,
            StudySessionRepository sessionRepository,
            ProjectService projectService,
            RoadmapService roadmapService,
            AiService aiService) {
        this.roadmapRepository = roadmapRepository;
        this.weekPlanRepository = weekPlanRepository;
        this.taskRepository = taskRepository;
        this.dsaRepository = dsaRepository;
        this.sessionRepository = sessionRepository;
        this.projectService = projectService;
        this.roadmapService = roadmapService;
        this.aiService = aiService;
    }

    public DashboardSummaryDto getDashboardSummary(User user) {
        LocalDate start = user.getRoadmapStartDate() != null ? user.getRoadmapStartDate() : LocalDate.of(2026, 11, 2);
        LocalDate today = LocalDate.now();

        RoadmapTimelineCalculator.TimelineInfo timeline = RoadmapTimelineCalculator.calculate(start, today);
        boolean isStarted = timeline.isStarted();
        long daysUntilStart = timeline.daysUntilStart();
        int currentWeekNum = isStarted ? timeline.currentWeekNumber() : 1;

        Roadmap roadmap = roadmapRepository.findByOwnerIdAndIsActiveTrue(user.getId()).orElse(null);

        WeekPlan currentWeek = null;
        if (roadmap != null) {
            currentWeek = weekPlanRepository.findByRoadmapIdAndWeekNumber(roadmap.getId(), currentWeekNum).orElse(null);
        }

        String phaseCode = currentWeek != null && currentWeek.getPhase() != null ? currentWeek.getPhase().getCode() : "A";
        String phaseTitle = currentWeek != null && currentWeek.getPhase() != null ? currentWeek.getPhase().getTitle() : "Foundations & AI Fluency";
        String engFocus = currentWeek != null ? currentWeek.getEngineeringFocus() : "Assess Java skills, Big-O and arrays";
        String aiDeliverable = currentWeek != null ? currentWeek.getAiReadinessDeliverable() : "Establish safe AI-assisted coding workflow";

        long totalTasks = taskRepository.countByOwnerId(user.getId());
        long completedTasks = taskRepository.countByOwnerIdAndStatus(user.getId(), Enums.TaskStatus.COMPLETED);
        int overallPct = totalTasks > 0 ? (int) Math.round((double) completedTasks / totalTasks * 100) : 0;

        long dsaSolved = dsaRepository.countByOwnerIdAndStatus(user.getId(), Enums.DsaStatus.SOLVED);

        BigDecimal plannedHoursThisWeek = currentWeek != null ? currentWeek.getPlannedHours() : new BigDecimal("14.0");

        LocalDate weekStartDate = currentWeek != null ? currentWeek.getStartDate() : today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate weekEndDate = currentWeek != null ? currentWeek.getEndDate() : weekStartDate.plusDays(6);

        Integer weekMins = sessionRepository.getTotalMinutesBetween(user.getId(), weekStartDate, weekEndDate);
        BigDecimal actualHoursThisWeek = BigDecimal.valueOf(weekMins != null ? weekMins : 0)
                .divide(BigDecimal.valueOf(60), 1, RoundingMode.HALF_UP);

        // Calculate streak
        int streak = calculateStreak(user.getId());

        List<TaskDto> todayTasks = taskRepository.findTodayTasks(user.getId(), today)
                .stream().map(roadmapService::toTaskDto).collect(Collectors.toList());

        List<TaskDto> overdueTasks = taskRepository.findOverdueTasks(user.getId(), today)
                .stream().map(roadmapService::toTaskDto).collect(Collectors.toList());

        List<TaskDto> currentWeekTasks = currentWeek != null
                ? taskRepository.findByWeekPlanIdAndOwnerIdOrderBySortOrderAsc(currentWeek.getId(), user.getId())
                .stream().map(roadmapService::toTaskDto).collect(Collectors.toList())
                : Collections.emptyList();

        List<ProjectDto> projects = projectService.getProjects(user);
        AiStatusDto aiStatus = aiService.getStatus(user.getId());

        return new DashboardSummaryDto(
                currentWeekNum,
                daysUntilStart,
                isStarted,
                phaseCode,
                phaseTitle,
                engFocus,
                aiDeliverable,
                completedTasks,
                totalTasks,
                overallPct,
                dsaSolved,
                120,
                160,
                plannedHoursThisWeek,
                actualHoursThisWeek,
                streak,
                todayTasks,
                overdueTasks,
                currentWeekTasks,
                projects,
                aiStatus
        );
    }

    private int calculateStreak(Long userId) {
        List<StudySession> sessions = sessionRepository.findByOwnerIdOrderBySessionDateDescCreatedAtDesc(userId);
        if (sessions.isEmpty()) return 0;

        Set<LocalDate> studyDates = sessions.stream().map(StudySession::getSessionDate).collect(Collectors.toSet());
        LocalDate today = LocalDate.now();

        LocalDate check = studyDates.contains(today) ? today : today.minusDays(1);
        if (!studyDates.contains(check)) return 0;

        int streak = 0;
        while (studyDates.contains(check)) {
            streak++;
            check = check.minusDays(1);
        }
        return streak;
    }
}
