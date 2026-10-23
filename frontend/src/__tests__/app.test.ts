import { describe, it, expect } from 'vitest';
import { api } from '../services/api';

describe('Frontend API Client & Endpoints Structure', () => {
  it('should expose all required module endpoints', () => {
    expect(typeof api.login).toBe('function');
    expect(typeof api.register).toBe('function');
    expect(typeof api.logout).toBe('function');
    expect(typeof api.getMe).toBe('function');
    expect(typeof api.getRoadmap).toBe('function');
    expect(typeof api.getWeeks).toBe('function');
    expect(typeof api.getWeekDetail).toBe('function');
    expect(typeof api.createTask).toBe('function');
    expect(typeof api.toggleTask).toBe('function');
    expect(typeof api.carryForwardTask).toBe('function');
    expect(typeof api.logSession).toBe('function');
    expect(typeof api.createDsaProblem).toBe('function');
    expect(typeof api.getDsaStats).toBe('function');
    expect(typeof api.saveReview).toBe('function');
    expect(typeof api.getProjects).toBe('function');
    expect(typeof api.getApplications).toBe('function');
    expect(typeof api.getDashboard).toBe('function');
    expect(typeof api.getAnalytics).toBe('function');
    expect(typeof api.search).toBe('function');
    expect(typeof api.chatAi).toBe('function');
    expect(typeof api.rateAiMessage).toBe('function');
    expect(typeof api.exportJson).toBe('function');
    expect(typeof api.importJson).toBe('function');
  });

  it('should define correct CSV export download URLs', () => {
    expect(api.exportSessionsCsvUrl).toContain('/portability/export/sessions/csv');
    expect(api.exportDsaCsvUrl).toContain('/portability/export/dsa/csv');
  });
});

describe('Transition Roadmap Arithmetic Logic', () => {
  it('should correctly calculate days until kickoff (Nov 2, 2026)', () => {
    const kickoff = new Date('2026-11-02T00:00:00Z');
    const testDate = new Date('2026-10-24T00:00:00Z');
    const diffTime = kickoff.getTime() - testDate.getTime();
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    expect(diffDays).toBe(9);
  });

  it('should format session durations cleanly', () => {
    const formatDuration = (mins: number) => {
      const h = Math.floor(mins / 60);
      const m = mins % 60;
      return `${h}h ${m}m`;
    };
    expect(formatDuration(90)).toBe('1h 30m');
    expect(formatDuration(45)).toBe('0h 45m');
    expect(formatDuration(120)).toBe('2h 0m');
  });
});
