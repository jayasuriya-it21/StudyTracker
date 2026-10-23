import React, { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api';
import { Calendar, CheckCircle2, Clock, Filter, ArrowRight, BookOpen, Layers } from 'lucide-react';

export const RoadmapPage: React.FC = () => {
  const navigate = useNavigate();
  const [selectedPhase, setSelectedPhase] = useState<string>('ALL');
  const [searchQuery, setSearchQuery] = useState<string>('');

  const { data: roadmap, isLoading: loadingRoadmap } = useQuery({
    queryKey: ['roadmap'],
    queryFn: api.getRoadmap,
  });

  const { data: weeks, isLoading: loadingWeeks } = useQuery({
    queryKey: ['weeks'],
    queryFn: api.getWeeks,
  });

  if (loadingRoadmap || loadingWeeks) {
    return (
      <div className="space-y-4">
        <div className="h-8 bg-card rounded w-1/4 animate-pulse"></div>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {[...Array(6)].map((_, i) => (
            <div key={i} className="h-44 bg-card rounded-xl animate-pulse"></div>
          ))}
        </div>
      </div>
    );
  }

  const filteredWeeks = (weeks || []).filter((w) => {
    const matchesPhase = selectedPhase === 'ALL' || w.phaseCode === selectedPhase;
    const matchesQuery =
      searchQuery === '' ||
      w.engineeringFocus.toLowerCase().includes(searchQuery.toLowerCase()) ||
      w.aiReadinessDeliverable.toLowerCase().includes(searchQuery.toLowerCase()) ||
      `week ${w.weekNumber}`.toLowerCase().includes(searchQuery.toLowerCase());
    return matchesPhase && matchesQuery;
  });

  const totalTasks = (weeks || []).reduce((acc, w) => acc + w.totalTasks, 0);
  const completedTasks = (weeks || []).reduce((acc, w) => acc + w.completedTasks, 0);
  const overallPct = totalTasks > 0 ? Math.round((completedTasks / totalTasks) * 100) : 0;

  return (
    <div className="space-y-6">
      {/* Page Header */}
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <div>
          <h1 className="text-2xl font-bold tracking-tight">52-Week Transition Curriculum</h1>
          <p className="text-sm text-muted">
            Phase A through Phase G: Java/Spring fundamentals to AI-Augmented Product Engineering & DevSecOps
          </p>
        </div>
        <div className="flex items-center gap-3">
          <div className="text-right">
            <span className="text-xs text-muted block">Curriculum Progress</span>
            <span className="text-sm font-bold font-mono text-primary">
              {completedTasks}/{totalTasks} Tasks ({overallPct}%)
            </span>
          </div>
          <div className="w-24">
            <div className="progress-bar">
              <div className="progress-fill green" style={{ width: `${overallPct}%` }}></div>
            </div>
          </div>
        </div>
      </div>

      {/* Phase Pills & Filter */}
      <div className="card p-4 space-y-4">
        <div className="flex flex-wrap items-center gap-2">
          <button
            className={`btn btn-sm ${selectedPhase === 'ALL' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSelectedPhase('ALL')}
          >
            All Phases (52 Weeks)
          </button>
          {roadmap?.phases.map((p) => (
            <button
              key={p.code}
              className={`btn btn-sm ${selectedPhase === p.code ? 'btn-primary' : 'btn-secondary'}`}
              onClick={() => setSelectedPhase(p.code)}
            >
              Phase {p.code}: {p.title}
            </button>
          ))}
        </div>

        <div className="flex items-center gap-3">
          <div className="relative flex-1">
            <input
              type="text"
              placeholder="Search weeks by engineering focus, AI deliverable, or week number..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="pl-3 pr-3 py-1.5 text-sm"
            />
          </div>
          <span className="text-xs text-muted whitespace-nowrap">
            Showing {filteredWeeks.length} of {weeks?.length || 52} weeks
          </span>
        </div>
      </div>

      {/* Weeks Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {filteredWeeks.map((w) => {
          const isDone = w.completionPercentage === 100;
          return (
            <div
              key={w.id}
              className="card p-5 hover:border-primary/50 transition-all cursor-pointer flex flex-col justify-between"
              onClick={() => navigate(`/weeks/${w.weekNumber}`)}
            >
              <div>
                <div className="flex justify-between items-center mb-2">
                  <div className="flex items-center gap-2">
                    <span className="font-bold text-base">Week {w.weekNumber}</span>
                    <span className="category-badge badge-java text-[10px]">Phase {w.phaseCode}</span>
                  </div>
                  <span
                    className={`text-xs font-semibold px-2 py-0.5 rounded-full ${
                      isDone
                        ? 'bg-emerald-500/15 text-emerald-500'
                        : w.completionPercentage > 0
                        ? 'bg-blue-500/15 text-blue-500'
                        : 'bg-muted/15 text-muted'
                    }`}
                  >
                    {w.completionPercentage}% Done
                  </span>
                </div>

                <div className="text-xs text-muted mb-3 flex items-center gap-2">
                  <Calendar size={13} />
                  <span>
                    {w.startDate} → {w.endDate}
                  </span>
                  <span>·</span>
                  <Clock size={13} />
                  <span>{w.plannedHours}h target</span>
                </div>

                <div className="space-y-2 mb-4">
                  <div className="p-2.5 rounded bg-card-hover border border-border/60">
                    <span className="text-[10px] font-semibold text-muted uppercase tracking-wider block">
                      ⚙️ Engineering Focus
                    </span>
                    <p className="text-xs font-medium text-main line-clamp-2">{w.engineeringFocus}</p>
                  </div>

                  <div className="p-2.5 rounded bg-card-hover border border-border/60">
                    <span className="text-[10px] font-semibold text-muted uppercase tracking-wider block">
                      🤖 AI Readiness Deliverable
                    </span>
                    <p className="text-xs font-medium text-main line-clamp-2">{w.aiReadinessDeliverable}</p>
                  </div>
                </div>
              </div>

              <div>
                <div className="flex justify-between items-center text-xs text-muted mb-1.5">
                  <span className="flex items-center gap-1">
                    <CheckCircle2 size={13} className="text-primary" />
                    <span>
                      {w.completedTasks} / {w.totalTasks} deliverables
                    </span>
                  </span>
                  <span className="text-primary font-medium flex items-center gap-0.5">
                    View Week <ArrowRight size={12} />
                  </span>
                </div>
                <div className="progress-bar">
                  <div
                    className={`progress-fill ${isDone ? 'green' : ''}`}
                    style={{ width: `${w.completionPercentage}%` }}
                  ></div>
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};
