import React, { createContext, useContext, useState, useEffect } from 'react';
import { UserDto, AuthResponse } from '../types';
import { api } from '../services/api';

interface AuthContextType {
  user: UserDto | null;
  token: string | null;
  loading: boolean;
  login: (credentials: any) => Promise<void>;
  register: (data: any) => Promise<void>;
  logout: () => Promise<void>;
  updateUser: (updated: UserDto) => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<UserDto | null>(null);
  const [token, setToken] = useState<string | null>(() => localStorage.getItem('studytracker_token'));
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function loadUser() {
      if (token) {
        try {
          const u = await api.getMe();
          setUser(u);
        } catch (e) {
          console.warn('Failed to restore session:', e);
          localStorage.removeItem('studytracker_token');
          setToken(null);
          setUser(null);
        }
      }
      setLoading(false);
    }
    loadUser();
  }, [token]);

  const handleAuthSuccess = (res: AuthResponse) => {
    localStorage.setItem('studytracker_token', res.token);
    setToken(res.token);
    setUser(res.user);
  };

  const login = async (credentials: any) => {
    const res = await api.login(credentials);
    handleAuthSuccess(res);
  };

  const register = async (data: any) => {
    const res = await api.register(data);
    handleAuthSuccess(res);
  };

  const logout = async () => {
    try {
      await api.logout();
    } catch {
      // ignore
    }
    localStorage.removeItem('studytracker_token');
    setToken(null);
    setUser(null);
  };

  const updateUser = (updated: UserDto) => {
    setUser(updated);
  };

  return (
    <AuthContext.Provider value={{ user, token, loading, login, register, logout, updateUser }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error('useAuth must be used within an AuthProvider');
  return context;
};
