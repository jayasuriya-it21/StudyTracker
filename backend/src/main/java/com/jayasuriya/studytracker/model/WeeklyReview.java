package com.jayasuriya.studytracker.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "weekly_reviews", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"owner_id", "week_id"})
})
public class WeeklyReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "week_id", nullable = false)
    private WeekPlan weekPlan;

    @Column(name = "what_studied", columnDefinition = "TEXT")
    private String whatStudied;

    @Column(name = "what_completed", columnDefinition = "TEXT")
    private String whatCompleted;

    @Column(name = "what_ai_got_right", columnDefinition = "TEXT")
    private String whatAiGotRight;

    @Column(name = "what_ai_got_wrong", columnDefinition = "TEXT")
    private String whatAiGotWrong;

    @Column(name = "what_verified_independently", columnDefinition = "TEXT")
    private String whatVerifiedIndependently;

    @Column(columnDefinition = "TEXT")
    private String wins;

    @Column(columnDefinition = "TEXT")
    private String blockers;

    @Column(name = "areas_to_improve", columnDefinition = "TEXT")
    private String areasToImprove;

    @Column(name = "next_week_priorities", columnDefinition = "TEXT")
    private String nextWeekPriorities;

    @Column(name = "self_rating")
    private Integer selfRating;

    @Column(name = "total_actual_hours", precision = 4, scale = 1)
    private BigDecimal totalActualHours = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Enums.ReviewStatus status = Enums.ReviewStatus.DRAFT;

    @Column(name = "finalized_at")
    private OffsetDateTime finalizedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    public WeeklyReview() {}

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public WeekPlan getWeekPlan() { return weekPlan; }
    public void setWeekPlan(WeekPlan weekPlan) { this.weekPlan = weekPlan; }

    public String getWhatStudied() { return whatStudied; }
    public void setWhatStudied(String whatStudied) { this.whatStudied = whatStudied; }

    public String getWhatCompleted() { return whatCompleted; }
    public void setWhatCompleted(String whatCompleted) { this.whatCompleted = whatCompleted; }

    public String getWhatAiGotRight() { return whatAiGotRight; }
    public void setWhatAiGotRight(String whatAiGotRight) { this.whatAiGotRight = whatAiGotRight; }

    public String getWhatAiGotWrong() { return whatAiGotWrong; }
    public void setWhatAiGotWrong(String whatAiGotWrong) { this.whatAiGotWrong = whatAiGotWrong; }

    public String getWhatVerifiedIndependently() { return whatVerifiedIndependently; }
    public void setWhatVerifiedIndependently(String whatVerifiedIndependently) { this.whatVerifiedIndependently = whatVerifiedIndependently; }

    public String getWins() { return wins; }
    public void setWins(String wins) { this.wins = wins; }

    public String getBlockers() { return blockers; }
    public void setBlockers(String blockers) { this.blockers = blockers; }

    public String getAreasToImprove() { return areasToImprove; }
    public void setAreasToImprove(String areasToImprove) { this.areasToImprove = areasToImprove; }

    public String getNextWeekPriorities() { return nextWeekPriorities; }
    public void setNextWeekPriorities(String nextWeekPriorities) { this.nextWeekPriorities = nextWeekPriorities; }

    public Integer getSelfRating() { return selfRating; }
    public void setSelfRating(Integer selfRating) { this.selfRating = selfRating; }

    public BigDecimal getTotalActualHours() { return totalActualHours; }
    public void setTotalActualHours(BigDecimal totalActualHours) { this.totalActualHours = totalActualHours; }

    public Enums.ReviewStatus getStatus() { return status; }
    public void setStatus(Enums.ReviewStatus status) { this.status = status; }

    public OffsetDateTime getFinalizedAt() { return finalizedAt; }
    public void setFinalizedAt(OffsetDateTime finalizedAt) { this.finalizedAt = finalizedAt; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
