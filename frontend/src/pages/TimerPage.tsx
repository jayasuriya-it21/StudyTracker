import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { api } from '../services/api';
import { useToast } from '../components/Toast';
import { TimerWidget } from '../components/TimerWidget';
import { Clock, Plus, Calendar, History, BarChart2 } from 'lucide-react';
import { TaskCategory } from '../types';

export const TimerPage: React.FC = () => {
  const queryClient = useQueryClient();
  const { showToast } = useToast();

  const [category, setCategory] = useState<TaskCategory>('DSA');
  const [durationMinutes, setDurationMinutes] = useState(45);
  const [summary, setSummary] = useState('');
  const [interruptionNotes, setInterruptionNotes] = useState('');
  const [sessionDate, setSessionDate] = useState(new Date().toISOString().split('T')[0]);

  const { data: sessions, isLoading } = useQuery({
    queryKey: ['sessions'],
    queryFn: api.getSessions,
  });

  const { data: stats } = useQuery({
    queryKey: ['sessionStats'],
    queryFn: api.getSessionStats,
  });

  const manualLogMutation = useMutation({
    mutationFn: (data: any) => api.logSession(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['sessions'] });
      queryClient.invalidateQueries({ queryKey: ['sessionStats'] });
      queryClient.invalidateQueries({ queryKey: ['dashboard'] });
      showToast('Session logged successfully!', 'success');
      setSummary('');
      setInterruptionNotes('');
    },
    onError: (err: any) => showToast(err.message, 'error'),
  });

  const handleManualSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    manualLogMutation.mutate({
      category,
      durationMinutes,
      sessionDate,
      summary: summary.trim() || undefined,
      interruptionNotes: interruptionNotes.trim() || undefined,
    });
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold tracking-tight">Study Timer & Time Ledger</h1>
        <p className="text-sm text-muted">
          Measure deep work sessions toward your 14h/week preparation target. Accurate tracking prevents burnout and procrastination.
        </p>
      </div>

      {/* Top Stat Cards */}
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
        <div className="stat-card accent-blue">
          <div className="stat-value">{stats?.totalHours || 0}h</div>
          <div className="stat-label">Total Preparation Logged</div>
        </div>
        <div className="stat-card accent-green">
          <div className="stat-value">{stats?.weeklyHours || 0}h</div>
          <div className="stat-label">This Week's Deep Work</div>
        </div>
        <div className="stat-card accent-orange">
          <div className="stat-value">{sessions?.length || 0}</div>
          <div className="stat-label">Completed Sessions</div>
        </div>
        <div className="stat-card accent-purple">
          <div className="stat-value">{stats?.monthlyHours || 0}h</div>
          <div className="stat-label">This Month's Deep Work</div>
        </div>
      </div>

      {/* Live Timer & Manual Logger */}
      <div className="two-col">
        {/* Live Stopwatch */}
        <div className="space-y-4">
          <h3 className="font-semibold text-base flex items-center gap-2">
            <Clock size={18} className="text-primary" />
            <span>Live Session Stopwatch</span>
          </h3>
          <TimerWidget
            onSessionLogged={() => {
              queryClient.invalidateQueries({ queryKey: ['sessions'] });
              queryClient.invalidateQueries({ queryKey: ['sessionStats'] });
            }}
          />
        </div>

        {/* Manual Time Entry */}
        <div className="card p-5 space-y-4">
          <h3 className="font-semibold text-base flex items-center gap-2">
            <Plus size={18} className="text-primary" />
            <span>Log Offline Study Session</span>
          </h3>

          <form onSubmit={handleManualSubmit} className="space-y-3">
            <div className="grid grid-cols-2 gap-3">
              <div>
                <label className="text-xs font-semibold text-muted block mb-1">Category</label>
                <select
                  value={category}
                  onChange={(e) => setCategory(e.target.value as TaskCategory)}
                >
                  <option value="DSA">DSA / Algorithms</option>
                  <option value="JAVA">Java Core</option>
                  <option value="SPRING_BOOT">Spring Boot</option>
                  <option value="SQL">SQL & Database</option>
                  <option value="FLUTTER">Flutter</option>
                  <option value="AI_ASSISTED_DEV">AI-Assisted Dev</option>
                  <option value="AI_APP_ENGINEERING">AI App Engineering</option>
                  <option value="PORTFOLIO_PROJECT">Portfolio Project</option>
                  <option value="APPLICATION_SECURITY">AppSec / DevSecOps</option>
                  <option value="SYSTEM_DESIGN">System Design</option>
                  <option value="CAREER_PREPARATION">Career Prep</option>
                </select>
              </div>

              <div>
                <label className="text-xs font-semibold text-muted block mb-1">Minutes Spent</label>
                <input
                  type="number"
                  min={5}
                  max={720}
                  step={5}
                  value={durationMinutes}
                  onChange={(e) => setDurationMinutes(parseInt(e.target.value, 10))}
                />
              </div>
            </div>

            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Session Date</label>
              <input
                type="date"
                value={sessionDate}
                onChange={(e) => setSessionDate(e.target.value)}
              />
            </div>

            <div>
              <label className="text-xs font-semibold text-muted block mb-1">
                Summary / Focus (Optional)
              </label>
              <input
                type="text"
                placeholder="e.g. Practiced Binary Tree BFS/DFS, solved 2 Mediums"
                value={summary}
                onChange={(e) => setSummary(e.target.value)}
              />
            </div>

            <div>
              <label className="text-xs font-semibold text-muted block mb-1">
                Distractions / Friction (Optional)
              </label>
              <input
                type="text"
                placeholder="e.g. Work defect interruptions, phone alerts"
                value={interruptionNotes}
                onChange={(e) => setInterruptionNotes(e.target.value)}
              />
            </div>

            <button
              type="submit"
              className="btn btn-primary w-full"
              disabled={manualLogMutation.isPending}
            >
              {manualLogMutation.isPending ? 'Logging...' : 'Save Study Session'}
            </button>
          </form>
        </div>
      </div>

      {/* History Ledger Table */}
      <div className="card p-5 space-y-4">
        <div className="flex justify-between items-center">
          <h3 className="font-semibold text-base flex items-center gap-2">
            <History size={18} className="text-primary" />
            <span>Recent Sessions History</span>
          </h3>
          <span className="text-xs text-muted">Showing up to 50 recent entries</span>
        </div>

        {isLoading ? (
          <div className="h-32 bg-card-hover rounded animate-pulse"></div>
        ) : !sessions || sessions.length === 0 ? (
          <p className="text-sm text-muted text-center py-8">No sessions recorded yet. Start the stopwatch or log one above!</p>
        ) : (
          <div className="table-container">
            <table>
              <thead>
                <tr>
                  <th>Date</th>
                  <th>Category</th>
                  <th>Duration</th>
                  <th>Summary</th>
                  <th>Task Link</th>
                </tr>
              </thead>
              <tbody>
                {sessions.map((s) => (
                  <tr key={s.id}>
                    <td className="font-mono text-xs">{s.sessionDate}</td>
                    <td>
                      <span className="category-badge badge-dsa text-[10px]">
                        {s.category.replace('_', ' ')}
                      </span>
                    </td>
                    <td className="font-bold text-sm">
                      {Math.floor(s.durationMinutes / 60)}h {s.durationMinutes % 60}m
                    </td>
                    <td className="text-xs text-muted">{s.summary || '—'}</td>
                    <td className="text-xs">{s.taskTitle || 'Ad-hoc session'}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};
