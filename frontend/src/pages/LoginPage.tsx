import React, { useState } from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { useToast } from '../components/Toast';
import { LogIn, UserPlus, Sparkles } from 'lucide-react';

export const LoginPage: React.FC = () => {
  const { user, login, register } = useAuth();
  const { showToast } = useToast();
  const [isRegister, setIsRegister] = useState(false);
  const [loading, setLoading] = useState(false);

  // Form states initialized to empty for secure credentials handling
  const [name, setName] = useState('');
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  if (user) {
    return <Navigate to="/" replace />;
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      if (isRegister) {
        if (!name.trim() || !username.trim() || !email.trim() || !password) {
          showToast('Please fill in all registration fields', 'error');
          return;
        }
        await register({ name: name.trim(), username: username.trim(), email: email.trim(), password });
        showToast('Account registered successfully! Personalized plan ready.', 'success');
      } else {
        if (!username.trim() || !password) {
          showToast('Please provide your username and password', 'error');
          return;
        }
        await login({ username: username.trim(), password });
        showToast('Welcome back! Ready to prepare.', 'success');
      }
    } catch (err: any) {
      showToast(err.message || 'Authentication failed', 'error');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center p-4 bg-primary">
      <div className="card w-full max-w-md p-6 sm:p-8 shadow-xl">
        <div className="text-center mb-6">
          <div className="inline-flex p-3 rounded-2xl bg-blue-500/10 text-blue-500 mb-3">
            <Sparkles size={28} />
          </div>
          <h1 className="text-2xl font-bold tracking-tight">StudyTracker</h1>
          <p className="text-sm text-muted mt-1">
            Jayasuriya's Transition Roadmap: Java/Flutter → Secure Product Engineer
          </p>
        </div>

        <div className="flex border-b border-border mb-6">
          <button
            type="button"
            className={`flex-1 pb-3 text-sm font-semibold text-center border-b-2 transition-all ${
              !isRegister
                ? 'border-primary text-primary'
                : 'border-transparent text-muted hover:text-main'
            }`}
            onClick={() => setIsRegister(false)}
          >
            Sign In
          </button>
          <button
            type="button"
            className={`flex-1 pb-3 text-sm font-semibold text-center border-b-2 transition-all ${
              isRegister
                ? 'border-primary text-primary'
                : 'border-transparent text-muted hover:text-main'
            }`}
            onClick={() => setIsRegister(true)}
          >
            Create New Plan
          </button>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          {isRegister && (
            <>
              <div>
                <label className="block text-xs font-semibold text-muted mb-1">Full Name</label>
                <input
                  type="text"
                  required
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  placeholder="e.g. Jayasuriya"
                  autoFocus={isRegister}
                />
              </div>

              <div>
                <label className="block text-xs font-semibold text-muted mb-1">Email Address</label>
                <input
                  type="email"
                  required
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="name@example.com"
                />
              </div>
            </>
          )}

          <div>
            <label className="block text-xs font-semibold text-muted mb-1">Username</label>
            <input
              type="text"
              required
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="username"
              autoFocus={!isRegister}
            />
          </div>

          <div>
            <label className="block text-xs font-semibold text-muted mb-1">Password</label>
            <input
              type="password"
              required
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="••••••••"
            />
          </div>

          <button
            type="submit"
            className="btn btn-primary w-full justify-center py-2.5 mt-2"
            disabled={loading}
          >
            {isRegister ? <UserPlus size={16} /> : <LogIn size={16} />}
            <span>{loading ? 'Processing...' : isRegister ? 'Start Preparation' : 'Sign In'}</span>
          </button>
        </form>

        <div className="mt-6 pt-4 border-t border-border flex flex-col gap-2">
          <p className="text-[11px] text-center text-muted">
            All user data is strictly isolated in PostgreSQL with secure HTTP-only cookies.
          </p>
        </div>
      </div>
    </div>
  );
};
