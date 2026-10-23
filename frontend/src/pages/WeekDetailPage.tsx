import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { api } from '../services/api';
import { useToast } from '../components/Toast';
import { Modal } from '../components/Modal';
import {
  ArrowLeft,
  Calendar,
  Clock,
  CheckCircle2,
  Plus,
  Send,
  Bot,
  AlertCircle,
  Forward,
  Save,
  CheckCheck,
} from 'lucide-react';
import { TaskCategory, Priority } from '../types';

export const WeekDetailPage: React.FC = () => {
  const { weekNumber } = useParams<{ weekNumber: string }>();
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const { showToast } = useToast();

  const num = parseInt(weekNumber || '1', 10);

  const { data: detail, isLoading, error } = useQuery({
    queryKey: ['weekDetail', num],
    queryFn: () => api.getWeekDetail(num),
  });

  // Task creation modal state
  const [isTaskModalOpen, setIsTaskModalOpen] = useState(false);
  const [newTitle, setNewTitle] = useState('');
  const [newCategory, setNewCategory] = useState<TaskCategory>('DSA');
  const [newPriority, setNewPriority] = useState<Priority>('MEDIUM');
  const [newEstimate, setNewEstimate] = useState(60);

  // Carry forward modal state
  const [carryTaskId, setCarryTaskId] = useState<number | null>(null);
  const [targetWeekNum, setTargetWeekNum] = useState<number>(num + 1);

  // Review state
  const [whatStudied, setWhatStudied] = useState('');
  const [whatAiGotRight, setWhatAiGotRight] = useState('');
  const [whatAiGotWrong, setWhatAiGotWrong] = useState('');
  const [whatVerified, setWhatVerified] = useState('');
  const [blockers, setBlockers] = useState('');
  const [reviewInitialized, setReviewInitialized] = useState(false);

  // Initialize review fields once loaded
  if (detail?.review && !reviewInitialized) {
    setWhatStudied(detail.review.whatStudied || '');
    setWhatAiGotRight(detail.review.whatAiGotRight || '');
    setWhatAiGotWrong(detail.review.whatAiGotWrong || '');
    setWhatVerified(detail.review.whatVerifiedIndependently || '');
    setBlockers(detail.review.blockers || '');
    setReviewInitialized(true);
  }

  // Mutations
  const toggleMutation = useMutation({
    mutationFn: (id: number) => api.toggleTask(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['weekDetail', num] });
      queryClient.invalidateQueries({ queryKey: ['weeks'] });
      queryClient.invalidateQueries({ queryKey: ['dashboard'] });
      showToast('Task updated!', 'success');
    },
  });

  const createTaskMutation = useMutation({
    mutationFn: (data: any) => api.createTask(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['weekDetail', num] });
      queryClient.invalidateQueries({ queryKey: ['weeks'] });
      showToast('Task added!', 'success');
      setIsTaskModalOpen(false);
      setNewTitle('');
    },
  });

  const carryForwardMutation = useMutation({
    mutationFn: ({ taskId, targetWeekId }: { taskId: number; targetWeekId: number }) =>
      api.carryForwardTask(taskId, targetWeekId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['weekDetail', num] });
      queryClient.invalidateQueries({ queryKey: ['weeks'] });
      showToast('Task carried forward to next week!', 'success');
      setCarryTaskId(null);
    },
  });

  const saveReviewMutation = useMutation({
    mutationFn: (status: 'DRAFT' | 'FINALIZED') => {
      return api.saveReview({
        weekId: detail!.weekPlan.id,
        whatStudied,
        whatAiGotRight,
        whatAiGotWrong,
        whatVerifiedIndependently: whatVerified,
        blockers,
        status,
      });
    },
    onSuccess: (_, status) => {
      queryClient.invalidateQueries({ queryKey: ['weekDetail', num] });
      showToast(status === 'FINALIZED' ? 'Review finalized!' : 'Draft saved!', 'success');
    },
  });

  if (isLoading) {
    return (
      <div className="space-y-4">
        <div className="h-8 bg-card rounded w-1/4 animate-pulse"></div>
        <div className="h-64 bg-card rounded-xl animate-pulse"></div>
      </div>
    );
  }

  if (error || !detail) {
    return (
      <div className="card p-8 text-center text-red-500">
        <AlertCircle size={40} className="mx-auto mb-2" />
        <h3 className="font-bold">Week {num} Not Found</h3>
        <button className="btn btn-secondary btn-sm mt-3" onClick={() => navigate('/roadmap')}>
          Back to Roadmap
        </button>
      </div>
    );
  }

  const { weekPlan, tasks, loggedHours } = detail;

  const handleCreateTask = (e: React.FormEvent) => {
    e.preventDefault();
    if (!newTitle.trim()) return;
    createTaskMutation.mutate({
      weekId: weekPlan.id,
      title: newTitle.trim(),
      category: newCategory,
      priority: newPriority,
      estimateMinutes: newEstimate,
    });
  };

  return (
    <div className="space-y-6">
      {/* Navigation & Header */}
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <div className="flex items-center gap-3">
          <button className="btn btn-secondary btn-sm" onClick={() => navigate('/roadmap')}>
            <ArrowLeft size={16} />
            <span>Curriculum</span>
          </button>
          <div>
            <h1 className="text-2xl font-bold tracking-tight">Week {weekPlan.weekNumber} Plan</h1>
            <p className="text-xs text-muted">Phase {weekPlan.phaseCode} · {weekPlan.phaseTitle}</p>
          </div>
        </div>

        <div className="flex items-center gap-2">
          <button
            className="btn btn-secondary btn-sm"
            disabled={num <= 1}
            onClick={() => {
              setReviewInitialized(false);
              navigate(`/weeks/${num - 1}`);
            }}
          >
            ← Week {num - 1}
          </button>
          <button
            className="btn btn-secondary btn-sm"
            disabled={num >= 52}
            onClick={() => {
              setReviewInitialized(false);
              navigate(`/weeks/${num + 1}`);
            }}
          >
            Week {num + 1} →
          </button>
        </div>
      </div>

      {/* Week Focus Card */}
      <div className="card p-5 space-y-4">
        <div className="flex flex-wrap items-center justify-between gap-3 border-b border-border pb-3">
          <div className="flex items-center gap-4 text-xs text-muted">
            <span className="flex items-center gap-1.5">
              <Calendar size={14} className="text-primary" />
              <strong>{weekPlan.startDate}</strong> to <strong>{weekPlan.endDate}</strong>
            </span>
            <span className="flex items-center gap-1.5">
              <Clock size={14} className="text-primary" />
              Target: <strong>{weekPlan.plannedHours}h</strong> | Logged: <strong>{loggedHours.toFixed(1)}h</strong>
            </span>
          </div>
          <span className="category-badge badge-career font-semibold">
            {weekPlan.completedTasks} / {weekPlan.totalTasks} Tasks Completed ({weekPlan.completionPercentage}%)
          </span>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div className="p-4 rounded-xl bg-card-hover border border-border">
            <span className="text-xs font-semibold text-muted uppercase tracking-wider block mb-1">
              ⚙️ Engineering Focus
            </span>
            <p className="text-sm font-semibold">{weekPlan.engineeringFocus}</p>
          </div>
          <div className="p-4 rounded-xl bg-card-hover border border-border">
            <span className="text-xs font-semibold text-muted uppercase tracking-wider block mb-1">
              🤖 AI Readiness & Deliverable
            </span>
            <p className="text-sm font-semibold">{weekPlan.aiReadinessDeliverable}</p>
          </div>
        </div>

        {weekPlan.milestoneDescription && (
          <div className="p-3 rounded-lg bg-primary/10 border border-primary/20 text-xs">
            <strong>Key Milestone:</strong> {weekPlan.milestoneDescription}
          </div>
        )}
      </div>

      {/* Two Column Layout: Deliverables & Weekly Review */}
      <div className="two-col">
        {/* Left Column: Tasks / Deliverables */}
        <div className="card p-5 space-y-4">
          <div className="flex justify-between items-center">
            <div>
              <h3 className="font-semibold text-base">Weekly Deliverables</h3>
              <p className="text-xs text-muted">Check off items as you complete and verify them</p>
            </div>
            <button className="btn btn-primary btn-sm" onClick={() => setIsTaskModalOpen(true)}>
              <Plus size={14} />
              <span>Add Custom Task</span>
            </button>
          </div>

          <div className="task-list">
            {tasks.map((task) => (
              <div
                key={task.id}
                className={`task-item justify-between ${task.status === 'COMPLETED' ? 'completed' : ''}`}
              >
                <div className="flex items-center gap-3 flex-1 min-w-0">
                  <input
                    type="checkbox"
                    checked={task.status === 'COMPLETED'}
                    onChange={() => toggleMutation.mutate(task.id)}
                    id={`w-task-${task.id}`}
                  />
                  <div className="truncate">
                    <label
                      htmlFor={`w-task-${task.id}`}
                      className="task-text cursor-pointer block truncate font-medium text-sm"
                    >
                      {task.title}
                    </label>
                    <span className="text-[11px] text-muted">
                      ~{task.estimateMinutes}m · {task.priority} Priority
                    </span>
                  </div>
                </div>

                <div className="flex items-center gap-2 shrink-0">
                  <span className="category-badge badge-dsa text-[10px]">
                    {task.category.replace('_', ' ')}
                  </span>
                  {task.status !== 'COMPLETED' && (
                    <button
                      className="p-1 text-muted hover:text-primary"
                      title="Carry forward to another week"
                      onClick={() => setCarryTaskId(task.id)}
                    >
                      <Forward size={14} />
                    </button>
                  )}
                </div>
              </div>
            ))}
          </div>

          <div className="p-3 rounded-lg bg-card-hover border border-border flex items-center justify-between">
            <div className="flex items-center gap-2">
              <Bot size={18} className="text-purple-500" />
              <span className="text-xs">Need hints or review for this week's topics?</span>
            </div>
            <button
              className="btn btn-secondary btn-sm text-xs"
              onClick={() => navigate('/ai')}
            >
              Ask AI Assistant
            </button>
          </div>
        </div>

        {/* Right Column: Weekly Reflection Journal */}
        <div className="card p-5 space-y-4">
          <div className="flex justify-between items-center">
            <div>
              <h3 className="font-semibold text-base">Weekly Reflection & Verification</h3>
              <p className="text-xs text-muted">
                {detail.review?.status === 'FINALIZED' ? 'Finalized review' : 'Draft mode'}
              </p>
            </div>
            <div className="flex items-center gap-2">
              <button
                className="btn btn-secondary btn-sm"
                onClick={() => saveReviewMutation.mutate('DRAFT')}
                disabled={saveReviewMutation.isPending}
              >
                <Save size={14} />
                <span>Save Draft</span>
              </button>
              <button
                className="btn btn-primary btn-sm"
                onClick={() => saveReviewMutation.mutate('FINALIZED')}
                disabled={saveReviewMutation.isPending}
              >
                <CheckCheck size={14} />
                <span>Finalize</span>
              </button>
            </div>
          </div>

          <div className="space-y-3">
            <div>
              <label className="text-xs font-semibold text-muted block mb-1">
                1. What was studied / implemented this week?
              </label>
              <textarea
                rows={2}
                value={whatStudied}
                onChange={(e) => setWhatStudied(e.target.value)}
                placeholder="Key concepts, algorithms, code written..."
              />
            </div>

            <div>
              <label className="text-xs font-semibold text-muted block mb-1">
                2. What did AI get right?
              </label>
              <textarea
                rows={2}
                value={whatAiGotRight}
                onChange={(e) => setWhatAiGotRight(e.target.value)}
                placeholder="Accurate explanations, helpful boilerplates, test ideas..."
              />
            </div>

            <div>
              <label className="text-xs font-semibold text-muted block mb-1">
                3. What did AI get wrong?
              </label>
              <textarea
                rows={2}
                value={whatAiGotWrong}
                onChange={(e) => setWhatAiGotWrong(e.target.value)}
                placeholder="Hallucinations, subtle security bugs, inefficient edge-cases..."
              />
            </div>

            <div>
              <label className="text-xs font-semibold text-muted block mb-1">
                4. What did you verify independently? (Crucial Engineering Skill)
              </label>
              <textarea
                rows={2}
                value={whatVerified}
                onChange={(e) => setWhatVerified(e.target.value)}
                placeholder="Unit tests written, complexity checked, OWASP guidelines verified..."
              />
            </div>

            <div>
              <label className="text-xs font-semibold text-muted block mb-1">
                5. Blockers & next week priorities
              </label>
              <textarea
                rows={2}
                value={blockers}
                onChange={(e) => setBlockers(e.target.value)}
                placeholder="Stuck on DP, need more Spring Security practice..."
              />
            </div>
          </div>
        </div>
      </div>

      {/* Custom Task Modal */}
      <Modal
        isOpen={isTaskModalOpen}
        onClose={() => setIsTaskModalOpen(false)}
        title={`Add Custom Task to Week ${weekPlan.weekNumber}`}
      >
        <form onSubmit={handleCreateTask} className="space-y-4">
          <div>
            <label className="text-xs font-semibold text-muted block mb-1">Task Title *</label>
            <input
              type="text"
              required
              value={newTitle}
              onChange={(e) => setNewTitle(e.target.value)}
              placeholder="e.g. Implement refresh token rotation & write integration tests"
            />
          </div>

          <div className="grid grid-cols-2 gap-3">
            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Category</label>
              <select
                value={newCategory}
                onChange={(e) => setNewCategory(e.target.value as TaskCategory)}
              >
                <option value="DSA">DSA</option>
                <option value="JAVA">Java</option>
                <option value="SPRING_BOOT">Spring Boot</option>
                <option value="SQL">SQL</option>
                <option value="FLUTTER">Flutter</option>
                <option value="AI_ASSISTED_DEV">AI-Assisted Dev</option>
                <option value="AI_APP_ENGINEERING">AI App Engineering</option>
                <option value="PORTFOLIO_PROJECT">Portfolio Project</option>
                <option value="APPLICATION_SECURITY">Application Security</option>
                <option value="DEVSECOPS">DevSecOps</option>
                <option value="CAREER_PREPARATION">Career Prep</option>
              </select>
            </div>

            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Priority</label>
              <select
                value={newPriority}
                onChange={(e) => setNewPriority(e.target.value as Priority)}
              >
                <option value="LOW">Low</option>
                <option value="MEDIUM">Medium</option>
                <option value="HIGH">High</option>
                <option value="URGENT">Urgent</option>
              </select>
            </div>
          </div>

          <div>
            <label className="text-xs font-semibold text-muted block mb-1">Estimated Minutes</label>
            <input
              type="number"
              min={10}
              max={600}
              step={10}
              value={newEstimate}
              onChange={(e) => setNewEstimate(parseInt(e.target.value, 10))}
            />
          </div>

          <div className="flex justify-end gap-2 pt-2">
            <button
              type="button"
              className="btn btn-secondary btn-sm"
              onClick={() => setIsTaskModalOpen(false)}
            >
              Cancel
            </button>
            <button type="submit" className="btn btn-primary btn-sm">
              Create Task
            </button>
          </div>
        </form>
      </Modal>

      {/* Carry Forward Modal */}
      <Modal
        isOpen={carryTaskId !== null}
        onClose={() => setCarryTaskId(null)}
        title="Carry Forward Task"
      >
        <div className="space-y-4">
          <p className="text-sm text-muted">
            Move this unfinished task to a future week sprint while preserving its original history.
          </p>

          <div>
            <label className="text-xs font-semibold text-muted block mb-1">Target Week Number (1–52)</label>
            <input
              type="number"
              min={1}
              max={52}
              value={targetWeekNum}
              onChange={(e) => setTargetWeekNum(parseInt(e.target.value, 10))}
            />
          </div>

          <div className="flex justify-end gap-2 pt-2">
            <button
              type="button"
              className="btn btn-secondary btn-sm"
              onClick={() => setCarryTaskId(null)}
            >
              Cancel
            </button>
            <button
              type="button"
              className="btn btn-primary btn-sm"
              onClick={() => {
                // Find target week ID from weeks query
                const weeksList = queryClient.getQueryData<any[]>(['weeks']);
                const targetWeek = weeksList?.find((w) => w.weekNumber === targetWeekNum);
                if (targetWeek && carryTaskId) {
                  carryForwardMutation.mutate({ taskId: carryTaskId, targetWeekId: targetWeek.id });
                } else {
                  showToast(`Could not find week ${targetWeekNum}`, 'error');
                }
              }}
            >
              Confirm Carry Forward
            </button>
          </div>
        </div>
      </Modal>
    </div>
  );
};
