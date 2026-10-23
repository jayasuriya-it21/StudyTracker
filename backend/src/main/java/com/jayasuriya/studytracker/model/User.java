package com.jayasuriya.studytracker.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false, length = 50)
    private String timezone = "Asia/Kolkata";

    @Column(name = "weekly_target_hours", nullable = false, precision = 4, scale = 1)
    private BigDecimal weeklyTargetHours = new BigDecimal("14.0");

    @Column(name = "roadmap_start_date", nullable = false)
    private LocalDate roadmapStartDate = LocalDate.of(2026, 11, 2);

    @Column(name = "theme_preference", nullable = false, length = 20)
    private String themePreference = "dark";

    @Column(nullable = false, length = 30)
    private String role = "ROLE_USER";

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    public User() {}

    public User(String name, String username, String email, String passwordHash) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }

    public BigDecimal getWeeklyTargetHours() { return weeklyTargetHours; }
    public void setWeeklyTargetHours(BigDecimal weeklyTargetHours) { this.weeklyTargetHours = weeklyTargetHours; }

    public LocalDate getRoadmapStartDate() { return roadmapStartDate; }
    public void setRoadmapStartDate(LocalDate roadmapStartDate) { this.roadmapStartDate = roadmapStartDate; }

    public String getThemePreference() { return themePreference; }
    public void setThemePreference(String themePreference) { this.themePreference = themePreference; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
