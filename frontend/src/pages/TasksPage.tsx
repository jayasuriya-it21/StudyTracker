import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api';
import { useToast } from '../components/Toast';
import { Modal } from '../components/Modal';
import {
  Plus,
  ArrowRight,
} from 'lucide-react';
import type { TaskCategory, Priority } from '../types';

export const TasksPage: React.FC = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const { showToast } = useToast();

  const [selectedWeek, setSelectedWeek] = useState<number | 'ALL'>('ALL');
  const [selectedCategory, setSelectedCategory] = useState<string>('ALL');
  const [selectedStatus, setSelectedStatus] = useState<string>('ALL');
  const [searchQuery, setSearchQuery] = useState('');

  // Custom task modal
  const [isTaskModalOpen, setIsTaskModalOpen] = useState(false);
  const [weekId, setWeekId] = useState<number>(1);
  const [title, setTitle] = useState('');
  const [category, setCategory] = useState<TaskCategory>('DSA');
  const [priority, setPriority] = useState<Priority>('MEDIUM');
  const [estimateMinutes, setEstimateMinutes] = useState(60);

  const { data: weeks } = useQuery({
    queryKey: ['weeks'],
    queryFn: api.getWeeks,
  });

  // Pull all tasks from the current week detail or dashboard
  // We can also fetch the selected week or all weeks
  const { data: detail } = useQuery({
    queryKey: ['weekDetail', selectedWeek === 'ALL' ? 1 : selectedWeek],
    queryFn: () => api.getWeekDetail(selectedWeek === 'ALL' ? 1 : selectedWeek),
    enabled: selectedWeek !== 'ALL',
  });

  const { data: dashboard } = useQuery({
    queryKey: ['dashboard'],
    queryFn: api.getDashboard,
  });

  const toggleMutation = useMutation({
    mutationFn: (id: number) => api.toggleTask(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['weeks'] });
      queryClient.invalidateQueries({ queryKey: ['dashboard'] });
      if (selectedWeek !== 'ALL') {
        queryClient.invalidateQueries({ queryKey: ['weekDetail', selectedWeek] });
      }
      showToast('Task updated!', 'success');
    },
  });

  const createTaskMutation = useMutation({
    mutationFn: (data: any) => api.createTask(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['weeks'] });
      queryClient.invalidateQueries({ queryKey: ['dashboard'] });
      showToast('Task created!', 'success');
      setIsTaskModalOpen(false);
      setTitle('');
    },
  });

  const handleCreateTask = (e: React.FormEvent) => {
    e.preventDefault();
    if (!title.trim()) return;
    createTaskMutation.mutate({
      weekId,
      title: title.trim(),
      category,
      priority,
      estimateMinutes,
    });
  };

  // If a specific week is chosen, show that week's tasks. Otherwise show current week tasks from dashboard
  const displayTasks =
    selectedWeek !== 'ALL'
      ? detail?.tasks || []
      : dashboard?.currentWeekTasks || [];

  const filteredTasks = displayTasks.filter((t) => {
    const matchesCat = selectedCategory === 'ALL' || t.category === selectedCategory;
    const matchesStat = selectedStatus === 'ALL' || t.status === selectedStatus;
    const matchesQuery =
      searchQuery === '' || t.title.toLowerCase().includes(searchQuery.toLowerCase());
    return matchesCat && matchesStat && matchesQuery;
  });

  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <div>
          <h1 className="text-2xl font-bold tracking-tight">Tasks & Sprints Checklist</h1>
          <p className="text-sm text-muted">
            Track, filter, and complete deliverables week by week.
          </p>
        </div>
        <button className="btn btn-primary btn-sm" onClick={() => setIsTaskModalOpen(true)}>
          <Plus size={14} />
          <span>Add Custom Task</span>
        </button>
      </div>

      {/* Filter Card */}
      <div className="card p-4 space-y-3">
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-3">
          <div>
            <label className="text-[11px] font-semibold text-muted block mb-1">Select Week Sprint</label>
            <select
              value={selectedWeek}
              onChange={(e) =>
                setSelectedWeek(e.target.value === 'ALL' ? 'ALL' : parseInt(e.target.value, 10))
              }
              className="py-1.5 text-xs"
            >
              <option value="ALL">Current Active Week (Week {dashboard?.currentWeek || 1})</option>
              {weeks?.map((w) => (
                <option key={w.id} value={w.weekNumber}>
                  Week {w.weekNumber} ({w.phaseCode}: {w.completionPercentage}%)
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="text-[11px] font-semibold text-muted block mb-1">Category</label>
            <select
              value={selectedCategory}
              onChange={(e) => setSelectedCategory(e.target.value)}
              className="py-1.5 text-xs"
            >
              <option value="ALL">All Categories</option>
              <option value="DSA">DSA</option>
              <option value="JAVA">Java</option>
              <option value="SPRING_BOOT">Spring Boot</option>
              <option value="SQL">SQL</option>
              <option value="FLUTTER">Flutter</option>
              <option value="AI_ASSISTED_DEV">AI-Assisted Dev</option>
              <option value="AI_APP_ENGINEERING">AI App Engineering</option>
              <option value="PORTFOLIO_PROJECT">Portfolio Project</option>
              <option value="APPLICATION_SECURITY">AppSec / DevSecOps</option>
            </select>
          </div>

          <div>
            <label className="text-[11px] font-semibold text-muted block mb-1">Status</label>
            <select
              value={selectedStatus}
              onChange={(e) => setSelectedStatus(e.target.value)}
              className="py-1.5 text-xs"
            >
              <option value="ALL">All Statuses</option>
              <option value="NOT_STARTED">Not Started</option>
              <option value="IN_PROGRESS">In Progress</option>
              <option value="COMPLETED">Completed</option>
              <option value="CARRIED_FORWARD">Carried Forward</option>
            </select>
          </div>

          <div>
            <label className="text-[11px] font-semibold text-muted block mb-1">Search Keywords</label>
            <input
              type="text"
              placeholder="Search deliverables..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="py-1.5 text-xs"
            />
          </div>
        </div>
      </div>

      {/* Task List */}
      <div className="card p-5 space-y-4">
        <div className="flex justify-between items-center">
          <h3 className="font-semibold text-sm">
            {selectedWeek === 'ALL'
              ? `Week ${dashboard?.currentWeek || 1} Deliverables`
              : `Week ${selectedWeek} Deliverables`}{' '}
            ({filteredTasks.length})
          </h3>
          {selectedWeek !== 'ALL' && (
            <button
              className="btn btn-secondary btn-sm text-xs"
              onClick={() => navigate(`/weeks/${selectedWeek}`)}
            >
              <span>Open Week {selectedWeek} Journal</span>
              <ArrowRight size={12} />
            </button>
          )}
        </div>

        {filteredTasks.length === 0 ? (
          <p className="text-sm text-muted text-center py-8">No tasks found matching your filter criteria.</p>
        ) : (
          <div className="task-list">
            {filteredTasks.map((task) => {
              const isCompleted = task.status === 'COMPLETED';
              return (
                <div
                  key={task.id}
                  className={`task-item justify-between ${isCompleted ? 'completed' : ''}`}
                >
                  <div className="flex items-center gap-3 min-w-0 flex-1">
                    <input
                      type="checkbox"
                      checked={isCompleted}
                      onChange={() => toggleMutation.mutate(task.id)}
                      id={`page-task-${task.id}`}
                    />
                    <div className="truncate">
                      <label
                        htmlFor={`page-task-${task.id}`}
                        className="task-text cursor-pointer block truncate font-medium text-sm"
                      >
                        {task.title}
                      </label>
                      <span className="text-[11px] text-muted">
                        ~{task.estimateMinutes}m · Priority: {task.priority} · Source: {task.source}
                      </span>
                    </div>
                  </div>

                  <span className="category-badge badge-dsa text-[10px] shrink-0">
                    {task.category.replace('_', ' ')}
                  </span>
                </div>
              );
            })}
          </div>
        )}
      </div>

      {/* Add Task Modal */}
      <Modal isOpen={isTaskModalOpen} onClose={() => setIsTaskModalOpen(false)} title="Add Task">
        <form onSubmit={handleCreateTask} className="space-y-4">
          <div>
            <label className="text-xs font-semibold text-muted block mb-1">Task Title *</label>
            <input
              type="text"
              required
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="e.g. Solve 3 LeetCode Mediums on Two Pointers"
            />
          </div>

          <div className="grid grid-cols-2 gap-3">
            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Target Week Sprint</label>
              <select
                value={weekId}
                onChange={(e) => setWeekId(parseInt(e.target.value, 10))}
              >
                {weeks?.map((w) => (
                  <option key={w.id} value={w.id}>
                    Week {w.weekNumber} (Phase {w.phaseCode})
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Category</label>
              <select
                value={category}
                onChange={(e) => setCategory(e.target.value as TaskCategory)}
              >
                <option value="DSA">DSA</option>
                <option value="JAVA">Java</option>
                <option value="SPRING_BOOT">Spring Boot</option>
                <option value="SQL">SQL</option>
                <option value="FLUTTER">Flutter</option>
                <option value="AI_ASSISTED_DEV">AI-Assisted Dev</option>
                <option value="AI_APP_ENGINEERING">AI App Engineering</option>
                <option value="PORTFOLIO_PROJECT">Portfolio Project</option>
                <option value="APPLICATION_SECURITY">AppSec / DevSecOps</option>
              </select>
            </div>
          </div>

          <div className="grid grid-cols-2 gap-3">
            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Priority</label>
              <select
                value={priority}
                onChange={(e) => setPriority(e.target.value as Priority)}
              >
                <option value="LOW">Low</option>
                <option value="MEDIUM">Medium</option>
                <option value="HIGH">High</option>
                <option value="URGENT">Urgent</option>
              </select>
            </div>

            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Est. Minutes</label>
              <input
                type="number"
                min={15}
                max={600}
                step={15}
                value={estimateMinutes}
                onChange={(e) => setEstimateMinutes(parseInt(e.target.value, 10))}
              />
            </div>
          </div>

          <div className="flex justify-end gap-2 pt-2">
            <button
              type="button"
              className="btn btn-secondary btn-sm"
              onClick={() => setIsTaskModalOpen(false)}
            >
              Cancel
            </button>
            <button
              type="submit"
              className="btn btn-primary btn-sm"
              disabled={createTaskMutation.isPending}
            >
              Save Task
            </button>
          </div>
        </form>
      </Modal>
    </div>
  );
};
