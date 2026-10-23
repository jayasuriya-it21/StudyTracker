import React, { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api';
import { BookOpenCheck, Clock, ArrowRight } from 'lucide-react';

export const WeeklyReviewPage: React.FC = () => {
  const navigate = useNavigate();
  const [selectedWeekNum, setSelectedWeekNum] = useState<number>(1);

  const { data: weeks } = useQuery({
    queryKey: ['weeks'],
    queryFn: api.getWeeks,
  });

  const selectedWeek = weeks?.find((w) => w.weekNumber === selectedWeekNum);

  const { data: review, isLoading } = useQuery({
    queryKey: ['review', selectedWeek?.id],
    queryFn: () => (selectedWeek ? api.getReview(selectedWeek.id) : null),
    enabled: !!selectedWeek,
  });

  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <div>
          <h1 className="text-2xl font-bold tracking-tight">Weekly Reflection & Accountability Journal</h1>
          <p className="text-sm text-muted">
            Evaluate engineering progress, verify AI outputs, identify knowledge gaps, and course-correct.
          </p>
        </div>
        <button
          className="btn btn-primary btn-sm"
          onClick={() => navigate(`/weeks/${selectedWeekNum}`)}
        >
          <span>Open Week {selectedWeekNum} Editor</span>
          <ArrowRight size={14} />
        </button>
      </div>

      {/* Week Selector Chips */}
      <div className="card p-4">
        <label className="text-xs font-semibold text-muted block mb-2">Select Week Sprint (1–52):</label>
        <div className="flex gap-2 overflow-x-auto pb-2 scrollbar-thin">
          {weeks?.map((w) => (
            <button
              key={w.id}
              className={`px-3 py-1.5 rounded-lg text-xs font-semibold whitespace-nowrap border transition-all ${
                selectedWeekNum === w.weekNumber
                  ? 'bg-primary text-white border-primary'
                  : 'bg-card-hover border-border text-muted hover:text-main'
              }`}
              onClick={() => setSelectedWeekNum(w.weekNumber)}
            >
              W{w.weekNumber} ({w.completionPercentage}%)
            </button>
          ))}
        </div>
      </div>

      {/* Review Content Display */}
      {isLoading ? (
        <div className="h-64 bg-card rounded-xl animate-pulse"></div>
      ) : !review ? (
        <div className="card p-8 text-center text-muted space-y-3">
          <BookOpenCheck size={40} className="mx-auto text-primary/50" />
          <h3 className="font-semibold text-base">No review recorded yet for Week {selectedWeekNum}</h3>
          <p className="text-xs">
            Reflections help transform daily effort into lasting engineering judgment.
          </p>
          <button
            className="btn btn-primary btn-sm"
            onClick={() => navigate(`/weeks/${selectedWeekNum}`)}
          >
            Start Week {selectedWeekNum} Review
          </button>
        </div>
      ) : (
        <div className="space-y-4">
          <div className="card p-5 space-y-4">
            <div className="flex justify-between items-center border-b border-border pb-3">
              <div className="flex items-center gap-3">
                <span className="font-bold text-lg">Week {review.weekNumber} Reflection</span>
                <span
                  className={`text-xs font-semibold px-2.5 py-0.5 rounded-full ${
                    review.status === 'FINALIZED'
                      ? 'bg-emerald-500/15 text-emerald-500'
                      : 'bg-amber-500/15 text-amber-500'
                  }`}
                >
                  {review.status}
                </span>
              </div>
              <div className="flex items-center gap-3 text-xs text-muted">
                <span className="flex items-center gap-1">
                  <Clock size={14} className="text-primary" />
                  <strong>{review.totalActualHours.toFixed(1)}h</strong> Logged
                </span>
              </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div className="p-4 rounded-xl bg-card-hover border border-border space-y-1">
                <span className="text-xs font-bold text-blue-500 uppercase tracking-wider">
                  1. What was studied & completed
                </span>
                <p className="text-sm whitespace-pre-line text-main">
                  {review.whatStudied || '—'}
                </p>
              </div>

              <div className="p-4 rounded-xl bg-card-hover border border-border space-y-1">
                <span className="text-xs font-bold text-emerald-500 uppercase tracking-wider">
                  2. What did AI get right?
                </span>
                <p className="text-sm whitespace-pre-line text-main">
                  {review.whatAiGotRight || '—'}
                </p>
              </div>

              <div className="p-4 rounded-xl bg-card-hover border border-border space-y-1">
                <span className="text-xs font-bold text-red-500 uppercase tracking-wider">
                  3. What did AI get wrong?
                </span>
                <p className="text-sm whitespace-pre-line text-main">
                  {review.whatAiGotWrong || '—'}
                </p>
              </div>

              <div className="p-4 rounded-xl bg-card-hover border border-border space-y-1">
                <span className="text-xs font-bold text-purple-500 uppercase tracking-wider">
                  4. What was verified independently?
                </span>
                <p className="text-sm whitespace-pre-line text-main">
                  {review.whatVerifiedIndependently || '—'}
                </p>
              </div>
            </div>

            {review.blockers && (
              <div className="p-3 rounded-lg bg-amber-500/10 border border-amber-500/20 text-xs space-y-1">
                <strong className="text-amber-500 uppercase tracking-wider block">
                  Blockers & Next Priorities:
                </strong>
                <p className="whitespace-pre-line">{review.blockers}</p>
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
};
