package com.jayasuriya.studytracker;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.model.Enums.Priority;
import com.jayasuriya.studytracker.model.Enums.TaskCategory;
import com.jayasuriya.studytracker.model.Enums.TaskStatus;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.repository.UserRepository;
import com.jayasuriya.studytracker.service.AuthService;
import com.jayasuriya.studytracker.service.RoadmapService;
import com.jayasuriya.studytracker.service.TaskService;
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
class TaskServiceTests {

    @Autowired
    private AuthService authService;

    @Autowired
    private RoadmapService roadmapService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Test task creation, toggle completion, and carry forward with audit trail")
    void testTaskLifecycleAndCarryForward() {
        AuthResponse auth = authService.register(new RegisterRequest(
                "Task Tester", "tasktester", "tasktester@example.com", "password123",
                "Asia/Kolkata", LocalDate.of(2026, 11, 2)
        ));
        User user = userRepository.findById(auth.user().id()).orElseThrow();

        List<WeekPlanDto> weeks = roadmapService.getWeeks(user);
        WeekPlanDto w1 = weeks.get(0);
        WeekPlanDto w2 = weeks.get(1);

        // Create custom task in week 1
        TaskDto customTask = taskService.createTask(user, new CreateTaskRequest(
                w1.id(), "Practice Quickselect in Java", "Detailed partition tests",
                TaskCategory.DSA, Priority.HIGH, 45, LocalDate.of(2026, 11, 5)
        ));

        assertNotNull(customTask.id());
        assertEquals(TaskStatus.NOT_STARTED, customTask.status());

        // Toggle to completed
        TaskDto completed = taskService.toggleTask(user, customTask.id());
        assertEquals(TaskStatus.COMPLETED, completed.status());
        assertNotNull(completed.completedAt());

        // Toggle back to not started
        TaskDto reopened = taskService.toggleTask(user, customTask.id());
        assertEquals(TaskStatus.NOT_STARTED, reopened.status());
        assertNull(reopened.completedAt());

        // Carry forward to week 2
        TaskDto carried = taskService.carryForwardTask(user, customTask.id(), w2.id());
        assertNotNull(carried.id());
        assertEquals(w2.id(), carried.weekId());
        assertTrue(carried.description().contains("[Carried forward from Week 1]"));

        // Check original task is marked CARRIED_FORWARD
        WeekDetailDto w1Detail = roadmapService.getWeekDetail(user, 1);
        TaskDto original = w1Detail.tasks().stream()
                .filter(t -> t.id().equals(customTask.id())).findFirst().orElseThrow();
        assertEquals(TaskStatus.CARRIED_FORWARD, original.status());
    }
}
