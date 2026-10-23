import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { api } from '../services/api';
import { useToast } from '../components/Toast';
import { Modal } from '../components/Modal';
import {
  FolderKanban,
  CheckCircle2,
  ExternalLink,
  GitBranch,
  Plus,
  Shield,
  Layers,
  Sparkles,
} from 'lucide-react';

export const ProjectsPage: React.FC = () => {
  const queryClient = useQueryClient();
  const { showToast } = useToast();

  const [activeProjectId, setActiveProjectId] = useState<number | null>(null);
  const [milestoneTitle, setMilestoneTitle] = useState('');
  const [isMilestoneModalOpen, setIsMilestoneModalOpen] = useState(false);

  const { data: projects, isLoading } = useQuery({
    queryKey: ['projects'],
    queryFn: api.getProjects,
  });

  const toggleMilestoneMutation = useMutation({
    mutationFn: (milestoneId: number) => api.toggleMilestone(milestoneId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['projects'] });
      queryClient.invalidateQueries({ queryKey: ['dashboard'] });
      showToast('Milestone updated!', 'success');
    },
  });

  const createMilestoneMutation = useMutation({
    mutationFn: ({ projectId, data }: { projectId: number; data: any }) =>
      api.createMilestone(projectId, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['projects'] });
      queryClient.invalidateQueries({ queryKey: ['dashboard'] });
      showToast('Milestone added!', 'success');
      setIsMilestoneModalOpen(false);
      setMilestoneTitle('');
    },
  });

  const handleCreateMilestone = (e: React.FormEvent) => {
    e.preventDefault();
    if (!milestoneTitle.trim() || !activeProjectId) return;
    createMilestoneMutation.mutate({
      projectId: activeProjectId,
      data: { title: milestoneTitle.trim() },
    });
  };

  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <div>
          <h1 className="text-2xl font-bold tracking-tight">Portfolio Projects & Artifacts</h1>
          <p className="text-sm text-muted">
            Tangible proof of engineering competence: synthetic banking scenarios, DevSecOps pipelines, and cloud-native security.
          </p>
        </div>
      </div>

      {/* Safety Notice regarding synthetic data */}
      <div className="p-4 rounded-xl border border-blue-500/30 bg-blue-500/10 flex items-center gap-3">
        <Shield className="text-blue-500 shrink-0" size={24} />
        <div className="text-xs text-muted">
          <strong className="text-blue-400 font-semibold block mb-0.5">
            Synthetic & Fictional Engineering Artifacts
          </strong>
          All projects use strictly synthetic, fictional enterprise scenarios (e.g. Acme Commercial Credit) with zero confidential TCS or Commercial Bank of Dubai proprietary code or customer data.
        </div>
      </div>

      {isLoading ? (
        <div className="space-y-4">
          {[...Array(3)].map((_, i) => (
            <div key={i} className="h-48 bg-card rounded-xl animate-pulse"></div>
          ))}
        </div>
      ) : (
        <div className="space-y-6">
          {projects?.map((project) => {
            const isFlagship = project.projectType === 'Flagship';
            const completedCount = project.milestones.filter((m) => m.status === 'COMPLETED').length;
            const totalCount = project.milestones.length;

            return (
              <div
                key={project.id}
                className={`card p-6 space-y-4 ${
                  isFlagship ? 'border-primary/40 bg-card' : ''
                }`}
              >
                <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-3 border-b border-border pb-4">
                  <div>
                    <div className="flex items-center gap-2 mb-1">
                      <span
                        className={`text-[10px] font-bold uppercase tracking-wider px-2.5 py-0.5 rounded-full ${
                          isFlagship
                            ? 'bg-amber-500/15 text-amber-500'
                            : 'bg-blue-500/15 text-blue-500'
                        }`}
                      >
                        {project.projectType}
                      </span>
                      <span className="text-xs font-semibold px-2 py-0.5 rounded-full bg-card-hover border border-border">
                        {project.status.replace('_', ' ')}
                      </span>
                    </div>
                    <h2 className="text-xl font-bold tracking-tight">{project.title}</h2>
                  </div>

                  <div className="flex items-center gap-2">
                    {project.repositoryUrl && (
                      <a
                        href={project.repositoryUrl}
                        target="_blank"
                        rel="noopener noreferrer"
                        className="btn btn-secondary btn-sm"
                      >
                        <GitBranch size={14} />
                        <span>Code</span>
                      </a>
                    )}
                    {project.demoUrl && (
                      <a
                        href={project.demoUrl}
                        target="_blank"
                        rel="noopener noreferrer"
                        className="btn btn-secondary btn-sm"
                      >
                        <ExternalLink size={14} />
                        <span>Live Demo</span>
                      </a>
                    )}
                    <button
                      className="btn btn-primary btn-sm"
                      onClick={() => {
                        setActiveProjectId(project.id);
                        setIsMilestoneModalOpen(true);
                      }}
                    >
                      <Plus size={14} />
                      <span>Add Milestone</span>
                    </button>
                  </div>
                </div>

                <p className="text-sm text-muted">{project.description}</p>

                {/* Tech stack tags */}
                <div className="flex flex-wrap gap-1.5">
                  {(project.techStack || '').split(',').map((tech, i) => (
                    <span
                      key={i}
                      className="px-2 py-0.5 rounded bg-card-hover border border-border/80 font-mono text-[11px] text-muted"
                    >
                      {tech.trim()}
                    </span>
                  ))}
                </div>

                {/* Progress bar */}
                <div>
                  <div className="flex justify-between items-center text-xs mb-1.5">
                    <span className="text-muted">
                      Milestones Completed: {completedCount} of {totalCount}
                    </span>
                    <span className="font-bold font-mono text-primary">
                      {project.progressPercentage}%
                    </span>
                  </div>
                  <div className="progress-bar">
                    <div
                      className={`progress-fill ${isFlagship ? 'green' : ''}`}
                      style={{ width: `${project.progressPercentage}%` }}
                    ></div>
                  </div>
                </div>

                {/* Milestones checklist */}
                <div className="space-y-2 pt-2">
                  <h4 className="text-xs font-semibold text-muted uppercase tracking-wider">
                    Key Implementation Milestones
                  </h4>
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-2">
                    {project.milestones.map((m) => {
                      const done = m.status === 'COMPLETED';
                      return (
                        <div
                          key={m.id}
                          className={`task-item justify-between ${done ? 'completed' : ''}`}
                        >
                          <div className="flex items-center gap-2.5 min-w-0">
                            <input
                              type="checkbox"
                              checked={done}
                              onChange={() => toggleMilestoneMutation.mutate(m.id)}
                              id={`milestone-${m.id}`}
                            />
                            <label
                              htmlFor={`milestone-${m.id}`}
                              className="text-xs cursor-pointer truncate font-medium"
                            >
                              {m.title}
                            </label>
                          </div>
                          {done && (
                            <span className="text-[10px] text-emerald-500 font-semibold shrink-0">
                              Verified
                            </span>
                          )}
                        </div>
                      );
                    })}
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      )}

      {/* Add Milestone Modal */}
      <Modal
        isOpen={isMilestoneModalOpen}
        onClose={() => setIsMilestoneModalOpen(false)}
        title="Add Project Milestone"
      >
        <form onSubmit={handleCreateMilestone} className="space-y-4">
          <div>
            <label className="text-xs font-semibold text-muted block mb-1">
              Milestone Deliverable *
            </label>
            <input
              type="text"
              required
              value={milestoneTitle}
              onChange={(e) => setMilestoneTitle(e.target.value)}
              placeholder="e.g. Integrate SonarQube & OWASP Dependency-Check in GitHub Actions"
            />
          </div>

          <div className="flex justify-end gap-2 pt-2">
            <button
              type="button"
              className="btn btn-secondary btn-sm"
              onClick={() => setIsMilestoneModalOpen(false)}
            >
              Cancel
            </button>
            <button
              type="submit"
              className="btn btn-primary btn-sm"
              disabled={createMilestoneMutation.isPending}
            >
              Add Milestone
            </button>
          </div>
        </form>
      </Modal>
    </div>
  );
};
