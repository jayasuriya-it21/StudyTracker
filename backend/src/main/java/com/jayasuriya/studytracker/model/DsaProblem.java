package com.jayasuriya.studytracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "dsa_problems")
public class DsaProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 50)
    private String platform = "LeetCode";

    @Column(length = 500)
    private String url;

    @Column(name = "topic_pattern", nullable = false, length = 100)
    private String topicPattern;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Enums.Difficulty difficulty = Enums.Difficulty.MEDIUM;

    @Column(name = "date_attempted", nullable = false)
    private LocalDate dateAttempted;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Enums.DsaStatus status = Enums.DsaStatus.SOLVED;

    @Column(name = "attempt_count", nullable = false)
    private int attemptCount = 1;

    @Column(name = "time_taken_minutes")
    private Integer timeTakenMinutes;

    @Column(name = "hints_used", nullable = false)
    private int hintsUsed = 0;

    @Column(name = "could_solve_without_ai", nullable = false)
    private boolean couldSolveWithoutAi = true;

    @Column(name = "complexity_explanation", columnDefinition = "TEXT")
    private String complexityExplanation;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "next_review_date")
    private LocalDate nextReviewDate;

    @Column(name = "confidence_score", nullable = false)
    private int confidenceScore = 3;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    public DsaProblem() {}

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

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getTopicPattern() { return topicPattern; }
    public void setTopicPattern(String topicPattern) { this.topicPattern = topicPattern; }

    public Enums.Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Enums.Difficulty difficulty) { this.difficulty = difficulty; }

    public LocalDate getDateAttempted() { return dateAttempted; }
    public void setDateAttempted(LocalDate dateAttempted) { this.dateAttempted = dateAttempted; }

    public Enums.DsaStatus getStatus() { return status; }
    public void setStatus(Enums.DsaStatus status) { this.status = status; }

    public int getAttemptCount() { return attemptCount; }
    public void setAttemptCount(int attemptCount) { this.attemptCount = attemptCount; }

    public Integer getTimeTakenMinutes() { return timeTakenMinutes; }
    public void setTimeTakenMinutes(Integer timeTakenMinutes) { this.timeTakenMinutes = timeTakenMinutes; }

    public int getHintsUsed() { return hintsUsed; }
    public void setHintsUsed(int hintsUsed) { this.hintsUsed = hintsUsed; }

    public boolean isCouldSolveWithoutAi() { return couldSolveWithoutAi; }
    public void setCouldSolveWithoutAi(boolean couldSolveWithoutAi) { this.couldSolveWithoutAi = couldSolveWithoutAi; }

    public String getComplexityExplanation() { return complexityExplanation; }
    public void setComplexityExplanation(String complexityExplanation) { this.complexityExplanation = complexityExplanation; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getNextReviewDate() { return nextReviewDate; }
    public void setNextReviewDate(LocalDate nextReviewDate) { this.nextReviewDate = nextReviewDate; }

    public int getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(int confidenceScore) { this.confidenceScore = confidenceScore; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
