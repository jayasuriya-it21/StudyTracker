package com.jayasuriya.studytracker.service;

import com.jayasuriya.studytracker.config.JwtUtils;
import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.exception.Exceptions.BadRequestException;
import com.jayasuriya.studytracker.exception.Exceptions.UnauthorizedException;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AuthService {

    public record LoginResult(String token, UserDto user) {}

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RoadmapService roadmapService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils,
            RoadmapService roadmapService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.roadmapService = roadmapService;
    }

    @Transactional
    public LoginResult register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.username())) {
            throw new BadRequestException("Username is already taken");
        }
        if (userRepository.existsByEmail(req.email())) {
            throw new BadRequestException("Email is already registered");
        }

        User user = new User();
        user.setName(req.name());
        user.setUsername(req.username());
        user.setEmail(req.email());
        user.setPasswordHash(passwordEncoder.encode(req.password()));
        if (req.timezone() != null) user.setTimezone(req.timezone());
        if (req.roadmapStartDate() != null) user.setRoadmapStartDate(req.roadmapStartDate());
        else user.setRoadmapStartDate(LocalDate.of(2026, 11, 2));

        user = userRepository.save(user);

        // Initialize user-owned isolated personal copy of the 52-week roadmap and projects
        roadmapService.initializeUserRoadmap(user);

        String token = jwtUtils.generateToken(user.getUsername(), user.getId(), user.getRole());
        return new LoginResult(token, toDto(user));
    }

    public LoginResult login(LoginRequest req) {
        User user = userRepository.findByUsername(req.username())
                .or(() -> userRepository.findByEmail(req.username()))
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        if (!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid username or password");
        }

        String token = jwtUtils.generateToken(user.getUsername(), user.getId(), user.getRole());
        return new LoginResult(token, toDto(user));
    }

    public UserDto getCurrentUserDto(User user) {
        return toDto(user);
    }

    @Transactional
    public UserDto updateProfile(User user, UpdateProfileRequest req) {
        if (req.name() != null && !req.name().isBlank()) user.setName(req.name());
        if (req.timezone() != null) user.setTimezone(req.timezone());
        if (req.weeklyTargetHours() != null) user.setWeeklyTargetHours(req.weeklyTargetHours());
        if (req.roadmapStartDate() != null) user.setRoadmapStartDate(req.roadmapStartDate());
        if (req.themePreference() != null) user.setThemePreference(req.themePreference());

        user = userRepository.save(user);
        return toDto(user);
    }

    private UserDto toDto(User u) {
        return new UserDto(
                u.getId(),
                u.getName(),
                u.getUsername(),
                u.getEmail(),
                u.getTimezone(),
                u.getWeeklyTargetHours(),
                u.getRoadmapStartDate(),
                u.getThemePreference(),
                u.getRole()
        );
    }
}
