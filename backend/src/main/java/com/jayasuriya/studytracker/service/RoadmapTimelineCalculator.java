package com.jayasuriya.studytracker.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RoadmapTimelineCalculator {

    public record TimelineInfo(
            boolean isStarted,
            boolean isCompleted,
            long daysUntilStart,
            int currentWeekNumber,
            long daysSinceStart
    ) {}

    public static TimelineInfo calculate(LocalDate roadmapStart, LocalDate targetDate) {
        if (roadmapStart == null) {
            roadmapStart = LocalDate.of(2026, 11, 2);
        }
        if (targetDate == null) {
            targetDate = LocalDate.now();
        }

        if (targetDate.isBefore(roadmapStart)) {
            long daysUntil = ChronoUnit.DAYS.between(targetDate, roadmapStart);
            return new TimelineInfo(false, false, daysUntil, 0, 0);
        }

        LocalDate roadmapEnd = roadmapStart.plusDays(52 * 7 - 1); // 363 days after start = 52 weeks = 2027-10-31
        long diffDays = ChronoUnit.DAYS.between(roadmapStart, targetDate);

        if (targetDate.isAfter(roadmapEnd)) {
            return new TimelineInfo(true, true, 0, 52, diffDays);
        }

        int weekNumber = (int) (diffDays / 7) + 1;
        weekNumber = Math.min(Math.max(weekNumber, 1), 52);

        return new TimelineInfo(true, false, 0, weekNumber, diffDays);
    }
}
