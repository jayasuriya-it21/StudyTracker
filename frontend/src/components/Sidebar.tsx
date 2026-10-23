import React from 'react';
import { NavLink } from 'react-router-dom';
import { useTheme } from '../contexts/ThemeContext';
import { useAuth } from '../contexts/AuthContext';
import {
  LayoutDashboard,
  CalendarDays,
  CheckSquare,
  Timer,
  Code2,
  BookOpenCheck,
  FolderKanban,
  Briefcase,
  BarChart3,
  Search,
  Bot,
  Settings,
  Sun,
  Moon,
  LogOut,
  X,
} from 'lucide-react';

interface SidebarProps {
  isOpen: boolean;
  onClose: () => void;
}

export const Sidebar: React.FC<SidebarProps> = ({ isOpen, onClose }) => {
  const { theme, toggleTheme } = useTheme();
  const { logout } = useAuth();

  const navItems = [
    { to: '/', label: 'Dashboard', icon: LayoutDashboard },
    { to: '/roadmap', label: '52-Week Plan', icon: CalendarDays },
    { to: '/tasks', label: 'Tasks & Sprints', icon: CheckSquare },
    { to: '/timer', label: 'Study Timer', icon: Timer },
    { to: '/dsa', label: 'DSA Tracker', icon: Code2 },
    { to: '/reviews', label: 'Weekly Journal', icon: BookOpenCheck },
    { to: '/projects', label: 'Projects', icon: FolderKanban },
    { to: '/career', label: 'Career & Pipeline', icon: Briefcase },
    { to: '/analytics', label: 'Analytics', icon: BarChart3 },
    { to: '/search', label: 'Search', icon: Search },
    { to: '/ai', label: 'AI Study Assistant', icon: Bot },
    { to: '/settings', label: 'Settings & Data', icon: Settings },
  ];

  return (
    <>
      {/* Mobile backdrop */}
      {isOpen && (
        <div
          className="fixed inset-0 bg-black/60 z-40 lg:hidden backdrop-blur-sm"
          onClick={onClose}
        />
      )}

      <aside className={`sidebar ${isOpen ? 'open' : ''}`}>
        <div className="sidebar-brand flex justify-between items-center">
          <div>
            <h1 className="flex items-center gap-2 text-xl font-bold">
              📚 StudyTracker
            </h1>
            <p className="text-xs text-muted">TCS → Product Engineer (2026–27)</p>
          </div>
          <button
            className="lg:hidden p-1.5 text-muted hover:text-primary rounded-md"
            onClick={onClose}
            aria-label="Close menu"
          >
            <X size={20} />
          </button>
        </div>

        <nav className="sidebar-nav">
          {navItems.map((item) => {
            const Icon = item.icon;
            return (
              <NavLink
                key={item.to}
                to={item.to}
                onClick={() => {
                  if (window.innerWidth < 1024) onClose();
                }}
                className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}
                end={item.to === '/'}
              >
                <Icon size={18} className="nav-icon" />
                <span>{item.label}</span>
              </NavLink>
            );
          })}
        </nav>

        <div className="sidebar-footer">
          <button
            className="theme-toggle mb-2"
            onClick={toggleTheme}
            aria-label="Toggle theme mode"
          >
            <span className="flex items-center gap-2">
              {theme === 'dark' ? <Moon size={16} /> : <Sun size={16} />}
              <span>{theme === 'dark' ? 'Dark Mode' : 'Light Mode'}</span>
            </span>
            <span className="text-xs text-muted">⚡</span>
          </button>

          <button
            className="btn btn-secondary w-full text-xs justify-center"
            onClick={logout}
          >
            <LogOut size={14} />
            <span>Sign Out</span>
          </button>
        </div>
      </aside>
    </>
  );
};
