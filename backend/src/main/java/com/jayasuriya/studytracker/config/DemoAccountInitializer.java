package com.jayasuriya.studytracker.config;

import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.repository.UserRepository;
import com.jayasuriya.studytracker.service.RoadmapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DemoAccountInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DemoAccountInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoadmapService roadmapService;

    @Value("${app.demo-account.enabled:true}")
    private boolean enabled;

    @Value("${app.demo-account.name:Jayasuriya}")
    private String name;

    @Value("${app.demo-account.username:jayasuriya}")
    private String username;

    @Value("${app.demo-account.email:jayasuriya@example.com}")
    private String email;

    @Value("${app.demo-account.password:password123}")
    private String password;

    public DemoAccountInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoadmapService roadmapService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roadmapService = roadmapService;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!enabled) return;

        if (userRepository.findByUsername(username).isEmpty()) {
            log.info("Initializing demo account '{}' for development and testing...", username);
            User user = new User();
            user.setName(name);
            user.setUsername(username);
            user.setEmail(email);
            user.setPasswordHash(passwordEncoder.encode(password));
            user.setRoadmapStartDate(LocalDate.of(2026, 11, 2));
            user.setTimezone("Asia/Kolkata");
            user = userRepository.save(user);

            roadmapService.initializeUserRoadmap(user);
            log.info("Demo account initialized successfully with full 52-week roadmap!");
        }
    }
}
