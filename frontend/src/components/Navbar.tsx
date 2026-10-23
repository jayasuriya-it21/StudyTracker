import React from 'react';
import { useAuth } from '../contexts/AuthContext';
import { Menu, Target, Clock } from 'lucide-react';

interface NavbarProps {
  onOpenSidebar: () => void;
}

export const Navbar: React.FC<NavbarProps> = ({ onOpenSidebar }) => {
  const { user } = useAuth();

  return (
    <header className="navbar">
      <div className="flex items-center gap-3">
        <button
          className="menu-toggle-btn lg:hidden"
          onClick={onOpenSidebar}
          aria-label="Open menu"
        >
          <Menu size={22} />
        </button>
        <div>
          <span className="font-semibold text-sm sm:text-base">
            {user ? `Hello, ${user.name}` : 'StudyTracker'}
          </span>
          <span className="hidden sm:inline-block ml-2 text-xs text-muted">
            · Target: ₹30–40 LPA Ambition
          </span>
        </div>
      </div>

      <div className="flex items-center gap-2 sm:gap-3">
        <div className="hidden sm:flex items-center gap-1.5 px-2.5 py-1 rounded-full bg-blue-500/10 text-blue-500 text-xs font-semibold">
          <Target size={14} />
          <span>Product SDE / DevSecOps</span>
        </div>
        <div className="flex items-center gap-1 px-2.5 py-1 rounded-full bg-emerald-500/10 text-emerald-500 text-xs font-semibold">
          <Clock size={14} />
          <span>14h/week target</span>
        </div>
      </div>
    </header>
  );
};
