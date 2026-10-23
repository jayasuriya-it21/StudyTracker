package com.jayasuriya.studytracker.service;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.*;
import com.jayasuriya.studytracker.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final RoadmapRepository roadmapRepository;
    private final PhaseRepository phaseRepository;
    private final WeekPlanRepository weekPlanRepository;
    private final StudyTaskRepository taskRepository;
    private final StudySessionRepository sessionRepository;
    private final DsaService dsaService;
    private final CareerApplicationRepository applicationRepository;

    public AnalyticsService(
            RoadmapRepository roadmapRepository,
            PhaseRepository phaseRepository,
            WeekPlanRepository weekPlanRepository,
            StudyTaskRepository taskRepository,
            StudySessionRepository sessionRepository,
            DsaService dsaService,
            CareerApplicationRepository applicationRepository) {
        this.roadmapRepository = roadmapRepository;
        this.phaseRepository = phaseRepository;
        this.weekPlanRepository = weekPlanRepository;
        this.taskRepository = taskRepository;
        this.sessionRepository = sessionRepository;
        this.dsaService = dsaService;
        this.applicationRepository = applicationRepository;
    }

    public AnalyticsDto getAnalytics(User user) {
        Roadmap roadmap = roadmapRepository.findByOwnerIdAndIsActiveTrue(user.getId()).orElse(null);

        // 1. Weekly planned vs actual hours for 13 4-week milestones (or all weeks)
        Map<String, BigDecimal> weeklyHours = new LinkedHashMap<>();
        if (roadmap != null) {
            List<WeekPlan> weeks = weekPlanRepository.findByRoadmapIdOrderByWeekNumberAsc(roadmap.getId());
            for (int i = 1; i <= Math.min(16, weeks.size()); i++) {
                WeekPlan w = weeks.get(i - 1);
                Integer mins = sessionRepository.getTotalMinutesBetween(user.getId(), w.getStartDate(), w.getEndDate());
                BigDecimal hours = BigDecimal.valueOf(mins != null ? mins : 0)
                        .divide(BigDecimal.valueOf(60), 1, RoundingMode.HALF_UP);
                weeklyHours.put("W" + w.getWeekNumber(), hours);
            }
        }

        // 2. Category distribution from actual study sessions
        List<StudySession> sessions = sessionRepository.findByOwnerIdOrderBySessionDateDescCreatedAtDesc(user.getId());
        Map<Enums.TaskCategory, BigDecimal> categoryDist = new EnumMap<>(Enums.TaskCategory.class);
        for (StudySession s : sessions) {
            BigDecimal current = categoryDist.getOrDefault(s.getCategory(), BigDecimal.ZERO);
            BigDecimal added = BigDecimal.valueOf(s.getDurationMinutes()).divide(BigDecimal.valueOf(60), 1, RoundingMode.HALF_UP);
            categoryDist.put(s.getCategory(), current.add(added));
        }

        // 3. Phase completion rates
        Map<String, Integer> phaseRates = new LinkedHashMap<>();
        if (roadmap != null) {
            List<Phase> phases = phaseRepository.findByRoadmapIdOrderByDisplayOrderAsc(roadmap.getId());
            for (Phase p : phases) {
                List<WeekPlan> phaseWeeks = weekPlanRepository.findByRoadmapIdOrderByWeekNumberAsc(roadmap.getId()).stream()
                        .filter(w -> w.getPhase() != null && w.getPhase().getId().equals(p.getId()))
                        .collect(Collectors.toList());

                int totalTasks = 0;
                int completedTasks = 0;
                for (WeekPlan w : phaseWeeks) {
                    List<StudyTask> tasks = taskRepository.findByWeekPlanIdAndOwnerIdOrderBySortOrderAsc(w.getId(), user.getId());
                    totalTasks += tasks.size();
                    completedTasks += (int) tasks.stream().filter(t -> t.getStatus() == Enums.TaskStatus.COMPLETED).count();
                }
                int pct = totalTasks > 0 ? (int) Math.round((double) completedTasks / totalTasks * 100) : 0;
                phaseRates.put("Phase " + p.getCode(), pct);
            }
        }

        // 4. DSA Stats
        DsaStatsDto dsaStats = dsaService.getStats(user);

        // 5. Streak calculation
        int streak = calculateStreak(user.getId());

        // 6. Application Funnel
        List<CareerApplication> apps = applicationRepository.findByOwnerIdOrderByApplicationDateDesc(user.getId());
        Map<Enums.ApplicationStatus, Long> funnel = apps.stream()
                .collect(Collectors.groupingBy(CareerApplication::getStatus, Collectors.counting()));

        return new AnalyticsDto(
                weeklyHours,
                categoryDist,
                phaseRates,
                dsaStats,
                streak,
                funnel
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
