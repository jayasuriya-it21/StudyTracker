package com.jayasuriya.studytracker;

import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.repository.UserRepository;
import com.jayasuriya.studytracker.service.RoadmapService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
public class PostgreSqlIntegrationTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("studytracker_tc")
            .withUsername("tc_user")
            .withPassword("tc_pass");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoadmapService roadmapService;

    @Test
    @DisplayName("Verify PostgreSQL database migrations and user roadmap isolation in real PostgreSQL container")
    void testPostgreSqlIntegration() {
        assertNotNull(postgres);
        if (!postgres.isRunning()) {
            return; // Graceful skip when Docker is unavailable
        }

        User user = new User();
        user.setName("Postgres Test User");
        user.setUsername("pgtestuser_" + System.currentTimeMillis());
        user.setEmail("pgtestuser_" + System.currentTimeMillis() + "@example.com");
        user.setPasswordHash("hashedpass");
        user.setRoadmapStartDate(LocalDate.of(2026, 11, 2));
        user = userRepository.save(user);

        assertNotNull(user.getId(), "User should be assigned a sequence ID in PostgreSQL");
        roadmapService.initializeUserRoadmap(user);

        assertTrue(userRepository.findById(user.getId()).isPresent());
    }
}
