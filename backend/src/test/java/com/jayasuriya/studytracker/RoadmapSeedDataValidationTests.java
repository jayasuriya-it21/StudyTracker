package com.jayasuriya.studytracker;

import com.jayasuriya.studytracker.model.Phase;
import com.jayasuriya.studytracker.model.StudyTask;
import com.jayasuriya.studytracker.model.WeekPlan;
import com.jayasuriya.studytracker.repository.PhaseRepository;
import com.jayasuriya.studytracker.repository.StudyTaskRepository;
import com.jayasuriya.studytracker.repository.WeekPlanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class RoadmapSeedDataValidationTests {

    @Autowired
    private PhaseRepository phaseRepository;

    @Autowired
    private WeekPlanRepository weekPlanRepository;

    @Autowired
    private StudyTaskRepository taskRepository;

    @Test
    @DisplayName("Verify Flyway seed data integrity: 7 phases, 52 unique weeks, Nov 2 2026 to Oct 31 2027, and meaningful tasks")
    void testSeedDataIntegrity() {
        // 1. Verify 7 phases exist: A, B, C, D, E, F, G
        List<Phase> phases = phaseRepository.findByRoadmapIdOrderByDisplayOrderAsc(1L);
        assertEquals(7, phases.size(), "Template roadmap must contain exactly 7 phases (A through G)");
        Set<String> phaseCodes = phases.stream().map(Phase::getCode).collect(Collectors.toSet());
        assertTrue(phaseCodes.containsAll(Set.of("A", "B", "C", "D", "E", "F", "G")));

        // 2. Verify exactly 52 weeks exist in template roadmap (id=1)
        List<WeekPlan> weeks = weekPlanRepository.findByRoadmapIdOrderByWeekNumberAsc(1L);
        assertEquals(52, weeks.size(), "Template roadmap must contain exactly 52 weeks");

        // 3. Verify week numbers are unique and sequential from 1 to 52
        List<Integer> weekNumbers = weeks.stream().map(WeekPlan::getWeekNumber).toList();
        for (int i = 1; i <= 52; i++) {
            assertEquals(i, weekNumbers.get(i - 1), "Week numbers must be strictly sequential 1 through 52");
        }

        // 4. Verify Week 1 dates: Starts 2 November 2026
        WeekPlan week1 = weeks.get(0);
        assertEquals(LocalDate.of(2026, 11, 2), week1.getStartDate(), "Week 1 must start on Monday, 2 Nov 2026");
        assertEquals(LocalDate.of(2026, 11, 8), week1.getEndDate(), "Week 1 must end on Sunday, 8 Nov 2026");
        assertEquals("A", week1.getPhase().getCode(), "Week 1 must belong to Phase A");

        // 5. Verify Week 52 dates: Ends 31 October 2027
        WeekPlan week52 = weeks.get(51);
        assertEquals(LocalDate.of(2027, 10, 25), week52.getStartDate(), "Week 52 must start on 25 Oct 2027");
        assertEquals(LocalDate.of(2027, 10, 31), week52.getEndDate(), "Week 52 must end on Sunday, 31 Oct 2027");
        assertEquals("G", week52.getPhase().getCode(), "Week 52 must belong to Phase G");

        // 6. Verify seed tasks are meaningful
        List<StudyTask> week1Tasks = taskRepository.findByWeekPlanIdAndOwnerIsNullOrderBySortOrderAsc(week1.getId());
        assertFalse(week1Tasks.isEmpty(), "Week 1 must contain seeded tasks");
        for (StudyTask task : week1Tasks) {
            assertNotNull(task.getTitle(), "Task title must not be null");
            assertTrue(task.getTitle().length() > 5, "Task title must be meaningful");
            assertNotNull(task.getCategory(), "Task category must be assigned");
        }
    }
}
