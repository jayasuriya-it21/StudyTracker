package com.jayasuriya.studytracker.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "week_plans", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"roadmap_id", "week_number"})
})
public class WeekPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id", nullable = false)
    private Roadmap roadmap;

    @Column(name = "week_number", nullable = false)
    private int weekNumber;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "phase_id")
    private Phase phase;

    @Column(name = "engineering_focus", nullable = false)
    private String engineeringFocus;

    @Column(name = "ai_readiness_deliverable", nullable = false)
    private String aiReadinessDeliverable;

    @Column(name = "milestone_description", columnDefinition = "TEXT")
    private String milestoneDescription;

    @Column(name = "planned_hours", nullable = false, precision = 4, scale = 1)
    private BigDecimal plannedHours = new BigDecimal("14.0");

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Enums.TaskStatus status = Enums.TaskStatus.NOT_STARTED;

    @Column(name = "completion_percentage", nullable = false)
    private int completionPercentage = 0;

    public WeekPlan() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Roadmap getRoadmap() { return roadmap; }
    public void setRoadmap(Roadmap roadmap) { this.roadmap = roadmap; }

    public int getWeekNumber() { return weekNumber; }
    public void setWeekNumber(int weekNumber) { this.weekNumber = weekNumber; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Phase getPhase() { return phase; }
    public void setPhase(Phase phase) { this.phase = phase; }

    public String getEngineeringFocus() { return engineeringFocus; }
    public void setEngineeringFocus(String engineeringFocus) { this.engineeringFocus = engineeringFocus; }

    public String getAiReadinessDeliverable() { return aiReadinessDeliverable; }
    public void setAiReadinessDeliverable(String aiReadinessDeliverable) { this.aiReadinessDeliverable = aiReadinessDeliverable; }

    public String getMilestoneDescription() { return milestoneDescription; }
    public void setMilestoneDescription(String milestoneDescription) { this.milestoneDescription = milestoneDescription; }

    public BigDecimal getPlannedHours() { return plannedHours; }
    public void setPlannedHours(BigDecimal plannedHours) { this.plannedHours = plannedHours; }

    public Enums.TaskStatus getStatus() { return status; }
    public void setStatus(Enums.TaskStatus status) { this.status = status; }

    public int getCompletionPercentage() { return completionPercentage; }
    public void setCompletionPercentage(int completionPercentage) { this.completionPercentage = completionPercentage; }
}
