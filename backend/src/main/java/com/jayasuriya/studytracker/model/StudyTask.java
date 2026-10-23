package com.jayasuriya.studytracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "study_tasks")
public class StudyTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "week_id", nullable = false)
    private WeekPlan weekPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Enums.TaskCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Enums.Priority priority = Enums.Priority.MEDIUM;

    @Column(name = "estimate_minutes", nullable = false)
    private int estimateMinutes = 60;

    @Column(name = "actual_minutes", nullable = false)
    private int actualMinutes = 0;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Enums.TaskStatus status = Enums.TaskStatus.NOT_STARTED;

    @Column(name = "completed_at")
    private OffsetDateTime completedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Enums.TaskSource source = Enums.TaskSource.SEEDED;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    public StudyTask() {}

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public WeekPlan getWeekPlan() { return weekPlan; }
    public void setWeekPlan(WeekPlan weekPlan) { this.weekPlan = weekPlan; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Enums.TaskCategory getCategory() { return category; }
    public void setCategory(Enums.TaskCategory category) { this.category = category; }

    public Enums.Priority getPriority() { return priority; }
    public void setPriority(Enums.Priority priority) { this.priority = priority; }

    public int getEstimateMinutes() { return estimateMinutes; }
    public void setEstimateMinutes(int estimateMinutes) { this.estimateMinutes = estimateMinutes; }

    public int getActualMinutes() { return actualMinutes; }
    public void setActualMinutes(int actualMinutes) { this.actualMinutes = actualMinutes; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Enums.TaskStatus getStatus() { return status; }
    public void setStatus(Enums.TaskStatus status) { this.status = status; }

    public OffsetDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(OffsetDateTime completedAt) { this.completedAt = completedAt; }

    public Enums.TaskSource getSource() { return source; }
    public void setSource(Enums.TaskSource source) { this.source = source; }

    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
