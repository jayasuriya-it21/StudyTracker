import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { api } from '../services/api';
import { useToast } from '../components/Toast';
import {
  Send,
  AlertTriangle,
  ShieldAlert,
} from 'lucide-react';
import { VerificationRating } from '../types';

interface ChatMessage {
  id: number;
  sender: 'USER' | 'ASSISTANT';
  content: string;
  verificationRating?: VerificationRating;
  createdAt: string;
}

export const AiAssistantPage: React.FC = () => {
  const queryClient = useQueryClient();
  const { showToast } = useToast();

  const [inputMessage, setInputMessage] = useState('');
  const [activeMode, setActiveMode] = useState<string>('HINTS_FIRST');
  const [conversationId, setConversationId] = useState<number | undefined>(undefined);
  const [messages, setMessages] = useState<ChatMessage[]>(() => [
    {
      id: 0,
      sender: 'ASSISTANT',
      content:
        "Hello Jayasuriya! I am your AI Study Coach. I operate in **Hints-First mode** by default to help you develop independent problem-solving skills rather than copying code. How can I assist your Java, Spring Boot, DSA, or AppSec preparation today?",
      createdAt: '00:00',
    },
  ]);

  const { data: status } = useQuery({
    queryKey: ['aiStatus'],
    queryFn: api.getAiStatus,
  });

  const chatMutation = useMutation({
    mutationFn: (msg: string) =>
      api.chatAi({
        conversationId,
        message: msg,
        mode: activeMode,
      }),
    onSuccess: (data) => {
      setConversationId(data.conversationId);
      const assistantMsg: ChatMessage = {
        id: data.messageId,
        sender: 'ASSISTANT',
        content: data.reply,
        verificationRating: 'UNRATED',
        createdAt: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      };
      setMessages((prev) => [...prev, assistantMsg]);
      queryClient.invalidateQueries({ queryKey: ['aiStatus'] });
    },
    onError: (err: any) => {
      showToast(err.message, 'error');
    },
  });

  const rateMutation = useMutation({
    mutationFn: ({ messageId, rating }: { messageId: number; rating: VerificationRating }) =>
      api.rateAiMessage(messageId, rating),
    onSuccess: (_, vars) => {
      setMessages((prev) =>
        prev.map((m) => (m.id === vars.messageId ? { ...m, verificationRating: vars.rating } : m))
      );
      showToast(`Marked as ${vars.rating.replace('_', ' ')}!`, 'success');
    },
  });

  const handleSend = (e: React.FormEvent) => {
    e.preventDefault();
    if (!inputMessage.trim() || chatMutation.isPending) return;

    const userMsg: ChatMessage = {
      id: Date.now(),
      sender: 'USER',
      content: inputMessage.trim(),
      createdAt: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
    };

    setMessages((prev) => [...prev, userMsg]);
    const toSend = inputMessage.trim();
    setInputMessage('');
    chatMutation.mutate(toSend);
  };

  const modes = [
    { id: 'HINTS_FIRST', label: '💡 Hints First', desc: 'Socratic guidance; prompts thinking without spoon-feeding answers' },
    { id: 'QUIZ', label: '📝 Quiz Me', desc: 'Questions your understanding of algorithms & system trade-offs' },
    { id: 'MOCK_INTERVIEW', label: '🎙️ Mock Interview', desc: 'Conducts interactive behavioral or technical interview rounds' },
    { id: 'EXPLAIN', label: '📖 Deep Explain', desc: 'Breaks down architectures, security pitfalls & concurrency' },
  ];

  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <div>
          <h1 className="text-2xl font-bold tracking-tight">AI Engineering Study Coach</h1>
          <p className="text-sm text-muted">
            Direct AI, evaluate outputs, spot hallucinations, and verify solutions independently.
          </p>
        </div>

        {/* Provider Status Pill */}
        <div className="flex items-center gap-2">
          <span className="text-xs px-3 py-1 rounded-full bg-purple-500/10 text-purple-400 font-semibold border border-purple-500/20">
            Provider: <strong>{status?.provider || 'MOCK'}</strong> ({status?.model || 'mock-educational'})
          </span>
          <span className="text-xs px-2.5 py-1 rounded-full bg-card-hover border border-border text-muted font-mono">
            {status?.dailyRequestsRemaining ?? 50} left today
          </span>
        </div>
      </div>

      {/* Mandatory Verification Notice */}
      <div className="p-3.5 rounded-xl border border-amber-500/30 bg-amber-500/10 flex items-start gap-3">
        <AlertTriangle className="text-amber-500 shrink-0 mt-0.5" size={18} />
        <p className="text-xs text-muted">
          <strong className="text-amber-500 font-semibold">Verification Advisory: </strong>
          AI-generated code and explanations can contain subtle hallucinations, security vulnerabilities, or suboptimal edge cases. Always verify suggestions independently by running tests and consulting official specifications.
        </p>
      </div>

      {/* Mode Selector */}
      <div className="grid grid-cols-2 sm:grid-cols-4 gap-2">
        {modes.map((m) => (
          <button
            key={m.id}
            onClick={() => setActiveMode(m.id)}
            className={`p-3 rounded-xl border text-left transition-all ${
              activeMode === m.id
                ? 'bg-primary/10 border-primary text-primary font-semibold'
                : 'bg-card border-border text-muted hover:border-primary/40'
            }`}
          >
            <div className="text-xs font-bold mb-0.5">{m.label}</div>
            <div className="text-[11px] opacity-80 line-clamp-1">{m.desc}</div>
          </button>
        ))}
      </div>

      {/* Chat Area */}
      <div className="card flex flex-col h-[520px]">
        {/* Messages Scroll Area */}
        <div className="flex-1 overflow-y-auto p-4 space-y-4">
          {messages.map((msg) => (
            <div
              key={msg.id}
              className={`flex flex-col ${
                msg.sender === 'USER' ? 'items-end' : 'items-start'
              }`}
            >
              <div
                className={`max-w-[85%] rounded-2xl p-4 text-sm leading-relaxed ${
                  msg.sender === 'USER'
                    ? 'bg-primary text-white rounded-tr-none'
                    : 'bg-card-hover border border-border text-main rounded-tl-none'
                }`}
              >
                <div className="whitespace-pre-line">{msg.content}</div>

                {/* Rating Bar for Assistant responses */}
                {msg.sender === 'ASSISTANT' && msg.id !== 0 && (
                  <div className="mt-3 pt-2 border-t border-border/60 flex flex-wrap items-center justify-between gap-2 text-xs">
                    <span className="text-[11px] text-muted flex items-center gap-1">
                      <ShieldAlert size={12} />
                      Verify Output:
                    </span>
                    <div className="flex items-center gap-1.5">
                      <button
                        className={`px-2 py-0.5 rounded text-[10px] font-semibold transition-all ${
                          msg.verificationRating === 'VERIFIED'
                            ? 'bg-emerald-500 text-white'
                            : 'bg-emerald-500/10 text-emerald-500 hover:bg-emerald-500/20'
                        }`}
                        onClick={() => rateMutation.mutate({ messageId: msg.id, rating: 'VERIFIED' })}
                      >
                        ✓ Verified Correct
                      </button>
                      <button
                        className={`px-2 py-0.5 rounded text-[10px] font-semibold transition-all ${
                          msg.verificationRating === 'PARTIALLY_CORRECT'
                            ? 'bg-amber-500 text-white'
                            : 'bg-amber-500/10 text-amber-500 hover:bg-amber-500/20'
                        }`}
                        onClick={() => rateMutation.mutate({ messageId: msg.id, rating: 'PARTIALLY_CORRECT' })}
                      >
                        ~ Partial
                      </button>
                      <button
                        className={`px-2 py-0.5 rounded text-[10px] font-semibold transition-all ${
                          msg.verificationRating === 'INCORRECT'
                            ? 'bg-red-500 text-white'
                            : 'bg-red-500/10 text-red-500 hover:bg-red-500/20'
                        }`}
                        onClick={() => rateMutation.mutate({ messageId: msg.id, rating: 'INCORRECT' })}
                      >
                        ✗ Bug / Hallucination
                      </button>
                    </div>
                  </div>
                )}
              </div>
              <span className="text-[10px] text-muted mt-1 px-1">{msg.createdAt}</span>
            </div>
          ))}

          {chatMutation.isPending && (
            <div className="flex items-start gap-2">
              <div className="bg-card-hover border border-border p-3 rounded-2xl rounded-tl-none text-xs text-muted flex items-center gap-2">
                <div className="w-2 h-2 rounded-full bg-primary animate-ping"></div>
                <span>Coach is thinking in {activeMode} mode...</span>
              </div>
            </div>
          )}
        </div>

        {/* Input Footer */}
        <form onSubmit={handleSend} className="p-3 border-t border-border flex gap-2">
          <input
            type="text"
            placeholder={`Ask a question in ${activeMode} mode...`}
            value={inputMessage}
            onChange={(e) => setInputMessage(e.target.value)}
            disabled={chatMutation.isPending}
            className="flex-1 text-sm py-2 px-3"
          />
          <button
            type="submit"
            className="btn btn-primary"
            disabled={chatMutation.isPending || !inputMessage.trim()}
          >
            <Send size={16} />
            <span>Send</span>
          </button>
        </form>
      </div>
    </div>
  );
};
