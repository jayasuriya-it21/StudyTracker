import React, { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api';
import { Search, CheckCircle2, Code2, FolderKanban, Briefcase, ArrowRight } from 'lucide-react';

export const SearchPage: React.FC = () => {
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState('');
  const [submittedQuery, setSubmittedQuery] = useState('');

  const { data: results, isLoading } = useQuery({
    queryKey: ['search', submittedQuery],
    queryFn: () => (submittedQuery ? api.search(submittedQuery) : null),
    enabled: !!submittedQuery,
  });

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    if (searchTerm.trim().length >= 2) {
      setSubmittedQuery(searchTerm.trim());
    }
  };

  const totalResults =
    (results?.tasks.length || 0) +
    (results?.dsaProblems.length || 0) +
    (results?.projects.length || 0) +
    (results?.applications.length || 0);

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold tracking-tight">Global Workspace Search</h1>
        <p className="text-sm text-muted">
          Search across 260 curriculum tasks, DSA problems, portfolio milestones, and job applications.
        </p>
      </div>

      {/* Search Input Box */}
      <form onSubmit={handleSearch} className="card p-4 flex gap-3">
        <div className="relative flex-1">
          <input
            type="text"
            placeholder="Type keywords (e.g. 'Kafka', 'JWT', 'Binary Tree', 'OAuth2', 'Docker')..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="py-2.5 px-4 text-sm"
            autoFocus
          />
        </div>
        <button type="submit" className="btn btn-primary">
          <Search size={16} />
          <span>Search</span>
        </button>
      </form>

      {/* Results Section */}
      {isLoading ? (
        <div className="space-y-3">
          {[...Array(3)].map((_, i) => (
            <div key={i} className="h-20 bg-card rounded-xl animate-pulse"></div>
          ))}
        </div>
      ) : submittedQuery && (!results || totalResults === 0) ? (
        <div className="card p-8 text-center text-muted">
          <p className="text-sm">No items found matching "{submittedQuery}". Try another keyword!</p>
        </div>
      ) : results ? (
        <div className="space-y-6">
          <p className="text-xs text-muted">
            Found <strong>{totalResults}</strong> result(s) for "{submittedQuery}"
          </p>

          {/* Tasks Results */}
          {results.tasks.length > 0 && (
            <div className="card p-5 space-y-3">
              <h3 className="font-semibold text-sm flex items-center gap-2 text-primary">
                <CheckCircle2 size={16} />
                <span>Curriculum Deliverables ({results.tasks.length})</span>
              </h3>
              <div className="task-list">
                {results.tasks.map((task) => (
                  <div
                    key={task.id}
                    className="task-item justify-between cursor-pointer hover:border-primary/50"
                    onClick={() => navigate(`/weeks/${task.weekNumber}`)}
                  >
                    <div className="flex items-center gap-2 min-w-0">
                      <span className="font-bold text-xs text-muted">Week {task.weekNumber}:</span>
                      <span className="text-sm font-medium truncate">{task.title}</span>
                    </div>
                    <div className="flex items-center gap-2 shrink-0">
                      <span className="category-badge badge-dsa text-[10px]">
                        {task.category.replace('_', ' ')}
                      </span>
                      <ArrowRight size={14} className="text-muted" />
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )}

          {/* DSA Results */}
          {results.dsaProblems.length > 0 && (
            <div className="card p-5 space-y-3">
              <h3 className="font-semibold text-sm flex items-center gap-2 text-amber-500">
                <Code2 size={16} />
                <span>DSA Problems ({results.dsaProblems.length})</span>
              </h3>
              <div className="task-list">
                {results.dsaProblems.map((p) => (
                  <div
                    key={p.id}
                    className="task-item justify-between cursor-pointer hover:border-primary/50"
                    onClick={() => navigate('/dsa')}
                  >
                    <div>
                      <span className="text-sm font-medium block">{p.title}</span>
                      <span className="text-xs text-muted">{p.topicPattern}</span>
                    </div>
                    <span className="category-badge badge-dsa text-[10px]">{p.difficulty}</span>
                  </div>
                ))}
              </div>
            </div>
          )}

          {/* Projects Results */}
          {results.projects.length > 0 && (
            <div className="card p-5 space-y-3">
              <h3 className="font-semibold text-sm flex items-center gap-2 text-purple-500">
                <FolderKanban size={16} />
                <span>Portfolio Projects ({results.projects.length})</span>
              </h3>
              <div className="task-list">
                {results.projects.map((proj) => (
                  <div
                    key={proj.id}
                    className="task-item justify-between cursor-pointer hover:border-primary/50"
                    onClick={() => navigate('/projects')}
                  >
                    <div>
                      <span className="text-sm font-semibold">{proj.title}</span>
                      <p className="text-xs text-muted">{proj.techStack}</p>
                    </div>
                    <span className="font-mono text-xs font-bold text-primary">
                      {proj.progressPercentage}%
                    </span>
                  </div>
                ))}
              </div>
            </div>
          )}

          {/* Applications Results */}
          {results.applications.length > 0 && (
            <div className="card p-5 space-y-3">
              <h3 className="font-semibold text-sm flex items-center gap-2 text-blue-500">
                <Briefcase size={16} />
                <span>Job Applications ({results.applications.length})</span>
              </h3>
              <div className="task-list">
                {results.applications.map((app) => (
                  <div
                    key={app.id}
                    className="task-item justify-between cursor-pointer hover:border-primary/50"
                    onClick={() => navigate('/career')}
                  >
                    <div>
                      <span className="text-sm font-semibold">{app.company}</span>
                      <span className="text-xs text-muted block">{app.role}</span>
                    </div>
                    <span className="text-xs font-semibold px-2 py-0.5 rounded-full bg-blue-500/10 text-blue-500">
                      {app.status}
                    </span>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      ) : null}
    </div>
  );
};
