package com.jayasuriya.studytracker;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.repository.UserRepository;
import com.jayasuriya.studytracker.service.AuthService;
import com.jayasuriya.studytracker.service.DashboardService;
import com.jayasuriya.studytracker.service.RoadmapService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RoadmapCalculationTests {

    @Autowired
    private AuthService authService;

    @Autowired
    private RoadmapService roadmapService;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Verify roadmap contains exactly 52 weeks and starts Nov 2 2026 to Oct 31 2027")
    void testRoadmap52WeeksDates() {
        AuthResponse auth = authService.register(new RegisterRequest(
                "Test User", "testuser52", "test52@example.com", "password123",
                "Asia/Kolkata", LocalDate.of(2026, 11, 2)
        ));

        User user = userRepository.findById(auth.user().id()).orElseThrow();
        RoadmapDto roadmap = roadmapService.getRoadmapForUser(user);

        assertEquals(LocalDate.of(2026, 11, 2), roadmap.startDate());
        assertEquals(LocalDate.of(2027, 10, 31), roadmap.endDate());
        assertEquals(7, roadmap.phases().size(), "Must have exactly 7 phases (A-G)");

        List<WeekPlanDto> weeks = roadmapService.getWeeks(user);
        assertEquals(52, weeks.size(), "Must have exactly 52 weeks seeded");

        // Verify Week 1
        WeekPlanDto w1 = weeks.get(0);
        assertEquals(1, w1.weekNumber());
        assertEquals(LocalDate.of(2026, 11, 2), w1.startDate());
        assertEquals(LocalDate.of(2026, 11, 8), w1.endDate());
        assertEquals("A", w1.phaseCode());

        // Verify Week 52
        WeekPlanDto w52 = weeks.get(51);
        assertEquals(52, w52.weekNumber());
        assertEquals(LocalDate.of(2027, 10, 25), w52.startDate());
        assertEquals(LocalDate.of(2027, 10, 31), w52.endDate());
        assertEquals("G", w52.phaseCode());
    }

    @Test
    @DisplayName("Verify dashboard shows countdown before start date without marking week 1 completed")
    void testDashboardCountdownBeforeStart() {
        AuthResponse auth = authService.register(new RegisterRequest(
                "Future User", "futureuser", "future@example.com", "password123",
                "Asia/Kolkata", LocalDate.of(2026, 11, 2)
        ));

        User user = userRepository.findById(auth.user().id()).orElseThrow();
        DashboardSummaryDto dash = dashboardService.getDashboardSummary(user);

        LocalDate today = LocalDate.now();
        if (today.isBefore(LocalDate.of(2026, 11, 2))) {
            assertFalse(dash.isStarted());
            assertTrue(dash.daysUntilStart() > 0);
            assertEquals(1, dash.currentWeek());
            assertEquals(0, dash.overallCompletionPercentage());
        }
    }
}
