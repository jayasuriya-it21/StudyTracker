package com.jayasuriya.studytracker;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.exception.Exceptions.BadRequestException;
import com.jayasuriya.studytracker.exception.Exceptions.ResourceNotFoundException;
import com.jayasuriya.studytracker.model.Enums.TaskCategory;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthAndIsolationApiTests {

    @Autowired
    private AuthService authService;

    @Autowired
    private RoadmapService roadmapService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Verify strict ownership isolation: User B cannot access or modify User A's tasks")
    void testOwnershipIsolation() {
        // Register User A
        AuthResponse authA = authService.register(new RegisterRequest(
                "Alice", "alice", "alice@example.com", "password123",
                "Asia/Kolkata", LocalDate.of(2026, 11, 2)
        ));
        User userA = userRepository.findById(authA.user().id()).orElseThrow();

        // Register User B
        AuthResponse authB = authService.register(new RegisterRequest(
                "Bob", "bob", "bob@example.com", "password123",
                "Asia/Kolkata", LocalDate.of(2026, 11, 2)
        ));
        User userB = userRepository.findById(authB.user().id()).orElseThrow();

        // User A creates a task in their Week 1
        Long aWeek1Id = roadmapService.getWeeks(userA).get(0).id();
        TaskDto aTask = taskService.createTask(userA, new CreateTaskRequest(
                aWeek1Id, "Alice Private Task", "Private details", TaskCategory.JAVA, null, 30, null
        ));

        // User B tries to toggle Alice's task -> Must fail with ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.toggleTask(userB, aTask.id());
        });

        // User B tries to delete Alice's task -> Must fail with ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.deleteTask(userB, aTask.id());
        });

        // User B tries to add a task to Alice's week -> Must fail with BadRequestException
        assertThrows(BadRequestException.class, () -> {
            taskService.createTask(userB, new CreateTaskRequest(
                    aWeek1Id, "Bob Imposter Task", "", TaskCategory.SQL, null, 30, null
            ));
        });
    }
}
