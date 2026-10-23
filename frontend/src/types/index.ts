export type TaskCategory =
  | 'DSA'
  | 'JAVA'
  | 'SPRING_BOOT'
  | 'SQL'
  | 'FLUTTER'
  | 'AI_ASSISTED_DEV'
  | 'AI_APP_ENGINEERING'
  | 'PORTFOLIO_PROJECT'
  | 'APPLICATION_SECURITY'
  | 'DEVSECOPS'
  | 'SYSTEM_DESIGN'
  | 'CAREER_PREPARATION'
  | 'INTERVIEW_PREPARATION'
  | 'OTHER';

export type TaskStatus = 'NOT_STARTED' | 'IN_PROGRESS' | 'COMPLETED' | 'CARRIED_FORWARD';
export type Priority = 'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT';
export type TaskSource = 'SEEDED' | 'CUSTOM';
export type Difficulty = 'EASY' | 'MEDIUM' | 'HARD';
export type DsaStatus = 'SOLVED' | 'ATTEMPTED' | 'REVISIT';
export type ReviewStatus = 'DRAFT' | 'FINALIZED';
export type ProjectStatus = 'NOT_STARTED' | 'IN_PROGRESS' | 'COMPLETED';
export type MilestoneStatus = 'PENDING' | 'COMPLETED';
export type ApplicationStatus =
  | 'WISHLIST'
  | 'APPLIED'
  | 'SCREENING'
  | 'TECHNICAL_ROUND'
  | 'SYSTEM_DESIGN'
  | 'HR_ROUND'
  | 'OFFER'
  | 'REJECTED'
  | 'WITHDRAWN';

export type VerificationRating = 'UNRATED' | 'VERIFIED' | 'PARTIALLY_CORRECT' | 'INCORRECT';

export interface UserDto {
  id: number;
  name: string;
  username: string;
  email: string;
  timezone: string;
  weeklyTargetHours: number;
  roadmapStartDate: string;
  themePreference: string;
  role: string;
}

export interface AuthResponse {
  token: string;
  user: UserDto;
}

export interface PhaseDto {
  id: number;
  code: string;
  title: string;
  description: string;
  startWeek: number;
  endWeek: number;
  color: string;
  icon: string;
  displayOrder: number;
  completionPercentage: number;
}

export interface RoadmapDto {
  id: number;
  title: string;
  startDate: string;
  endDate: string;
  targetRole: string;
  targetDsaMin: number;
  targetDsaMax: number;
  targetWeeklyHours: number;
  phases: PhaseDto[];
}

export interface WeekPlanDto {
  id: number;
  weekNumber: number;
  startDate: string;
  endDate: string;
  phaseCode: string;
  phaseTitle: string;
  engineeringFocus: string;
  aiReadinessDeliverable: string;
  milestoneDescription?: string;
  plannedHours: number;
  status: TaskStatus;
  completionPercentage: number;
  totalTasks: number;
  completedTasks: number;
}

export interface TaskDto {
  id: number;
  weekId: number;
  weekNumber: number;
  title: string;
  description?: string;
  category: TaskCategory;
  priority: Priority;
  estimateMinutes: number;
  actualMinutes: number;
  dueDate?: string;
  status: TaskStatus;
  completedAt?: string;
  source: TaskSource;
  sortOrder: number;
}

export interface WeeklyReviewDto {
  id: number;
  weekId: number;
  weekNumber: number;
  whatStudied?: string;
  whatCompleted?: string;
  whatAiGotRight?: string;
  whatAiGotWrong?: string;
  whatVerifiedIndependently?: string;
  wins?: string;
  blockers?: string;
  areasToImprove?: string;
  nextWeekPriorities?: string;
  selfRating?: number;
  totalActualHours: number;
  status: ReviewStatus;
  finalizedAt?: string;
  updatedAt: string;
}

export interface WeekDetailDto {
  weekPlan: WeekPlanDto;
  tasks: TaskDto[];
  review?: WeeklyReviewDto;
  loggedHours: number;
}

export interface SessionDto {
  id: number;
  taskId?: number;
  taskTitle?: string;
  category: TaskCategory;
  sessionDate: string;
  durationMinutes: number;
  summary?: string;
  interruptionNotes?: string;
  createdAt: string;
}

export interface SessionStatsDto {
  totalHours: number;
  weeklyHours: number;
  monthlyHours: number;
  hoursByCategory: Record<string, number>;
}

export interface DsaProblemDto {
  id: number;
  title: string;
  platform: string;
  url?: string;
  topicPattern: string;
  difficulty: Difficulty;
  dateAttempted: string;
  status: DsaStatus;
  attemptCount: number;
  timeTakenMinutes?: number;
  hintsUsed: number;
  couldSolveWithoutAi: boolean;
  complexityExplanation?: string;
  notes?: string;
  nextReviewDate?: string;
  confidenceScore: number;
}

export interface DsaStatsDto {
  totalSolved: number;
  totalAttempted: number;
  targetMin: number;
  targetMax: number;
  byDifficulty: Record<string, number>;
  byTopic: Record<string, number>;
  weakTopics: string[];
  dueForReviewCount: number;
}

export interface ProjectMilestoneDto {
  id: number;
  projectId: number;
  title: string;
  description?: string;
  dueDate?: string;
  status: MilestoneStatus;
  completedDate?: string;
}

export interface ProjectDto {
  id: number;
  title: string;
  projectType: string;
  description?: string;
  techStack?: string;
  status: ProjectStatus;
  progressPercentage: number;
  repositoryUrl?: string;
  demoUrl?: string;
  startDate?: string;
  targetDate?: string;
  notes?: string;
  milestones: ProjectMilestoneDto[];
}

export interface CareerApplicationDto {
  id: number;
  company: string;
  role: string;
  jobUrl?: string;
  sourceReferral?: string;
  applicationDate: string;
  status: ApplicationStatus;
  interviewStage?: string;
  noticePeriodCompatibility?: string;
  fixedCompensation?: string;
  variableCompensation?: string;
  equity?: string;
  notes?: string;
  nextActionDate?: string;
}

export interface MockInterviewDto {
  id: number;
  interviewType: string;
  interviewDate: string;
  durationMinutes: number;
  score?: number;
  strengths?: string;
  weaknesses?: string;
  feedback?: string;
  nextActions?: string;
}

export interface DashboardSummaryDto {
  currentWeek: number;
  daysUntilStart: number;
  isStarted: boolean;
  currentPhaseCode: string;
  currentPhaseTitle: string;
  engineeringFocus: string;
  aiDeliverable: string;
  totalTasksCompleted: number;
  totalTasksPlanned: number;
  overallCompletionPercentage: number;
  dsaSolvedCount: number;
  dsaTargetMin: number;
  dsaTargetMax: number;
  plannedHoursThisWeek: number;
  actualHoursThisWeek: number;
  currentStreak: number;
  todayTasks: TaskDto[];
  overdueTasks: TaskDto[];
  currentWeekTasks: TaskDto[];
  projectsSummary: ProjectDto[];
  aiStatus: AiStatusDto;
}

export interface AnalyticsDto {
  weeklyPlannedVsActualHours: Record<string, number>;
  categoryHoursDistribution: Record<string, number>;
  phaseCompletionRates: Record<string, number>;
  dsaStats: DsaStatsDto;
  streakDays: number;
  applicationFunnel: Record<string, number>;
}

export interface SearchResultDto {
  tasks: TaskDto[];
  dsaProblems: DsaProblemDto[];
  projects: ProjectDto[];
  applications: CareerApplicationDto[];
  resources: any[];
}

export interface AiStatusDto {
  enabled: boolean;
  provider: string;
  model: string;
  dailyRequestsRemaining: number;
  dailyLimit: number;
}

export interface AiChatResponse {
  conversationId: number;
  messageId: number;
  reply: string;
  model: string;
  tokensUsed: number;
  notice: string;
}
