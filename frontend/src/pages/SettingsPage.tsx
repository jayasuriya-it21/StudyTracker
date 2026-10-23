import React, { useState } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { useTheme } from '../contexts/ThemeContext';
import { useToast } from '../components/Toast';
import { api } from '../services/api';
import {
  Settings,
  Download,
  Upload,
  User,
  Moon,
  Sun,
  Shield,
  FileSpreadsheet,
  CheckCircle2,
} from 'lucide-react';

export const SettingsPage: React.FC = () => {
  const { user } = useAuth();
  const { theme, toggleTheme } = useTheme();
  const { showToast } = useToast();

  const [name, setName] = useState(user?.name || 'Jayasuriya');
  const [weeklyTargetHours, setWeeklyTargetHours] = useState(user?.weeklyTargetHours || 14);
  const [isUpdating, setIsUpdating] = useState(false);
  const [isExporting, setIsExporting] = useState(false);
  const [isImporting, setIsImporting] = useState(false);

  const handleUpdateProfile = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsUpdating(true);
    try {
      await api.updateProfile({ name, weeklyTargetHours });
      showToast('Profile updated!', 'success');
    } catch (err: any) {
      showToast(err.message, 'error');
    } finally {
      setIsUpdating(false);
    }
  };

  const handleExportJson = async () => {
    setIsExporting(true);
    try {
      const data = await api.exportJson();
      const blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
      const url = URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `studytracker-backup-${new Date().toISOString().split('T')[0]}.json`;
      a.click();
      URL.revokeObjectURL(url);
      showToast('JSON Backup downloaded!', 'success');
    } catch (err: any) {
      showToast(err.message, 'error');
    } finally {
      setIsExporting(false);
    }
  };

  const handleImportJson = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    setIsImporting(true);
    try {
      const text = await file.text();
      const parsed = JSON.parse(text);
      await api.importJson(parsed);
      showToast('Data imported successfully! Refreshing...', 'success');
      setTimeout(() => window.location.reload(), 1000);
    } catch (err: any) {
      showToast('Invalid JSON file: ' + err.message, 'error');
    } finally {
      setIsImporting(false);
    }
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold tracking-tight">Account & Platform Settings</h1>
        <p className="text-sm text-muted">
          Manage your study parameters, themes, and complete data portability.
        </p>
      </div>

      <div className="two-col">
        {/* Profile & Target Parameters */}
        <div className="card p-5 space-y-4">
          <h3 className="font-semibold text-base flex items-center gap-2">
            <User size={18} className="text-primary" />
            <span>Profile & Study Parameters</span>
          </h3>

          <form onSubmit={handleUpdateProfile} className="space-y-3">
            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Full Name</label>
              <input type="text" value={name} onChange={(e) => setName(e.target.value)} />
            </div>

            <div className="grid grid-cols-2 gap-3">
              <div>
                <label className="text-xs font-semibold text-muted block mb-1">Username</label>
                <input type="text" value={user?.username || ''} disabled className="opacity-60" />
              </div>
              <div>
                <label className="text-xs font-semibold text-muted block mb-1">Weekly Target Hours</label>
                <input
                  type="number"
                  min={5}
                  max={40}
                  value={weeklyTargetHours}
                  onChange={(e) => setWeeklyTargetHours(parseInt(e.target.value, 10))}
                />
              </div>
            </div>

            <div>
              <label className="text-xs font-semibold text-muted block mb-1">Roadmap Start Date</label>
              <input
                type="text"
                value={user?.roadmapStartDate || '2026-11-02 (Monday)'}
                disabled
                className="opacity-60 font-mono text-xs"
              />
            </div>

            <button type="submit" className="btn btn-primary" disabled={isUpdating}>
              {isUpdating ? 'Saving...' : 'Update Settings'}
            </button>
          </form>
        </div>

        {/* Appearance & Preferences */}
        <div className="space-y-4">
          <div className="card p-5 space-y-4">
            <h3 className="font-semibold text-base flex items-center gap-2">
              <Sun size={18} className="text-amber-500" />
              <span>Theme Appearance</span>
            </h3>
            <p className="text-xs text-muted">
              Choose between Dark mode (optimal for nighttime coding) and Light mode.
            </p>
            <button className="btn btn-secondary w-full justify-between" onClick={toggleTheme}>
              <span>Current Theme: <strong>{theme === 'dark' ? 'Dark Mode' : 'Light Mode'}</strong></span>
              {theme === 'dark' ? <Moon size={16} /> : <Sun size={16} />}
            </button>
          </div>

          {/* Data Portability Card */}
          <div className="card p-5 space-y-4">
            <h3 className="font-semibold text-base flex items-center gap-2">
              <Download size={18} className="text-emerald-500" />
              <span>Data Portability & Backup</span>
            </h3>
            <p className="text-xs text-muted">
              You own your data. Export your entire plan, tasks, study logs, and DSA history at any time.
            </p>

            <div className="space-y-2">
              <button
                className="btn btn-secondary w-full text-xs justify-start gap-2"
                onClick={handleExportJson}
                disabled={isExporting}
              >
                <Download size={14} className="text-primary" />
                <span>Download Complete JSON Backup</span>
              </button>

              <a
                href={api.exportSessionsCsvUrl}
                download
                className="btn btn-secondary w-full text-xs justify-start gap-2"
              >
                <FileSpreadsheet size={14} className="text-emerald-500" />
                <span>Export Study Sessions CSV</span>
              </a>

              <a
                href={api.exportDsaCsvUrl}
                download
                className="btn btn-secondary w-full text-xs justify-start gap-2"
              >
                <FileSpreadsheet size={14} className="text-amber-500" />
                <span>Export DSA Problems CSV</span>
              </a>

              <label className="btn btn-secondary w-full text-xs justify-start gap-2 cursor-pointer">
                <Upload size={14} className="text-purple-500" />
                <span>{isImporting ? 'Importing JSON...' : 'Restore / Import JSON Backup'}</span>
                <input
                  type="file"
                  accept=".json"
                  className="hidden"
                  onChange={handleImportJson}
                  disabled={isImporting}
                />
              </label>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
