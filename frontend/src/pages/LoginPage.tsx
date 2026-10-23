import React, { useState } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { useToast } from '../components/Toast';
import { LogIn, UserPlus, Sparkles } from 'lucide-react';

export const LoginPage: React.FC = () => {
  const { login, register } = useAuth();
  const { showToast } = useToast();
  const [isRegister, setIsRegister] = useState(false);
  const [loading, setLoading] = useState(false);

  // Form states
  const [name, setName] = useState('Jayasuriya');
  const [username, setUsername] = useState('jayasuriya');
  const [email, setEmail] = useState('jayasuriya@example.com');
  const [password, setPassword] = useState('password123');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      if (isRegister) {
        await register({ name, username, email, password });
        showToast('Account registered successfully! Personalized plan ready.', 'success');
      } else {
        await login({ username, password });
        showToast('Welcome back! Ready to prepare.', 'success');
      }
    } catch (err: any) {
      showToast(err.message || 'Authentication failed', 'error');
    } finally {
      setLoading(false);
    }
  };

  const handleFillDemo = () => {
    setUsername('jayasuriya');
    setPassword('password123');
    setIsRegister(false);
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
            AI-Ready Career Plan: TCS CBD-WCAS → Product Engineer
          </p>
        </div>

        <div className="flex border-b border-border mb-6">
          <button
            type="button"
            className={`flex-1 pb-3 text-sm font-semibold text-center border-b-2 transition-colors ${
              !isRegister ? 'border-accent text-accent' : 'border-transparent text-muted'
            }`}
            onClick={() => setIsRegister(false)}
          >
            Sign In
          </button>
          <button
            type="button"
            className={`flex-1 pb-3 text-sm font-semibold text-center border-b-2 transition-colors ${
              isRegister ? 'border-accent text-accent' : 'border-transparent text-muted'
            }`}
            onClick={() => setIsRegister(true)}
          >
            Create Account
          </button>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          {isRegister && (
            <div>
              <label className="block text-xs font-semibold text-muted mb-1">Full Name</label>
              <input
                type="text"
                className="form-control"
                required
                value={name}
                onChange={(e) => setName(e.target.value)}
                placeholder="e.g. Jayasuriya"
              />
            </div>
          )}

          <div>
            <label className="block text-xs font-semibold text-muted mb-1">
              {isRegister ? 'Username' : 'Username or Email'}
            </label>
            <input
              type="text"
              className="form-control"
              required
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="e.g. jayasuriya"
            />
          </div>

          {isRegister && (
            <div>
              <label className="block text-xs font-semibold text-muted mb-1">Email Address</label>
              <input
                type="email"
                className="form-control"
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="e.g. jayasuriya@example.com"
              />
            </div>
          )}

          <div>
            <label className="block text-xs font-semibold text-muted mb-1">Password</label>
            <input
              type="password"
              className="form-control"
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
          <button
            type="button"
            className="btn btn-secondary btn-sm justify-center text-xs"
            onClick={handleFillDemo}
          >
            🔑 Fill Default Local Demo Account (jayasuriya / password123)
          </button>
          <p className="text-[11px] text-center text-muted">
            All user data is strictly isolated in PostgreSQL. Start date: Nov 2, 2026.
          </p>
        </div>
      </div>
    </div>
  );
};
