import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { api } from '../services/api';
import { useToast } from '../components/Toast';
import { Modal } from '../components/Modal';
import {
  Plus,
  ExternalLink,
  RotateCcw,
} from 'lucide-react';
import { Difficulty, DsaStatus } from '../types';

export const DsaPage: React.FC = () => {
  const queryClient = useQueryClient();
  const { showToast } = useToast();

  const [activeTab, setActiveTab] = useState<'ALL' | 'REVISION'>('ALL');
  const [topicFilter, setTopicFilter] = useState('ALL');
  const [difficultyFilter, setDifficultyFilter] = useState('ALL');
  const [statusFilter, setStatusFilter] = useState('ALL');
  const [searchQuery, setSearchQuery] = useState('');

  // Modal State
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [title, setTitle] = useState('');
  const [topic, setTopic] = useState('Arrays & Hashing');
  const [difficulty, setDifficulty] = useState<Difficulty>('MEDIUM');
  const [status, setStatus] = useState<DsaStatus>('SOLVED');
  const [timeComplexity, setTimeComplexity] = useState('O(N)');
  const [spaceComplexity, setSpaceComplexity] = useState('O(1)');
  const [platformUrl, setPlatformUrl] = useState('');
  const [notes, setNotes] = useState('');

  const { data: problems, isLoading } = useQuery({
    queryKey: ['dsaProblems'],
    queryFn: api.getDsaProblems,
  });

  const { data: stats } = useQuery({
    queryKey: ['dsaStats'],
    queryFn: api.getDsaStats,
  });

  const { data: revisionQueue } = useQuery({
    queryKey: ['revisionQueue'],
    queryFn: api.getRevisionQueue,
  });

  const createProblemMutation = useMutation({
    mutationFn: (data: any) => api.createDsaProblem(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['dsaProblems'] });
      queryClient.invalidateQueries({ queryKey: ['dsaStats'] });
      queryClient.invalidateQueries({ queryKey: ['revisionQueue'] });
      queryClient.invalidateQueries({ queryKey: ['dashboard'] });
      showToast('Problem saved!', 'success');
      setIsModalOpen(false);
      resetForm();
    },
    onError: (err: any) => showToast(err.message, 'error'),
  });

  const resetForm = () => {
    setTitle('');
    setPlatformUrl('');
    setNotes('');
    setTimeComplexity('O(N)');
    setSpaceComplexity('O(1)');
  };

  const handleCreate = (e: React.FormEvent) => {
    e.preventDefault();
    if (!title.trim()) return;
    createProblemMutation.mutate({
      title: title.trim(),
      topicPattern: topic,
      difficulty,
      status,
      dateAttempted: new Date().toISOString().split('T')[0],
      complexityExplanation: `${timeComplexity} time, ${spaceComplexity} space`,
      url: platformUrl.trim() || undefined,
      notes: notes.trim() || undefined,
    });
  };

  const displayList = activeTab === 'REVISION' ? revisionQueue || [] : problems || [];

  const filteredProblems = displayList.filter((p) => {
    const matchesTopic = topicFilter === 'ALL' || p.topicPattern === topicFilter;
    const matchesDiff = difficultyFilter === 'ALL' || p.difficulty === difficultyFilter;
    const matchesStatus = statusFilter === 'ALL' || p.status === statusFilter;
    const matchesQuery =
      searchQuery === '' ||
      p.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
      p.topicPattern.toLowerCase().includes(searchQuery.toLowerCase());
    return matchesTopic && matchesDiff && matchesStatus && matchesQuery;
  });

  const solvedCount = stats?.totalSolved || 0;
  const targetMin = stats?.targetMin || 120;
  const targetMax = stats?.targetMax || 160;
  const progressPct = Math.min(100, Math.round((solvedCount / targetMin) * 100));

  const topicsList = [
    'Arrays & Hashing',
    'Two Pointers',
    'Sliding Window',
    'Stack',
    'Binary Search',
    'Linked List',
    'Trees & BST',
    'Tries',
    'Heap / Priority Queue',
    'Backtracking',
    'Graphs (BFS/DFS)',
    'Dynamic Programming',
    'Intervals',
    'Greedy',
    'Bit Manipulation',
  ];

  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <div>
          <h1 className="text-2xl font-bold tracking-tight">DSA & Coding Interview Tracker</h1>
          <p className="text-sm text-muted">
            Target: <strong>{targetMin}–{targetMax}</strong> high-yield problems with deep complexity & pattern retention.
          </p>
        </div>
        <button className="btn btn-primary" onClick={() => setIsModalOpen(true)}>
          <Plus size={16} />
          <span>Log Solved Problem</span>
        </button>
      </div>

      {/* Stats Summary Cards */}
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
        <div className="stat-card accent-orange">
          <div className="stat-value">
            {solvedCount} / {targetMin}
          </div>
          <div className="stat-label">Problems Solved ({progressPct}% of base target)</div>
        </div>
        <div className="stat-card accent-green">
          <div className="stat-value text-emerald-500">{stats?.byDifficulty?.['EASY'] || 0}</div>
          <div className="stat-label">Easy Solved (Pattern foundation)</div>
        </div>
        <div className="stat-card accent-blue">
          <div className="stat-value text-blue-500">{stats?.byDifficulty?.['MEDIUM'] || 0}</div>
          <div className="stat-label">Medium Solved (Interview standard)</div>
        </div>
        <div className="stat-card accent-red">
          <div className="stat-value text-red-500">{stats?.byDifficulty?.['HARD'] || 0}</div>
          <div className="stat-label">Hard Solved (Advanced)</div>
        </div>
      </div>

      {/* Tab Switcher & Filters */}
      <div className="card p-4 space-y-4">
        <div className="flex flex-wrap items-center justify-between gap-3 border-b border-border pb-3">
          <div className="flex items-center gap-2">
            <button
              className={`btn btn-sm ${activeTab === 'ALL' ? 'btn-primary' : 'btn-secondary'}`}
              onClick={() => setActiveTab('ALL')}
            >
              All Problems ({problems?.length || 0})
            </button>
            <button
              className={`btn btn-sm ${activeTab === 'REVISION' ? 'btn-primary' : 'btn-secondary'}`}
              onClick={() => setActiveTab('REVISION')}
            >
              <RotateCcw size={14} />
              <span>Spaced Repetition Queue ({revisionQueue?.length || 0})</span>
            </button>
          </div>
          <span className="text-xs text-muted">
            Showing {filteredProblems.length} problems
          </span>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-3">
          <div className="relative">
            <input
              type="text"
              placeholder="Search problem title..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="py-1.5 text-xs"
            />
          </div>

          <select
            value={topicFilter}
            onChange={(e) => setTopicFilter(e.target.value)}
            className="py-1.5 text-xs"
          >
            <option value="ALL">All Patterns / Topics</option>
            {topicsList.map((t) => (
              <option key={t} value={t}>
                {t}
              </option>
            ))}
          </select>

          <select
            value={difficultyFilter}
            onChange={(e) => setDifficultyFilter(e.target.value)}
            className="py-1.5 text-xs"
          >
            <option value="ALL">All Difficulties</option>
            <option value="EASY">Easy</option>
            <option value="MEDIUM">Medium</option>
            <option value="HARD">Hard</option>
          </select>

          <select
            value={statusFilter}
            onChange={(e) => setStatusFilter(e.target.value)}
            className="py-1.5 text-xs"
          >
            <option value="ALL">All Statuses</option>
            <option value="SOLVED">Solved</option>
            <option value="ATTEMPTED">Attempted</option>
            <option value="REVISIT">Revisit Needed</option>
          </select>
        </div>
      </div>

      {/* Table */}
      <div className="card p-5">
        {isLoading ? (
          <div className="h-40 bg-card-hover rounded animate-pulse"></div>
        ) : filteredProblems.length === 0 ? (
          <p className="text-sm text-muted text-center py-8">
            No DSA problems match your filter. Click "+ Log Solved Problem" to add one!
          </p>
        ) : (
          <div className="table-container">
            <table>
              <thead>
                <tr>
                  <th>Title</th>
                  <th>Topic</th>
                  <th>Difficulty</th>
                  <th>Status</th>
                  <th>Complexity</th>
                  <th>Solves</th>
                  <th>Link</th>
                </tr>
              </thead>
              <tbody>
                {filteredProblems.map((p) => (
                  <tr key={p.id}>
                    <td>
                      <span className="font-semibold text-sm block">{p.title}</span>
                      {p.notes && <span className="text-xs text-muted line-clamp-1">{p.notes}</span>}
                    </td>
                    <td>
                      <span className="text-xs font-medium text-muted">{p.topicPattern}</span>
                    </td>
                    <td>
                      <span
                        className={`text-xs font-semibold px-2 py-0.5 rounded-full ${
                          p.difficulty === 'EASY'
                            ? 'bg-emerald-500/15 text-emerald-500'
                            : p.difficulty === 'MEDIUM'
                            ? 'bg-amber-500/15 text-amber-500'
                            : 'bg-red-500/15 text-red-500'
                        }`}
                      >
                        {p.difficulty}
                      </span>
                    </td>
                    <td>
                      <span
                        className={`text-xs font-semibold px-2 py-0.5 rounded-full ${
                          p.status === 'SOLVED'
                            ? 'bg-blue-500/15 text-blue-500'
                            : p.status === 'REVISIT'
                            ? 'bg-purple-500/15 text-purple-500'
                            : 'bg-muted/15 text-muted'
                        }`}
                      >
                        {p.status}
                      </span>
                    </td>
                    <td className="font-mono text-xs">
                      {p.complexityExplanation || '—'}
                    </td>
                    <td className="font-mono text-xs text-center">{p.attemptCount}x</td>
                    <td>
                      {p.url ? (
                        <a
                          href={p.url}
                          target="_blank"
                          rel="noopener noreferrer"
                          className="text-primary hover:underline flex items-center gap-1 text-xs"
                        >
                          <span>Open</span>
                          <ExternalLink size={12} />
                        </a>
                      ) : (
                        '—'
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      {/* Log Problem Modal */}
      <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} title="Log DSA Problem">
        <form onSubmit={handleCreate} className="space-y-4">
          <div>
            <label className="text-xs font-semibold text-muted block mb-1">Problem Title *</label>
            <input
              type="text"
              required
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="e.g. 3Sum, LRU Cache, Number of Islands"
            />
          </div>

          <div className="grid grid-cols-2 gap-3">
            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Topic / Pattern</label>
              <select value={topic} onChange={(e) => setTopic(e.target.value)}>
                {topicsList.map((t) => (
                  <option key={t} value={t}>
                    {t}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Difficulty</label>
              <select
                value={difficulty}
                onChange={(e) => setDifficulty(e.target.value as Difficulty)}
              >
                <option value="EASY">Easy</option>
                <option value="MEDIUM">Medium</option>
                <option value="HARD">Hard</option>
              </select>
            </div>
          </div>

          <div className="grid grid-cols-3 gap-3">
            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Status</label>
              <select
                value={status}
                onChange={(e) => setStatus(e.target.value as DsaStatus)}
              >
                <option value="SOLVED">Solved</option>
                <option value="ATTEMPTED">Attempted</option>
                <option value="REVISIT">Revisit Needed</option>
              </select>
            </div>

            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Time Complexity</label>
              <input
                type="text"
                value={timeComplexity}
                onChange={(e) => setTimeComplexity(e.target.value)}
                placeholder="e.g. O(N log N)"
              />
            </div>

            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Space Complexity</label>
              <input
                type="text"
                value={spaceComplexity}
                onChange={(e) => setSpaceComplexity(e.target.value)}
                placeholder="e.g. O(1)"
              />
            </div>
          </div>

          <div>
            <label className="text-xs font-semibold text-muted block mb-1">
              LeetCode / Problem URL (Optional)
            </label>
            <input
              type="text"
              value={platformUrl}
              onChange={(e) => setPlatformUrl(e.target.value)}
              placeholder="https://leetcode.com/problems/..."
            />
          </div>

          <div>
            <label className="text-xs font-semibold text-muted block mb-1">
              Key Pattern / Edge Cases / Notes
            </label>
            <textarea
              rows={3}
              value={notes}
              onChange={(e) => setNotes(e.target.value)}
              placeholder="e.g. Sort first, then two pointers. Watch out for duplicate elements..."
            />
          </div>

          <div className="flex justify-end gap-2 pt-2">
            <button
              type="button"
              className="btn btn-secondary btn-sm"
              onClick={() => setIsModalOpen(false)}
            >
              Cancel
            </button>
            <button
              type="submit"
              className="btn btn-primary btn-sm"
              disabled={createProblemMutation.isPending}
            >
              Save Problem
            </button>
          </div>
        </form>
      </Modal>
    </div>
  );
};
