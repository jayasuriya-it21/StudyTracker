import React, { useState, useEffect } from 'react';
import { Play, Pause, Square, RotateCcw, Clock } from 'lucide-react';
import { TaskCategory } from '../types';
import { api } from '../services/api';
import { useToast } from './Toast';

export const TimerWidget: React.FC<{ onSessionLogged?: () => void }> = ({ onSessionLogged }) => {
  const { showToast } = useToast();
  const [seconds, setSeconds] = useState<number>(() => {
    return parseInt(localStorage.getItem('studytracker_timer_seconds') || '0', 10);
  });
  const [isActive, setIsActive] = useState<boolean>(() => {
    return localStorage.getItem('studytracker_timer_active') === 'true';
  });
  const [category, setCategory] = useState<TaskCategory>('DSA');
  const [summary, setSummary] = useState('');
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    let interval: any = null;
    if (isActive) {
      interval = setInterval(() => {
        setSeconds((s) => {
          const next = s + 1;
          localStorage.setItem('studytracker_timer_seconds', next.toString());
          return next;
        });
      }, 1000);
    } else {
      clearInterval(interval);
    }
    return () => clearInterval(interval);
  }, [isActive]);

  const toggleTimer = () => {
    const next = !isActive;
    setIsActive(next);
    localStorage.setItem('studytracker_timer_active', next.toString());
  };

  const resetTimer = () => {
    setIsActive(false);
    setSeconds(0);
    localStorage.removeItem('studytracker_timer_seconds');
    localStorage.removeItem('studytracker_timer_active');
  };

  const handleStopAndSave = async () => {
    if (seconds < 60) {
      showToast('Session too short (minimum 1 minute to save)', 'info');
      return;
    }

    const durationMinutes = Math.round(seconds / 60);
    setSubmitting(true);
    try {
      await api.logSession({
        category,
        sessionDate: new Date().toISOString().split('T')[0],
        durationMinutes,
        summary: summary || `Focused study session (${category})`,
      });
      showToast(`Recorded ${durationMinutes} minutes of ${category}! 🎉`, 'success');
      resetTimer();
      setSummary('');
      if (onSessionLogged) onSessionLogged();
    } catch (err: any) {
      showToast(err.message || 'Failed to save session', 'error');
    } finally {
      setSubmitting(false);
    }
  };

  const formatTime = (totalSeconds: number) => {
    const hrs = Math.floor(totalSeconds / 3600);
    const mins = Math.floor((totalSeconds % 3600) / 60);
    const secs = totalSeconds % 60;
    return `${hrs.toString().padStart(2, '0')}:${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
  };

  return (
    <div className="card p-4">
      <div className="flex items-center justify-between mb-3">
        <h3 className="flex items-center gap-2 text-sm font-semibold">
          <Clock size={16} className="text-blue-500" />
          <span>Active Study Timer</span>
        </h3>
        <span className="text-xs px-2 py-0.5 rounded-full bg-blue-500/10 text-blue-500 font-mono">
          {isActive ? '⏱️ RUNNING' : 'PAUSED'}
        </span>
      </div>

      <div className="text-center my-3">
        <div className="font-mono text-3xl sm:text-4xl font-bold tracking-tight text-primary">
          {formatTime(seconds)}
        </div>
      </div>

      <div className="grid grid-cols-2 gap-2 mb-3">
        <select
          className="form-control text-xs"
          value={category}
          onChange={(e) => setCategory(e.target.value as TaskCategory)}
          disabled={isActive}
        >
          <option value="DSA">DSA & Coding</option>
          <option value="JAVA">Java & Spring Boot</option>
          <option value="SQL">SQL & Databases</option>
          <option value="FLUTTER">Flutter & Mobile</option>
          <option value="AI_APP_ENGINEERING">AI Engineering & RAG</option>
          <option value="APPLICATION_SECURITY">Application Security</option>
          <option value="DEVSECOPS">DevSecOps & Docker</option>
          <option value="PORTFOLIO_PROJECT">Portfolio Project</option>
          <option value="CAREER_PREPARATION">Career Prep</option>
        </select>

        <input
          type="text"
          className="form-control text-xs"
          placeholder="Session focus notes..."
          value={summary}
          onChange={(e) => setSummary(e.target.value)}
        />
      </div>

      <div className="flex items-center justify-center gap-2">
        <button
          className={`btn btn-sm ${isActive ? 'btn-secondary' : 'btn-primary'}`}
          onClick={toggleTimer}
        >
          {isActive ? <Pause size={14} /> : <Play size={14} />}
          <span>{isActive ? 'Pause' : 'Start'}</span>
        </button>

        <button
          className="btn btn-success btn-sm"
          onClick={handleStopAndSave}
          disabled={seconds < 60 || submitting}
        >
          <Square size={14} />
          <span>Save Session</span>
        </button>

        <button
          className="btn btn-secondary btn-sm"
          onClick={resetTimer}
          disabled={seconds === 0}
          title="Reset timer"
        >
          <RotateCcw size={14} />
        </button>
      </div>
    </div>
  );
};
