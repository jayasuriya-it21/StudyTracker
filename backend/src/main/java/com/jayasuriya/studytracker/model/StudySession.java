package com.jayasuriya.studytracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "study_sessions")
public class StudySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private StudyTask task;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Enums.TaskCategory category;

    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate;

    @Column(name = "duration_minutes", nullable = false)
    private int durationMinutes;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(name = "interruption_notes", columnDefinition = "TEXT")
    private String interruptionNotes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public StudySession() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public StudyTask getTask() { return task; }
    public void setTask(StudyTask task) { this.task = task; }

    public Enums.TaskCategory getCategory() { return category; }
    public void setCategory(Enums.TaskCategory category) { this.category = category; }

    public LocalDate getSessionDate() { return sessionDate; }
    public void setSessionDate(LocalDate sessionDate) { this.sessionDate = sessionDate; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getInterruptionNotes() { return interruptionNotes; }
    public void setInterruptionNotes(String interruptionNotes) { this.interruptionNotes = interruptionNotes; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
