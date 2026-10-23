package com.jayasuriya.studytracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class StudyTrackerApplicationTests {

    @Test
    void contextLoads() {
        // Confirms Spring context loads, Flyway runs all migrations, and JPA mappings are verified
    }
}
