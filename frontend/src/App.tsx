import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { AuthProvider, useAuth } from './contexts/AuthContext';
import { ThemeProvider } from './contexts/ThemeContext';
import { ToastProvider } from './components/Toast';
import { AppLayout } from './components/AppLayout';

import { LoginPage } from './pages/LoginPage';
import { DashboardPage } from './pages/DashboardPage';
import { RoadmapPage } from './pages/RoadmapPage';
import { WeekDetailPage } from './pages/WeekDetailPage';
import { TasksPage } from './pages/TasksPage';
import { TimerPage } from './pages/TimerPage';
import { DsaPage } from './pages/DsaPage';
import { WeeklyReviewPage } from './pages/WeeklyReviewPage';
import { ProjectsPage } from './pages/ProjectsPage';
import { CareerPage } from './pages/CareerPage';
import { AnalyticsPage } from './pages/AnalyticsPage';
import { SearchPage } from './pages/SearchPage';
import { AiAssistantPage } from './pages/AiAssistantPage';
import { SettingsPage } from './pages/SettingsPage';

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
      retry: 1,
      staleTime: 1000 * 60 * 2, // 2 minutes
    },
  },
});

const ProtectedRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const { user, loading } = useAuth();

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-primary text-muted text-sm">
        <div className="flex flex-col items-center gap-3">
          <div className="w-8 h-8 border-2 border-primary border-t-transparent rounded-full animate-spin"></div>
          <span>Loading workspace...</span>
        </div>
      </div>
    );
  }

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>;
};

export const App: React.FC = () => {
  return (
    <QueryClientProvider client={queryClient}>
      <ThemeProvider>
        <ToastProvider>
          <AuthProvider>
            <BrowserRouter>
              <Routes>
                <Route path="/login" element={<LoginPage />} />

                <Route
                  path="/"
                  element={
                    <ProtectedRoute>
                      <AppLayout />
                    </ProtectedRoute>
                  }
                >
                  <Route index element={<DashboardPage />} />
                  <Route path="roadmap" element={<RoadmapPage />} />
                  <Route path="weeks/:weekNumber" element={<WeekDetailPage />} />
                  <Route path="tasks" element={<TasksPage />} />
                  <Route path="timer" element={<TimerPage />} />
                  <Route path="dsa" element={<DsaPage />} />
                  <Route path="reviews" element={<WeeklyReviewPage />} />
                  <Route path="projects" element={<ProjectsPage />} />
                  <Route path="career" element={<CareerPage />} />
                  <Route path="analytics" element={<AnalyticsPage />} />
                  <Route path="search" element={<SearchPage />} />
                  <Route path="ai" element={<AiAssistantPage />} />
                  <Route path="settings" element={<SettingsPage />} />
                </Route>

                <Route path="*" element={<Navigate to="/" replace />} />
              </Routes>
            </BrowserRouter>
          </AuthProvider>
        </ToastProvider>
      </ThemeProvider>
    </QueryClientProvider>
  );
};

export default App;
