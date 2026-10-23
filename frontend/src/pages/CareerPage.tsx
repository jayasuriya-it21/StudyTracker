import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { api } from '../services/api';
import { useToast } from '../components/Toast';
import { Modal } from '../components/Modal';
import {
  Briefcase,
  Plus,
  Target,
  TrendingUp,
  Building,
  CheckCircle2,
  Calendar,
  Award,
  AlertCircle,
} from 'lucide-react';
import { ApplicationStatus } from '../types';

export const CareerPage: React.FC = () => {
  const queryClient = useQueryClient();
  const { showToast } = useToast();

  const [activeTab, setActiveTab] = useState<'APPLICATIONS' | 'INTERVIEWS'>('APPLICATIONS');

  // Application Modal state
  const [isAppModalOpen, setIsAppModalOpen] = useState(false);
  const [company, setCompany] = useState('');
  const [roleTitle, setRoleTitle] = useState('Software Engineer (Product)');
  const [targetCompensation, setTargetCompensation] = useState('₹32,000,000');
  const [appStatus, setAppStatus] = useState<ApplicationStatus>('WISHLIST');
  const [jobUrl, setJobUrl] = useState('');
  const [appNotes, setAppNotes] = useState('');

  // Mock Interview Modal state
  const [isInterviewModalOpen, setIsInterviewModalOpen] = useState(false);
  const [interviewType, setInterviewType] = useState('DSA & Problem Solving');
  const [interviewer, setInterviewer] = useState('Pramp / Peer');
  const [rating, setRating] = useState(8);
  const [feedback, setFeedback] = useState('');
  const [interviewDate, setInterviewDate] = useState(new Date().toISOString().split('T')[0]);

  const { data: applications, isLoading: loadingApps } = useQuery({
    queryKey: ['applications'],
    queryFn: api.getApplications,
  });

  const { data: interviews, isLoading: loadingInterviews } = useQuery({
    queryKey: ['interviews'],
    queryFn: api.getMockInterviews,
  });

  const createAppMutation = useMutation({
    mutationFn: (data: any) => api.createApplication(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['applications'] });
      showToast('Application logged!', 'success');
      setIsAppModalOpen(false);
      resetAppForm();
    },
  });

  const updateAppMutation = useMutation({
    mutationFn: ({ id, data }: { id: number; data: any }) => api.updateApplication(id, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['applications'] });
      showToast('Status updated!', 'success');
    },
  });

  const createInterviewMutation = useMutation({
    mutationFn: (data: any) => api.createMockInterview(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['interviews'] });
      showToast('Mock interview saved!', 'success');
      setIsInterviewModalOpen(false);
      resetInterviewForm();
    },
  });

  const resetAppForm = () => {
    setCompany('');
    setRoleTitle('Software Engineer (Product)');
    setJobUrl('');
    setAppNotes('');
  };

  const resetInterviewForm = () => {
    setFeedback('');
    setRating(8);
  };

  const handleCreateApp = (e: React.FormEvent) => {
    e.preventDefault();
    if (!company.trim()) return;
    createAppMutation.mutate({
      company: company.trim(),
      role: roleTitle.trim(),
      status: appStatus,
      applicationDate: new Date().toISOString().split('T')[0],
      fixedCompensation: targetCompensation.trim() || undefined,
      jobUrl: jobUrl.trim() || undefined,
      notes: appNotes.trim() || undefined,
    });
  };

  const handleCreateInterview = (e: React.FormEvent) => {
    e.preventDefault();
    createInterviewMutation.mutate({
      interviewType,
      durationMinutes: 45,
      score: rating,
      feedback: feedback.trim() || undefined,
      interviewDate,
    });
  };

  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <div>
          <h1 className="text-2xl font-bold tracking-tight">Career Pipeline & Interview Readiness</h1>
          <p className="text-sm text-muted">
            Track product company applications, mock interview feedback, and compensation progress.
          </p>
        </div>
        <div className="flex items-center gap-2">
          <button className="btn btn-secondary btn-sm" onClick={() => setIsInterviewModalOpen(true)}>
            <Award size={14} />
            <span>Log Mock Interview</span>
          </button>
          <button className="btn btn-primary btn-sm" onClick={() => setIsAppModalOpen(true)}>
            <Plus size={14} />
            <span>Add Application</span>
          </button>
        </div>
      </div>

      {/* Target Compensation Banner */}
      <div className="card p-6 border-primary/30 space-y-3">
        <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-2">
          <div className="flex items-center gap-3">
            <Target className="text-primary" size={28} />
            <div>
              <h2 className="text-xl font-bold">Compensation Target: ₹30–40 LPA Ambition</h2>
              <p className="text-xs text-muted">
                Targeting Tier 1/2 Product Companies, Fintech Unicorns, and DevSecOps Platforms in Late 2027
              </p>
            </div>
          </div>
          <span className="category-badge badge-career font-bold text-xs px-3 py-1">
            Goal, Not Guaranteed
          </span>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 pt-2 text-xs">
          <div className="p-3 rounded-lg bg-card-hover border border-border">
            <span className="text-muted block mb-0.5">Fixed Base Expectation</span>
            <strong className="text-sm font-mono text-primary">₹24–30 LPA</strong>
          </div>
          <div className="p-3 rounded-lg bg-card-hover border border-border">
            <span className="text-muted block mb-0.5">Performance Bonus</span>
            <strong className="text-sm font-mono text-amber-500">₹3–5 LPA</strong>
          </div>
          <div className="p-3 rounded-lg bg-card-hover border border-border">
            <span className="text-muted block mb-0.5">Stock Options / ESOPs</span>
            <strong className="text-sm font-mono text-emerald-500">₹5–10 LPA (Vested)</strong>
          </div>
        </div>
      </div>

      {/* Tabs */}
      <div className="card p-4 space-y-4">
        <div className="flex items-center gap-2 border-b border-border pb-3">
          <button
            className={`btn btn-sm ${activeTab === 'APPLICATIONS' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setActiveTab('APPLICATIONS')}
          >
            Applications Pipeline ({applications?.length || 0})
          </button>
          <button
            className={`btn btn-sm ${activeTab === 'INTERVIEWS' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setActiveTab('INTERVIEWS')}
          >
            Mock Interviews & Evaluations ({interviews?.length || 0})
          </button>
        </div>

        {activeTab === 'APPLICATIONS' ? (
          <div>
            {loadingApps ? (
              <div className="h-40 bg-card-hover rounded animate-pulse"></div>
            ) : !applications || applications.length === 0 ? (
              <div className="text-center py-8 space-y-2">
                <Briefcase size={36} className="mx-auto text-muted" />
                <p className="text-sm text-muted">No applications logged yet.</p>
                <button className="btn btn-primary btn-sm" onClick={() => setIsAppModalOpen(true)}>
                  Add First Company
                </button>
              </div>
            ) : (
              <div className="table-container">
                <table>
                  <thead>
                    <tr>
                      <th>Company</th>
                      <th>Role</th>
                      <th>Status</th>
                      <th>Target Comp</th>
                      <th>Applied Date</th>
                      <th>Update Stage</th>
                    </tr>
                  </thead>
                  <tbody>
                    {applications.map((app) => (
                      <tr key={app.id}>
                        <td>
                          <span className="font-semibold text-sm block">{app.company}</span>
                          {app.notes && <span className="text-xs text-muted">{app.notes}</span>}
                        </td>
                        <td className="text-xs">{app.role}</td>
                        <td>
                          <span
                            className={`text-xs font-semibold px-2 py-0.5 rounded-full ${
                              app.status === 'OFFER'
                                ? 'bg-emerald-500/15 text-emerald-500'
                                : app.status === 'REJECTED'
                                ? 'bg-red-500/15 text-red-500'
                                : 'bg-blue-500/15 text-blue-500'
                            }`}
                          >
                            {app.status.replace('_', ' ')}
                          </span>
                        </td>
                        <td className="font-mono text-xs">{app.fixedCompensation || '—'}</td>
                        <td className="font-mono text-xs">{app.applicationDate || 'Wishlist'}</td>
                        <td>
                          <select
                            value={app.status}
                            onChange={(e) =>
                              updateAppMutation.mutate({
                                id: app.id,
                                data: { ...app, status: e.target.value as ApplicationStatus },
                              })
                            }
                            className="py-1 px-2 text-xs w-auto"
                          >
                            <option value="WISHLIST">Wishlist</option>
                            <option value="APPLIED">Applied</option>
                            <option value="SCREENING">Screening</option>
                            <option value="TECHNICAL_ROUND">Technical</option>
                            <option value="SYSTEM_DESIGN">System Design</option>
                            <option value="HR_ROUND">HR Round</option>
                            <option value="OFFER">Offer 🎉</option>
                            <option value="REJECTED">Rejected</option>
                          </select>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        ) : (
          <div>
            {loadingInterviews ? (
              <div className="h-40 bg-card-hover rounded animate-pulse"></div>
            ) : !interviews || interviews.length === 0 ? (
              <div className="text-center py-8 space-y-2">
                <Award size={36} className="mx-auto text-muted" />
                <p className="text-sm text-muted">No mock interviews recorded yet.</p>
                <button
                  className="btn btn-secondary btn-sm"
                  onClick={() => setIsInterviewModalOpen(true)}
                >
                  Record Mock Interview
                </button>
              </div>
            ) : (
              <div className="table-container">
                <table>
                  <thead>
                    <tr>
                      <th>Date</th>
                      <th>Interview Type</th>
                      <th>Platform / Peer</th>
                      <th>Score (1–10)</th>
                      <th>Key Feedback & Gap Analysis</th>
                    </tr>
                  </thead>
                  <tbody>
                    {interviews.map((iv) => (
                      <tr key={iv.id}>
                        <td className="font-mono text-xs">{iv.interviewDate}</td>
                        <td className="font-semibold text-xs">{iv.interviewType}</td>
                        <td className="text-xs text-muted">{iv.durationMinutes} mins</td>
                        <td>
                          <span className="font-bold text-xs text-primary font-mono">
                            {iv.score ? `${iv.score}/10` : '—'}
                          </span>
                        </td>
                        <td className="text-xs text-muted">{iv.feedback || '—'}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        )}
      </div>

      {/* Add Application Modal */}
      <Modal isOpen={isAppModalOpen} onClose={() => setIsAppModalOpen(false)} title="Add Application">
        <form onSubmit={handleCreateApp} className="space-y-4">
          <div>
            <label className="text-xs font-semibold text-muted block mb-1">Company Name *</label>
            <input
              type="text"
              required
              value={company}
              onChange={(e) => setCompany(e.target.value)}
              placeholder="e.g. Razorpay, Swiggy, Atlassian, Microsoft, CRED"
            />
          </div>

          <div className="grid grid-cols-2 gap-3">
            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Role Title</label>
              <input
                type="text"
                value={roleTitle}
                onChange={(e) => setRoleTitle(e.target.value)}
              />
            </div>
            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Pipeline Status</label>
              <select
                value={appStatus}
                onChange={(e) => setAppStatus(e.target.value as ApplicationStatus)}
              >
                <option value="WISHLIST">Wishlist</option>
                <option value="APPLIED">Applied</option>
                <option value="SCREENING">Screening</option>
                <option value="TECHNICAL_ROUND">Technical Round</option>
                <option value="SYSTEM_DESIGN">System Design</option>
                <option value="HR_ROUND">HR Round</option>
                <option value="OFFER">Offer</option>
              </select>
            </div>
          </div>

          <div>
            <label className="text-xs font-semibold text-muted block mb-1">Target Compensation</label>
            <input
              type="text"
              value={targetCompensation}
              onChange={(e) => setTargetCompensation(e.target.value)}
              placeholder="e.g. ₹32–36 LPA"
            />
          </div>

          <div>
            <label className="text-xs font-semibold text-muted block mb-1">Job Post URL (Optional)</label>
            <input
              type="text"
              value={jobUrl}
              onChange={(e) => setJobUrl(e.target.value)}
              placeholder="https://..."
            />
          </div>

          <div>
            <label className="text-xs font-semibold text-muted block mb-1">Notes / Referral</label>
            <textarea
              rows={2}
              value={appNotes}
              onChange={(e) => setAppNotes(e.target.value)}
              placeholder="e.g. Connected via Senior SDE on LinkedIn..."
            />
          </div>

          <div className="flex justify-end gap-2 pt-2">
            <button
              type="button"
              className="btn btn-secondary btn-sm"
              onClick={() => setIsAppModalOpen(false)}
            >
              Cancel
            </button>
            <button
              type="submit"
              className="btn btn-primary btn-sm"
              disabled={createAppMutation.isPending}
            >
              Save Application
            </button>
          </div>
        </form>
      </Modal>

      {/* Add Mock Interview Modal */}
      <Modal
        isOpen={isInterviewModalOpen}
        onClose={() => setIsInterviewModalOpen(false)}
        title="Log Mock Interview Evaluation"
      >
        <form onSubmit={handleCreateInterview} className="space-y-4">
          <div className="grid grid-cols-2 gap-3">
            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Interview Type</label>
              <select
                value={interviewType}
                onChange={(e) => setInterviewType(e.target.value)}
              >
                <option value="DSA & Problem Solving">DSA & Problem Solving</option>
                <option value="Java & Spring Boot Core">Java & Spring Boot Core</option>
                <option value="High-Level System Design">High-Level System Design</option>
                <option value="AppSec & Threat Modeling">AppSec & Threat Modeling</option>
                <option value="Behavioral / Leadership">Behavioral / Leadership</option>
              </select>
            </div>

            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Date</label>
              <input
                type="date"
                value={interviewDate}
                onChange={(e) => setInterviewDate(e.target.value)}
              />
            </div>
          </div>

          <div className="grid grid-cols-2 gap-3">
            <div>
              <label className="text-xs font-semibold text-muted block mb-1">
                Interviewer / Platform
              </label>
              <input
                type="text"
                value={interviewer}
                onChange={(e) => setInterviewer(e.target.value)}
                placeholder="e.g. Pramp, Meetup Peer, AI Mock"
              />
            </div>

            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Score (1 to 10)</label>
              <input
                type="number"
                min={1}
                max={10}
                value={rating}
                onChange={(e) => setRating(parseInt(e.target.value, 10))}
              />
            </div>
          </div>

          <div>
            <label className="text-xs font-semibold text-muted block mb-1">
              Feedback, Mistakes & Areas to Improve
            </label>
            <textarea
              rows={3}
              value={feedback}
              onChange={(e) => setFeedback(e.target.value)}
              placeholder="e.g. Good communication during DFS traversal, but stumbled on time complexity of cyclic graphs..."
            />
          </div>

          <div className="flex justify-end gap-2 pt-2">
            <button
              type="button"
              className="btn btn-secondary btn-sm"
              onClick={() => setIsInterviewModalOpen(false)}
            >
              Cancel
            </button>
            <button
              type="submit"
              className="btn btn-primary btn-sm"
              disabled={createInterviewMutation.isPending}
            >
              Save Evaluation
            </button>
          </div>
        </form>
      </Modal>
    </div>
  );
};
