package com.jayasuriya.studytracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "career_applications")
public class CareerApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false, length = 100)
    private String company;

    @Column(nullable = false, length = 100)
    private String role;

    @Column(name = "job_url", length = 500)
    private String jobUrl;

    @Column(name = "source_referral", length = 100)
    private String sourceReferral;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Enums.ApplicationStatus status = Enums.ApplicationStatus.APPLIED;

    @Column(name = "interview_stage", length = 100)
    private String interviewStage;

    @Column(name = "notice_period_compatibility", length = 100)
    private String noticePeriodCompatibility;

    @Column(name = "fixed_compensation", length = 50)
    private String fixedCompensation;

    @Column(name = "variable_compensation", length = 50)
    private String variableCompensation;

    @Column(length = 50)
    private String equity;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "next_action_date")
    private LocalDate nextActionDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    public CareerApplication() {}

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getJobUrl() { return jobUrl; }
    public void setJobUrl(String jobUrl) { this.jobUrl = jobUrl; }

    public String getSourceReferral() { return sourceReferral; }
    public void setSourceReferral(String sourceReferral) { this.sourceReferral = sourceReferral; }

    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }

    public Enums.ApplicationStatus getStatus() { return status; }
    public void setStatus(Enums.ApplicationStatus status) { this.status = status; }

    public String getInterviewStage() { return interviewStage; }
    public void setInterviewStage(String interviewStage) { this.interviewStage = interviewStage; }

    public String getNoticePeriodCompatibility() { return noticePeriodCompatibility; }
    public void setNoticePeriodCompatibility(String noticePeriodCompatibility) { this.noticePeriodCompatibility = noticePeriodCompatibility; }

    public String getFixedCompensation() { return fixedCompensation; }
    public void setFixedCompensation(String fixedCompensation) { this.fixedCompensation = fixedCompensation; }

    public String getVariableCompensation() { return variableCompensation; }
    public void setVariableCompensation(String variableCompensation) { this.variableCompensation = variableCompensation; }

    public String getEquity() { return equity; }
    public void setEquity(String equity) { this.equity = equity; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getNextActionDate() { return nextActionDate; }
    public void setNextActionDate(LocalDate nextActionDate) { this.nextActionDate = nextActionDate; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
