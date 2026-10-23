import type {
  AuthResponse,
  UserDto,
  RoadmapDto,
  WeekPlanDto,
  WeekDetailDto,
  TaskDto,
  SessionDto,
  SessionStatsDto,
  DsaProblemDto,
  DsaStatsDto,
  WeeklyReviewDto,
  ProjectDto,
  ProjectMilestoneDto,
  CareerApplicationDto,
  MockInterviewDto,
  DashboardSummaryDto,
  AnalyticsDto,
  SearchResultDto,
  AiStatusDto,
  AiChatResponse,
  VerificationRating,
} from '../types';

const BASE_URL = import.meta.env.VITE_API_URL || '/api/v1';

async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
  const headers = new Headers(options.headers || {});
  headers.set('Content-Type', 'application/json');
  headers.set('X-Requested-With', 'XMLHttpRequest');

  const res = await fetch(`${BASE_URL}${endpoint}`, {
    ...options,
    headers,
    credentials: 'include', // Automatically sends HTTP-only SameSite=Strict cookie
  });

  if (res.status === 204) {
    return {} as T;
  }

  if (!res.ok) {
    let errorMsg = 'An error occurred';
    try {
      const errJson = await res.json();
      errorMsg = errJson.message || errJson.error || errJson.detail || errorMsg;
    } catch {
      errorMsg = `HTTP Error ${res.status}: ${res.statusText}`;
    }
    throw new Error(errorMsg);
  }

  return res.json();
}

export const api = {
  // Auth (HTTP-Only Cookie based; no tokens in localStorage)
  register: (data: any) => request<AuthResponse>('/auth/register', { method: 'POST', body: JSON.stringify(data) }),
  login: (data: any) => request<AuthResponse>('/auth/login', { method: 'POST', body: JSON.stringify(data) }),
  logout: () => request<void>('/auth/logout', { method: 'POST' }),
  getMe: () => request<UserDto>('/auth/me'),
  updateProfile: (data: any) => request<UserDto>('/auth/profile', { method: 'PUT', body: JSON.stringify(data) }),

  // Roadmap & Weeks
  getRoadmap: () => request<RoadmapDto>('/roadmap'),
  getWeeks: () => request<WeekPlanDto[]>('/weeks'),
  getWeekDetail: (weekNumber: number) => request<WeekDetailDto>(`/weeks/${weekNumber}`),

  // Tasks
  createTask: (data: any) => request<TaskDto>('/tasks', { method: 'POST', body: JSON.stringify(data) }),
  updateTask: (id: number, data: any) => request<TaskDto>(`/tasks/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  toggleTask: (id: number) => request<TaskDto>(`/tasks/${id}/toggle`, { method: 'PATCH' }),
  deleteTask: (id: number) => request<void>(`/tasks/${id}`, { method: 'DELETE' }),
  carryForwardTask: (id: number, targetWeekId: number) =>
    request<TaskDto>(`/tasks/${id}/carry-forward`, { method: 'POST', body: JSON.stringify({ targetWeekId }) }),

  // Study Sessions
  logSession: (data: any) => request<SessionDto>('/sessions', { method: 'POST', body: JSON.stringify(data) }),
  getSessions: () => request<SessionDto[]>('/sessions'),
  getSessionStats: () => request<SessionStatsDto>('/sessions/stats'),

  // DSA
  createDsaProblem: (data: any) => request<DsaProblemDto>('/dsa', { method: 'POST', body: JSON.stringify(data) }),
  updateDsaProblem: (id: number, data: any) => request<DsaProblemDto>(`/dsa/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  deleteDsaProblem: (id: number) => request<void>(`/dsa/${id}`, { method: 'DELETE' }),
  getDsaProblems: () => request<DsaProblemDto[]>('/dsa'),
  getDsaStats: () => request<DsaStatsDto>('/dsa/stats'),
  getRevisionQueue: () => request<DsaProblemDto[]>('/dsa/revision-queue'),

  // Reviews
  getReview: (weekId: number) => request<WeeklyReviewDto>(`/reviews/${weekId}`),
  saveReview: (data: any) => request<WeeklyReviewDto>('/reviews', { method: 'POST', body: JSON.stringify(data) }),

  // Projects
  getProjects: () => request<ProjectDto[]>('/projects'),
  createProject: (data: any) => request<ProjectDto>('/projects', { method: 'POST', body: JSON.stringify(data) }),
  updateProject: (id: number, data: any) => request<ProjectDto>(`/projects/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  createMilestone: (projectId: number, data: any) =>
    request<ProjectMilestoneDto>(`/projects/${projectId}/milestones`, { method: 'POST', body: JSON.stringify(data) }),
  toggleMilestone: (milestoneId: number) =>
    request<ProjectMilestoneDto>(`/projects/milestones/${milestoneId}/toggle`, { method: 'PATCH' }),

  // Career
  getApplications: () => request<CareerApplicationDto[]>('/career/applications'),
  createApplication: (data: any) => request<CareerApplicationDto>('/career/applications', { method: 'POST', body: JSON.stringify(data) }),
  updateApplication: (id: number, data: any) => request<CareerApplicationDto>(`/career/applications/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  deleteApplication: (id: number) => request<void>(`/career/applications/${id}`, { method: 'DELETE' }),
  getMockInterviews: () => request<MockInterviewDto[]>('/career/interviews'),
  createMockInterview: (data: any) => request<MockInterviewDto>('/career/interviews', { method: 'POST', body: JSON.stringify(data) }),

  // Dashboard, Analytics & Search
  getDashboard: () => request<DashboardSummaryDto>('/dashboard'),
  getAnalytics: () => request<AnalyticsDto>('/analytics'),
  search: (query: string) => request<SearchResultDto>(`/search?q=${encodeURIComponent(query)}`),

  // AI Assistant
  getAiStatus: () => request<AiStatusDto>('/ai/status'),
  chatAi: (data: { conversationId?: number; message: string; mode?: string; contextType?: string; contextId?: number }) =>
    request<AiChatResponse>('/ai/chat', { method: 'POST', body: JSON.stringify(data) }),
  rateAiMessage: (messageId: number, rating: VerificationRating) =>
    request<void>(`/ai/messages/${messageId}/rate`, { method: 'POST', body: JSON.stringify({ rating }) }),

  // Portability
  exportJson: () => request<any>('/portability/export/json'),
  importJson: (data: any) => request<any>('/portability/import/json', { method: 'POST', body: JSON.stringify(data) }),
  exportSessionsCsvUrl: `${BASE_URL}/portability/export/sessions/csv`,
  exportDsaCsvUrl: `${BASE_URL}/portability/export/dsa/csv`,
};
