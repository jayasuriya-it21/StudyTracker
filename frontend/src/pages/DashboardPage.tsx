import React from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api';
import { useToast } from '../components/Toast';
import { TimerWidget } from '../components/TimerWidget';
import {
  Calendar,
  Layers,
  CheckCircle2,
  Code2,
  Clock,
  Flame,
  ArrowRight,
  AlertCircle,
  Sparkles,
  Bot,
} from 'lucide-react';

export const DashboardPage: React.FC = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const { showToast } = useToast();

  const { data: dash, isLoading, error } = useQuery({
    queryKey: ['dashboard'],
    queryFn: api.getDashboard,
  });

  const toggleTaskMutation = useMutation({
    mutationFn: (taskId: number) => api.toggleTask(taskId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['dashboard'] });
      queryClient.invalidateQueries({ queryKey: ['weeks'] });
      showToast('Task updated!', 'success');
    },
    onError: (err: any) => showToast(err.message, 'error'),
  });

  if (isLoading) {
    return (
      <div className="space-y-4">
        <div className="h-8 bg-card rounded w-1/3 animate-pulse"></div>
        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-3">
          {[...Array(6)].map((_, i) => (
            <div key={i} className="h-24 bg-card rounded-xl animate-pulse"></div>
          ))}
        </div>
      </div>
    );
  }

  if (error || !dash) {
    return (
      <div className="card p-8 text-center text-red-500">
        <AlertCircle size={40} className="mx-auto mb-2" />
        <h3 className="font-bold">Failed to load dashboard metrics</h3>
        <p className="text-sm text-muted">{(error as any)?.message || 'Unknown error'}</p>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* Header Banner */}
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-3">
        <div>
          <h1 className="text-2xl font-bold tracking-tight">Career Dashboard</h1>
          <p className="text-sm text-muted">
            Jayasuriya's Transition Roadmap: Java/Flutter (WCAS) → Secure AI Product Engineer
          </p>
        </div>
        <div className="flex items-center gap-2">
          <span className="category-badge badge-career text-xs px-3 py-1 font-semibold">
            🎯 Target: ₹30–40 LPA Ambition
          </span>
        </div>
      </div>

      {/* Countdown Alert (if before roadmap start date 2 Nov 2026) */}
      {!dash.isStarted && (
        <div className="p-4 rounded-xl border border-amber-500/30 bg-amber-500/10 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <Sparkles className="text-amber-500" size={24} />
            <div>
              <h4 className="font-semibold text-sm text-amber-500">
                Roadmap Kick-off in {dash.daysUntilStart} days! (Starts Monday, 2 Nov 2026)
              </h4>
              <p className="text-xs text-muted">
                Pre-start preparation phase. You can practice fundamental problems and configure your toolchain early!
              </p>
            </div>
          </div>
          <button
            className="btn btn-secondary btn-sm"
            onClick={() => navigate('/roadmap')}
          >
            Preview Curriculum
          </button>
        </div>
      )}

      {/* Top Stat Cards */}
      <div className="dashboard-grid">
        <div className="stat-card accent-blue">
          <div className="stat-icon"><Calendar size={22} className="text-blue-500" /></div>
          <div className="stat-value">Week {dash.currentWeek}</div>
          <div className="stat-label">
            {dash.isStarted ? 'Current Week' : `Kick-off in ${dash.daysUntilStart}d`}
          </div>
        </div>

        <div className="stat-card accent-purple">
          <div className="stat-icon"><Layers size={22} className="text-purple-500" /></div>
          <div className="stat-value text-lg">Phase {dash.currentPhaseCode}</div>
          <div className="stat-label truncate">{dash.currentPhaseTitle}</div>
        </div>

        <div className="stat-card accent-green">
          <div className="stat-icon"><CheckCircle2 size={22} className="text-emerald-500" /></div>
          <div className="stat-value">
            {dash.totalTasksCompleted} / {dash.totalTasksPlanned}
          </div>
          <div className="stat-label">{dash.overallCompletionPercentage}% Deliverables Done</div>
        </div>

        <div className="stat-card accent-orange">
          <div className="stat-icon"><Code2 size={22} className="text-amber-500" /></div>
          <div className="stat-value">{dash.dsaSolvedCount}</div>
          <div className="stat-label">DSA Solved (Goal: {dash.dsaTargetMin}–{dash.dsaTargetMax})</div>
        </div>

        <div className="stat-card accent-cyan">
          <div className="stat-icon"><Clock size={22} className="text-cyan-500" /></div>
          <div className="stat-value">{dash.actualHoursThisWeek}h</div>
          <div className="stat-label">Logged (Target: {dash.plannedHoursThisWeek}h)</div>
        </div>

        <div className="stat-card accent-red">
          <div className="stat-icon"><Flame size={22} className="text-red-500" /></div>
          <div className="stat-value">{dash.currentStreak}d</div>
          <div className="stat-label">Active Streak</div>
        </div>
      </div>

      {/* Two Column Layout: Current Week Focus & Actionable Tasks */}
      <div className="two-col">
        {/* Left: Week Focus & Projects */}
        <div className="space-y-4">
          <div className="card p-5">
            <div className="flex justify-between items-center mb-3">
              <h3 className="font-semibold text-base flex items-center gap-2">
                <span>🎯 Week {dash.currentWeek} Focus</span>
                <span className="category-badge badge-java text-[10px]">Phase {dash.currentPhaseCode}</span>
              </h3>
              <button
                className="btn btn-secondary btn-sm text-xs"
                onClick={() => navigate(`/weeks/${dash.currentWeek}`)}
              >
                <span>Full Details</span>
                <ArrowRight size={14} />
              </button>
            </div>

            <div className="space-y-3">
              <div className="p-3 rounded-lg bg-card-hover border border-border">
                <span className="text-[11px] font-semibold text-muted uppercase tracking-wider block mb-1">
                  ⚙️ Software Engineering & DSA
                </span>
                <p className="text-sm font-semibold text-primary">{dash.engineeringFocus}</p>
              </div>

              <div className="p-3 rounded-lg bg-card-hover border border-border">
                <span className="text-[11px] font-semibold text-muted uppercase tracking-wider block mb-1">
                  🤖 AI Readiness & Practical Deliverable
                </span>
                <p className="text-sm font-semibold text-primary">{dash.aiDeliverable}</p>
              </div>
            </div>
          </div>

          {/* Active Timer Widget */}
          <TimerWidget onSessionLogged={() => queryClient.invalidateQueries({ queryKey: ['dashboard'] })} />
        </div>

        {/* Right: Quick Action Checklist */}
        <div className="card p-5 flex flex-col justify-between">
          <div>
            <div className="flex justify-between items-center mb-3">
              <h3 className="font-semibold text-base flex items-center gap-2">
                <span>⚡ Week {dash.currentWeek} Action Checklist</span>
                <span className="text-xs text-muted">
                  ({dash.currentWeekTasks.filter((t) => t.status === 'COMPLETED').length}/
                  {dash.currentWeekTasks.length})
                </span>
              </h3>
              <button
                className="btn btn-primary btn-sm text-xs"
                onClick={() => navigate('/tasks')}
              >
                + Add Task
              </button>
            </div>

            <div className="task-list max-h-[360px] overflow-y-auto pr-1">
              {dash.currentWeekTasks.length === 0 ? (
                <p className="text-sm text-muted py-4 text-center">No tasks assigned for this week.</p>
              ) : (
                dash.currentWeekTasks.map((task) => (
                  <div
                    key={task.id}
                    className={`task-item ${task.status === 'COMPLETED' ? 'completed' : ''}`}
                  >
                    <input
                      type="checkbox"
                      checked={task.status === 'COMPLETED'}
                      onChange={() => toggleTaskMutation.mutate(task.id)}
                      id={`task-${task.id}`}
                    />
                    <label htmlFor={`task-${task.id}`} className="task-text cursor-pointer">
                      {task.title}
                    </label>
                    <span className="category-badge badge-dsa text-[10px]">
                      {task.category.replace('_', ' ')}
                    </span>
                  </div>
                ))
              )}
            </div>
          </div>

          {/* Overdue Alert if any */}
          {dash.overdueTasks.length > 0 && (
            <div className="mt-4 p-3 rounded-lg bg-red-500/10 border border-red-500/20 flex items-center gap-2">
              <AlertCircle size={16} className="text-red-500 shrink-0" />
              <span className="text-xs text-red-500 font-medium">
                {dash.overdueTasks.length} task(s) overdue. Review in Tasks tab.
              </span>
            </div>
          )}
        </div>
      </div>

      {/* Flagship Projects Quick Row */}
      <div className="card p-5">
        <div className="flex justify-between items-center mb-4">
          <div>
            <h3 className="font-semibold text-base">🚀 Portfolio Projects Status</h3>
            <p className="text-xs text-muted">Real evidence that proves your engineering capability</p>
          </div>
          <button className="btn btn-secondary btn-sm" onClick={() => navigate('/projects')}>
            View All Projects →
          </button>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          {dash.projectsSummary.map((p) => (
            <div key={p.id} className="p-4 rounded-xl border border-border bg-card-hover space-y-2">
              <div className="flex justify-between items-center">
                <span
                  className={`text-[10px] font-bold uppercase tracking-wider px-2 py-0.5 rounded-full ${
                    p.projectType === 'Flagship'
                      ? 'bg-amber-500/10 text-amber-500'
                      : 'bg-blue-500/10 text-blue-500'
                  }`}
                >
                  {p.projectType}
                </span>
                <span className="font-mono text-xs font-semibold">{p.progressPercentage}%</span>
              </div>
              <h4 className="font-semibold text-sm">{p.title}</h4>
              <div className="progress-bar">
                <div
                  className={`progress-fill ${p.projectType === 'Flagship' ? 'green' : ''}`}
                  style={{ width: `${p.progressPercentage}%` }}
                ></div>
              </div>
              <p className="text-xs text-muted truncate">{p.techStack}</p>
            </div>
          ))}
        </div>
      </div>

      {/* AI Assistant Banner */}
      <div className="p-4 rounded-xl border border-purple-500/20 bg-purple-500/10 flex flex-col sm:flex-row justify-between items-start sm:items-center gap-3">
        <div className="flex items-center gap-3">
          <Bot className="text-purple-500 shrink-0" size={24} />
          <div>
            <h4 className="font-semibold text-sm text-purple-400">
              AI Study Assistant (Educational & Hints-First Mode)
            </h4>
            <p className="text-xs text-muted">
              Active Provider: <strong>{dash.aiStatus.provider}</strong> ({dash.aiStatus.model}) ·{' '}
              {dash.aiStatus.dailyRequestsRemaining} requests remaining today.
            </p>
          </div>
        </div>
        <button className="btn btn-primary btn-sm text-xs" onClick={() => navigate('/ai')}>
          Open AI Coach
        </button>
      </div>
    </div>
  );
};
