package com.jayasuriya.studytracker;

import com.jayasuriya.studytracker.service.RoadmapTimelineCalculator;
import com.jayasuriya.studytracker.service.RoadmapTimelineCalculator.TimelineInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RoadmapTimelineCalculatorTests {

    private final LocalDate roadmapStart = LocalDate.of(2026, 11, 2);

    @Test
    @DisplayName("1. Date before roadmap start: isStarted=false, daysUntilStart>0, currentWeek=0")
    void testDateBeforeRoadmapStart() {
        LocalDate currentDate = LocalDate.of(2026, 10, 24); // 9 days before 2 Nov 2026
        TimelineInfo info = RoadmapTimelineCalculator.calculate(roadmapStart, currentDate);

        assertFalse(info.isStarted(), "Should not be started before 2 Nov 2026");
        assertFalse(info.isCompleted(), "Should not be completed");
        assertEquals(9, info.daysUntilStart(), "Should be exactly 9 days until start");
        assertEquals(0, info.currentWeekNumber(), "Current week should be 0 before start");
    }

    @Test
    @DisplayName("2. Exact start date (2026-11-02): isStarted=true, daysUntilStart=0, currentWeek=1")
    void testExactRoadmapStartDate() {
        LocalDate currentDate = LocalDate.of(2026, 11, 2);
        TimelineInfo info = RoadmapTimelineCalculator.calculate(roadmapStart, currentDate);

        assertTrue(info.isStarted(), "Should be started on exact start date");
        assertFalse(info.isCompleted(), "Should not be completed");
        assertEquals(0, info.daysUntilStart());
        assertEquals(1, info.currentWeekNumber(), "Should be in week 1 on start date");
    }

    @Test
    @DisplayName("3. Mid-roadmap date (2027-04-12): isStarted=true, week 24")
    void testMidRoadmapDate() {
        LocalDate currentDate = LocalDate.of(2027, 4, 12); // Monday of week 24
        TimelineInfo info = RoadmapTimelineCalculator.calculate(roadmapStart, currentDate);

        assertTrue(info.isStarted());
        assertFalse(info.isCompleted());
        assertEquals(24, info.currentWeekNumber(), "Should accurately calculate mid-roadmap week 24");
    }

    @Test
    @DisplayName("4. Exact end date (2027-10-31): isStarted=true, isCompleted=false, week 52")
    void testExactRoadmapEndDate() {
        LocalDate currentDate = LocalDate.of(2027, 10, 31); // Sunday of week 52
        TimelineInfo info = RoadmapTimelineCalculator.calculate(roadmapStart, currentDate);

        assertTrue(info.isStarted());
        assertFalse(info.isCompleted(), "On final day, roadmap is still active");
        assertEquals(52, info.currentWeekNumber(), "Should be week 52 on last day");
    }

    @Test
    @DisplayName("5. Date after roadmap end (2027-11-05): isStarted=true, isCompleted=true, week 52")
    void testDateAfterRoadmapEnd() {
        LocalDate currentDate = LocalDate.of(2027, 11, 5); // After 31 Oct 2027
        TimelineInfo info = RoadmapTimelineCalculator.calculate(roadmapStart, currentDate);

        assertTrue(info.isStarted());
        assertTrue(info.isCompleted(), "Should be marked as completed after 31 Oct 2027");
        assertEquals(52, info.currentWeekNumber());
    }
}
