package com.jayasuriya.studytracker.model;

public class Enums {

    public enum TaskCategory {
        DSA,
        JAVA,
        SPRING_BOOT,
        SQL,
        FLUTTER,
        AI_ASSISTED_DEV,
        AI_APP_ENGINEERING,
        PORTFOLIO_PROJECT,
        APPLICATION_SECURITY,
        DEVSECOPS,
        SYSTEM_DESIGN,
        CAREER_PREPARATION,
        INTERVIEW_PREPARATION,
        OTHER
    }

    public enum TaskStatus {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED,
        CARRIED_FORWARD
    }

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }

    public enum TaskSource {
        SEEDED,
        CUSTOM
    }

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    public enum DsaStatus {
        SOLVED,
        ATTEMPTED,
        REVISIT
    }

    public enum ReviewStatus {
        DRAFT,
        FINALIZED
    }

    public enum ProjectStatus {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED
    }

    public enum MilestoneStatus {
        PENDING,
        COMPLETED
    }

    public enum ApplicationStatus {
        WISHLIST,
        APPLIED,
        SCREENING,
        TECHNICAL_ROUND,
        SYSTEM_DESIGN,
        HR_ROUND,
        OFFER,
        REJECTED,
        WITHDRAWN
    }

    public enum VerificationRating {
        UNRATED,
        VERIFIED,
        PARTIALLY_CORRECT,
        INCORRECT
    }
}
