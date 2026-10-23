package com.jayasuriya.studytracker.dto;

import com.jayasuriya.studytracker.model.Enums.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public class Dtos {

    // --- Auth DTOs ---
    public record RegisterRequest(
            @NotBlank(message = "Name is required") String name,
            @NotBlank(message = "Username is required") @Size(min = 3, max = 50) String username,
            @NotBlank(message = "Email is required") @Email String email,
            @NotBlank(message = "Password is required") @Size(min = 6, max = 100) String password,
            String timezone,
            LocalDate roadmapStartDate
    ) {}

    public record LoginRequest(
            @NotBlank(message = "Username or email is required") String username,
            @NotBlank(message = "Password is required") String password
    ) {}

    public record AuthResponse(
            String token,
            UserDto user
    ) {}

    public record UserDto(
            Long id,
            String name,
            String username,
            String email,
            String timezone,
            BigDecimal weeklyTargetHours,
            LocalDate roadmapStartDate,
            String themePreference,
            String role
    ) {}

    public record UpdateProfileRequest(
            String name,
            String timezone,
            BigDecimal weeklyTargetHours,
            LocalDate roadmapStartDate,
            String themePreference
    ) {}

    // --- Roadmap & Week DTOs ---
    public record RoadmapDto(
            Long id,
            String title,
            LocalDate startDate,
            LocalDate endDate,
            String targetRole,
            int targetDsaMin,
            int targetDsaMax,
            BigDecimal targetWeeklyHours,
            List<PhaseDto> phases
    ) {}

    public record PhaseDto(
            Long id,
            String code,
            String title,
            String description,
            int startWeek,
            int endWeek,
            String color,
            String icon,
            int displayOrder,
            int completionPercentage
    ) {}

    public record WeekPlanDto(
            Long id,
            int weekNumber,
            LocalDate startDate,
            LocalDate endDate,
            String phaseCode,
            String phaseTitle,
            String engineeringFocus,
            String aiReadinessDeliverable,
            String milestoneDescription,
            BigDecimal plannedHours,
            TaskStatus status,
            int completionPercentage,
            int totalTasks,
            int completedTasks
    ) {}

    public record WeekDetailDto(
            WeekPlanDto weekPlan,
            List<TaskDto> tasks,
            WeeklyReviewDto review,
            BigDecimal loggedHours
    ) {}

    // --- Task DTOs ---
    public record TaskDto(
            Long id,
            Long weekId,
            int weekNumber,
            String title,
            String description,
            TaskCategory category,
            Priority priority,
            int estimateMinutes,
            int actualMinutes,
            LocalDate dueDate,
            TaskStatus status,
            OffsetDateTime completedAt,
            TaskSource source,
            int sortOrder
    ) {}

    public record CreateTaskRequest(
            @NotNull(message = "Week ID is required") Long weekId,
            @NotBlank(message = "Title is required") String title,
            String description,
            @NotNull(message = "Category is required") TaskCategory category,
            Priority priority,
            Integer estimateMinutes,
            LocalDate dueDate
    ) {}

    public record UpdateTaskRequest(
            String title,
            String description,
            TaskCategory category,
            Priority priority,
            Integer estimateMinutes,
            Integer actualMinutes,
            LocalDate dueDate,
            TaskStatus status,
            Integer sortOrder
    ) {}

    public record CarryForwardRequest(
            @NotNull(message = "Target week ID is required") Long targetWeekId
    ) {}

    // --- Session DTOs ---
    public record SessionDto(
            Long id,
            Long taskId,
            String taskTitle,
            TaskCategory category,
            LocalDate sessionDate,
            int durationMinutes,
            String summary,
            String interruptionNotes,
            OffsetDateTime createdAt
    ) {}

    public record CreateSessionRequest(
            Long taskId,
            @NotNull(message = "Category is required") TaskCategory category,
            @NotNull(message = "Date is required") LocalDate sessionDate,
            @Min(value = 1, message = "Duration must be at least 1 minute") int durationMinutes,
            String summary,
            String interruptionNotes
    ) {}

    public record SessionStatsDto(
            BigDecimal totalHours,
            BigDecimal weeklyHours,
            BigDecimal monthlyHours,
            Map<TaskCategory, BigDecimal> hoursByCategory
    ) {}

    // --- DSA DTOs ---
    public record DsaProblemDto(
            Long id,
            String title,
            String platform,
            String url,
            String topicPattern,
            Difficulty difficulty,
            LocalDate dateAttempted,
            DsaStatus status,
            int attemptCount,
            Integer timeTakenMinutes,
            int hintsUsed,
            boolean couldSolveWithoutAi,
            String complexityExplanation,
            String notes,
            LocalDate nextReviewDate,
            int confidenceScore
    ) {}

    public record CreateDsaRequest(
            @NotBlank(message = "Title is required") String title,
            String platform,
            String url,
            @NotBlank(message = "Topic pattern is required") String topicPattern,
            Difficulty difficulty,
            @NotNull(message = "Date is required") LocalDate dateAttempted,
            DsaStatus status,
            Integer attemptCount,
            Integer timeTakenMinutes,
            Integer hintsUsed,
            Boolean couldSolveWithoutAi,
            String complexityExplanation,
            String notes,
            LocalDate nextReviewDate,
            Integer confidenceScore
    ) {}

    public record UpdateDsaRequest(
            String title,
            String platform,
            String url,
            String topicPattern,
            Difficulty difficulty,
            LocalDate dateAttempted,
            DsaStatus status,
            Integer attemptCount,
            Integer timeTakenMinutes,
            Integer hintsUsed,
            Boolean couldSolveWithoutAi,
            String complexityExplanation,
            String notes,
            LocalDate nextReviewDate,
            Integer confidenceScore
    ) {}

    public record DsaStatsDto(
            long totalSolved,
            long totalAttempted,
            int targetMin,
            int targetMax,
            Map<Difficulty, Long> byDifficulty,
            Map<String, Long> byTopic,
            List<String> weakTopics,
            long dueForReviewCount
    ) {}

    // --- Review DTOs ---
    public record WeeklyReviewDto(
            Long id,
            Long weekId,
            int weekNumber,
            String whatStudied,
            String whatCompleted,
            String whatAiGotRight,
            String whatAiGotWrong,
            String whatVerifiedIndependently,
            String wins,
            String blockers,
            String areasToImprove,
            String nextWeekPriorities,
            Integer selfRating,
            BigDecimal totalActualHours,
            ReviewStatus status,
            OffsetDateTime finalizedAt,
            OffsetDateTime updatedAt
    ) {}

    public record SaveReviewRequest(
            @NotNull(message = "Week ID is required") Long weekId,
            String whatStudied,
            String whatCompleted,
            String whatAiGotRight,
            String whatAiGotWrong,
            String whatVerifiedIndependently,
            String wins,
            String blockers,
            String areasToImprove,
            String nextWeekPriorities,
            Integer selfRating,
            Boolean finalizeReview
    ) {}

    // --- Project DTOs ---
    public record ProjectDto(
            Long id,
            String title,
            String projectType,
            String description,
            String techStack,
            ProjectStatus status,
            int progressPercentage,
            String repositoryUrl,
            String demoUrl,
            LocalDate startDate,
            LocalDate targetDate,
            String notes,
            List<ProjectMilestoneDto> milestones
    ) {}

    public record ProjectMilestoneDto(
            Long id,
            Long projectId,
            String title,
            String description,
            LocalDate dueDate,
            MilestoneStatus status,
            LocalDate completedDate
    ) {}

    public record CreateProjectRequest(
            @NotBlank(message = "Title is required") String title,
            String projectType,
            String description,
            String techStack,
            LocalDate startDate,
            LocalDate targetDate,
            String repositoryUrl,
            String demoUrl,
            String notes
    ) {}

    public record UpdateProjectRequest(
            String title,
            String projectType,
            String description,
            String techStack,
            ProjectStatus status,
            Integer progressPercentage,
            String repositoryUrl,
            String demoUrl,
            LocalDate startDate,
            LocalDate targetDate,
            String notes
    ) {}

    public record CreateMilestoneRequest(
            @NotBlank(message = "Title is required") String title,
            String description,
            LocalDate dueDate
    ) {}

    // --- Career DTOs ---
    public record CareerApplicationDto(
            Long id,
            String company,
            String role,
            String jobUrl,
            String sourceReferral,
            LocalDate applicationDate,
            ApplicationStatus status,
            String interviewStage,
            String noticePeriodCompatibility,
            String fixedCompensation,
            String variableCompensation,
            String equity,
            String notes,
            LocalDate nextActionDate
    ) {}

    public record CreateApplicationRequest(
            @NotBlank(message = "Company is required") String company,
            @NotBlank(message = "Role is required") String role,
            String jobUrl,
            String sourceReferral,
            @NotNull(message = "Application date is required") LocalDate applicationDate,
            ApplicationStatus status,
            String interviewStage,
            String noticePeriodCompatibility,
            String fixedCompensation,
            String variableCompensation,
            String equity,
            String notes,
            LocalDate nextActionDate
    ) {}

    public record UpdateApplicationRequest(
            String company,
            String role,
            String jobUrl,
            String sourceReferral,
            LocalDate applicationDate,
            ApplicationStatus status,
            String interviewStage,
            String noticePeriodCompatibility,
            String fixedCompensation,
            String variableCompensation,
            String equity,
            String notes,
            LocalDate nextActionDate
    ) {}

    public record MockInterviewDto(
            Long id,
            String interviewType,
            LocalDate interviewDate,
            int durationMinutes,
            Integer score,
            String strengths,
            String weaknesses,
            String feedback,
            String nextActions
    ) {}

    public record CreateMockInterviewRequest(
            @NotBlank(message = "Interview type is required") String interviewType,
            @NotNull(message = "Date is required") LocalDate interviewDate,
            @Min(1) int durationMinutes,
            Integer score,
            String strengths,
            String weaknesses,
            String feedback,
            String nextActions
    ) {}

    // --- Dashboard & Analytics DTOs ---
    public record DashboardSummaryDto(
            int currentWeek,
            long daysUntilStart,
            boolean isStarted,
            String currentPhaseCode,
            String currentPhaseTitle,
            String engineeringFocus,
            String aiDeliverable,
            long totalTasksCompleted,
            long totalTasksPlanned,
            int overallCompletionPercentage,
            long dsaSolvedCount,
            int dsaTargetMin,
            int dsaTargetMax,
            BigDecimal plannedHoursThisWeek,
            BigDecimal actualHoursThisWeek,
            int currentStreak,
            List<TaskDto> todayTasks,
            List<TaskDto> overdueTasks,
            List<TaskDto> currentWeekTasks,
            List<ProjectDto> projectsSummary,
            AiStatusDto aiStatus
    ) {}

    public record AnalyticsDto(
            Map<String, BigDecimal> weeklyPlannedVsActualHours,
            Map<TaskCategory, BigDecimal> categoryHoursDistribution,
            Map<String, Integer> phaseCompletionRates,
            DsaStatsDto dsaStats,
            int streakDays,
            Map<ApplicationStatus, Long> applicationFunnel
    ) {}

    // --- Search DTO ---
    public record SearchResultDto(
            List<TaskDto> tasks,
            List<DsaProblemDto> dsaProblems,
            List<ProjectDto> projects,
            List<CareerApplicationDto> applications,
            List<ResourceDto> resources
    ) {}

    public record ResourceDto(
            Long id,
            String title,
            String url,
            String resourceType,
            String category,
            String status,
            String notes
    ) {}

    // --- AI DTOs ---
    public record AiChatRequest(
            Long conversationId,
            @NotBlank(message = "Message content is required") String message,
            String mode, // "HINTS_FIRST", "EXPLAIN", "QUIZ", "CODE_REVIEW", "MOCK_INTERVIEW", "EDGE_CASES", "TEST_SUGGESTION"
            String contextType,
            Long contextId
    ) {}

    public record AiChatResponse(
            Long conversationId,
            Long messageId,
            String reply,
            String model,
            int tokensUsed,
            String notice
    ) {}

    public record AiStatusDto(
            boolean enabled,
            String provider,
            String model,
            int dailyRequestsRemaining,
            int dailyLimit
    ) {}

    public record RateAiMessageRequest(
            @NotNull(message = "Rating is required") VerificationRating rating
    ) {}

    // --- Error Response ---
    public record ErrorResponse(
            int status,
            String error,
            String message,
            OffsetDateTime timestamp,
            List<FieldErrorDetail> fieldErrors
    ) {}

    public record FieldErrorDetail(
            String field,
            String message
    ) {}
}
