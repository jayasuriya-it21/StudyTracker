package com.jayasuriya.studytracker.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "roadmaps")
public class Roadmap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "target_role", nullable = false, length = 150)
    private String targetRole;

    @Column(name = "target_dsa_min", nullable = false)
    private int targetDsaMin = 120;

    @Column(name = "target_dsa_max", nullable = false)
    private int targetDsaMax = 160;

    @Column(name = "target_weekly_hours", nullable = false, precision = 4, scale = 1)
    private BigDecimal targetWeeklyHours = new BigDecimal("14.0");

    @Column(name = "is_template", nullable = false)
    private boolean isTemplate = false;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    public Roadmap() {}

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getTargetRole() { return targetRole; }
    public void setTargetRole(String targetRole) { this.targetRole = targetRole; }

    public int getTargetDsaMin() { return targetDsaMin; }
    public void setTargetDsaMin(int targetDsaMin) { this.targetDsaMin = targetDsaMin; }

    public int getTargetDsaMax() { return targetDsaMax; }
    public void setTargetDsaMax(int targetDsaMax) { this.targetDsaMax = targetDsaMax; }

    public BigDecimal getTargetWeeklyHours() { return targetWeeklyHours; }
    public void setTargetWeeklyHours(BigDecimal targetWeeklyHours) { this.targetWeeklyHours = targetWeeklyHours; }

    public boolean isTemplate() { return isTemplate; }
    public void setTemplate(boolean template) { isTemplate = template; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
