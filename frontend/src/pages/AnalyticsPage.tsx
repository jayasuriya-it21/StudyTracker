import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { api } from '../services/api';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  PointElement,
  LineElement,
  ArcElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Bar, Doughnut } from 'react-chartjs-2';
import { BarChart3, TrendingUp, Clock, CheckCircle2 } from 'lucide-react';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  PointElement,
  LineElement,
  ArcElement,
  Title,
  Tooltip,
  Legend
);

export const AnalyticsPage: React.FC = () => {
  const { data: analytics, isLoading } = useQuery({
    queryKey: ['analytics'],
    queryFn: api.getAnalytics,
  });

  if (isLoading || !analytics) {
    return (
      <div className="space-y-4">
        <div className="h-8 bg-card rounded w-1/4 animate-pulse"></div>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {[...Array(4)].map((_, i) => (
            <div key={i} className="h-64 bg-card rounded-xl animate-pulse"></div>
          ))}
        </div>
      </div>
    );
  }

  // 1. Weekly Hours Velocity Chart Data
  const weeklyKeys = Object.keys(analytics.weeklyPlannedVsActualHours || {});
  const weeklyActuals = Object.values(analytics.weeklyPlannedVsActualHours || {});
  const weeklyChartData = {
    labels: weeklyKeys.length > 0 ? weeklyKeys : ['W1', 'W2', 'W3', 'W4'],
    datasets: [
      {
        label: 'Logged Study Hours',
        data: weeklyActuals.length > 0 ? weeklyActuals : [14, 15, 12, 14],
        backgroundColor: '#3b82f6',
        borderRadius: 4,
      },
    ],
  };

  // 2. Category Distribution Doughnut Data
  const categoryLabels = Object.keys(analytics.categoryHoursDistribution || {}).map((k) =>
    k.replace(/_/g, ' ')
  );
  const categoryValues = Object.values(analytics.categoryHoursDistribution || {});

  const categoryChartData = {
    labels: categoryLabels.length > 0 ? categoryLabels : ['DSA', 'Java', 'AI Engineering', 'AppSec'],
    datasets: [
      {
        data: categoryValues.length > 0 ? categoryValues : [40, 25, 20, 15],
        backgroundColor: [
          '#f59e0b',
          '#3b82f6',
          '#8b5cf6',
          '#ef4444',
          '#10b981',
          '#06b6d4',
          '#ec4899',
        ],
        borderWidth: 0,
      },
    ],
  };

  // 3. Phase Completion Rates Data
  const phaseLabels = Object.keys(analytics.phaseCompletionRates || {}).map((k) =>
    k.replace(/_/g, ' ')
  );
  const phasePercentages = Object.values(analytics.phaseCompletionRates || {});

  const phaseChartData = {
    labels: phaseLabels.length > 0 ? phaseLabels : ['Phase A', 'Phase B', 'Phase C', 'Phase D'],
    datasets: [
      {
        label: 'Completion %',
        data: phasePercentages.length > 0 ? phasePercentages : [100, 60, 20, 0],
        backgroundColor: '#8b5cf6',
        borderRadius: 4,
      },
    ],
  };

  // 4. DSA Solved by Topic Data
  const dsaTopicLabels = Object.keys(analytics.dsaStats?.byTopic || {});
  const dsaTopicValues = Object.values(analytics.dsaStats?.byTopic || {});

  const dsaTopicData = {
    labels: dsaTopicLabels.length > 0 ? dsaTopicLabels : ['Arrays', 'Trees', 'Graphs', 'DP'],
    datasets: [
      {
        label: 'Problems Solved',
        data: dsaTopicValues.length > 0 ? dsaTopicValues : [12, 8, 6, 4],
        backgroundColor: '#f59e0b',
        borderRadius: 4,
      },
    ],
  };

  const chartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        labels: {
          color: '#94a3b8',
          font: { size: 11 },
        },
      },
    },
    scales: {
      x: {
        ticks: { color: '#64748b', font: { size: 10 } },
        grid: { color: 'rgba(255, 255, 255, 0.05)' },
      },
      y: {
        ticks: { color: '#64748b', font: { size: 10 } },
        grid: { color: 'rgba(255, 255, 255, 0.05)' },
      },
    },
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold tracking-tight">Performance & Velocity Analytics</h1>
        <p className="text-sm text-muted">
          Measure weekly study velocity, time distribution across engineering pillars, and curriculum progress.
        </p>
      </div>

      {/* Grid of Charts */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Weekly Hours Velocity */}
        <div className="card p-5 space-y-3">
          <div className="flex justify-between items-center">
            <h3 className="font-semibold text-base flex items-center gap-2">
              <TrendingUp size={18} className="text-primary" />
              <span>Weekly Effort Velocity vs Target (14h)</span>
            </h3>
          </div>
          <div className="h-64">
            <Bar data={weeklyChartData} options={chartOptions} />
          </div>
        </div>

        {/* Category Breakdown */}
        <div className="card p-5 space-y-3">
          <div className="flex justify-between items-center">
            <h3 className="font-semibold text-base flex items-center gap-2">
              <Clock size={18} className="text-purple-500" />
              <span>Time Allocation Across Core Pillars</span>
            </h3>
          </div>
          <div className="h-64 flex items-center justify-center">
            <Doughnut
              data={categoryChartData}
              options={{
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                  legend: {
                    position: 'right',
                    labels: { color: '#94a3b8', font: { size: 11 } },
                  },
                },
              }}
            />
          </div>
        </div>

        {/* Phase Completion */}
        <div className="card p-5 space-y-3">
          <div className="flex justify-between items-center">
            <h3 className="font-semibold text-base flex items-center gap-2">
              <CheckCircle2 size={18} className="text-emerald-500" />
              <span>Phase Progression (Phase A through G)</span>
            </h3>
          </div>
          <div className="h-64">
            <Bar data={phaseChartData} options={chartOptions} />
          </div>
        </div>

        {/* DSA by Topic */}
        <div className="card p-5 space-y-3">
          <div className="flex justify-between items-center">
            <h3 className="font-semibold text-base flex items-center gap-2">
              <BarChart3 size={18} className="text-amber-500" />
              <span>DSA Problem Coverage by Pattern</span>
            </h3>
          </div>
          <div className="h-64">
            <Bar data={dsaTopicData} options={chartOptions} />
          </div>
        </div>
      </div>
    </div>
  );
};
