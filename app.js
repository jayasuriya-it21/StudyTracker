/**
 * StudyTracker Application Logic
 * Single-Page Web Application for 52-Week TCS-to-Product-Company Plan
 */

class StudyTrackerApp {
  constructor() {
    this.storageKey = 'studyTracker_state_v1';
    this.state = this.loadState();
    this.charts = {};
    this.selectedWeek = this.getCurrentWeek();

    this.initTheme();
    this.initStreak();
    this.initUI();
    this.bindEvents();
    this.render();
  }

  // ==========================================
  // STATE MANAGEMENT
  // ==========================================

  getDefaultState() {
    return {
      taskCompletions: {},
      customTasks: {},
      weeklyNotes: {},
      dsaCount: 0,
      streak: 1,
      lastActiveDate: new Date().toISOString().split('T')[0],
      milestoneCompleted: {},
      projectProgress: {
        1: { progress: 0, status: "not-started", notes: "" },
        2: { progress: 0, status: "not-started", notes: "" },
        3: { progress: 0, status: "not-started", notes: "" }
      },
      theme: "dark",
      startDate: "2026-11-02"
    };
  }

  loadState() {
    try {
      const saved = localStorage.getItem(this.storageKey);
      if (saved) {
        const parsed = JSON.parse(saved);
        return { ...this.getDefaultState(), ...parsed };
      }
    } catch (e) {
      console.error("Failed to load state from localStorage:", e);
    }
    return this.getDefaultState();
  }

  saveState() {
    try {
      localStorage.setItem(this.storageKey, JSON.stringify(this.state));
    } catch (e) {
      console.error("Failed to save state:", e);
      this.showToast("Failed to save state to localStorage", "error");
    }
  }

  // ==========================================
  // DATE & TIME HELPERS
  // ==========================================

  getCurrentWeek() {
    const start = new Date(this.state.startDate || "2026-11-02");
    const today = new Date();
    const diffMs = today.getTime() - start.getTime();
    const diffDays = Math.floor(diffMs / (1000 * 60 * 60 * 24));
    
    if (diffDays < 0) return 1; // Prior to start date
    const weekNum = Math.floor(diffDays / 7) + 1;
    return Math.min(Math.max(weekNum, 1), 52);
  }

  getWeekDateRange(weekNum) {
    const start = new Date(this.state.startDate || "2026-11-02");
    const weekStart = new Date(start);
    weekStart.setDate(start.getDate() + (weekNum - 1) * 7);
    
    const weekEnd = new Date(weekStart);
    weekEnd.setDate(weekStart.getDate() + 6);

    const opts = { month: 'short', day: 'numeric', year: 'numeric' };
    return `${weekStart.toLocaleDateString('en-US', opts)} – ${weekEnd.toLocaleDateString('en-US', opts)}`;
  }

  getPhaseForWeek(weekNum) {
    if (!PLAN_DATA || !PLAN_DATA.phases) return null;
    return PLAN_DATA.phases.find(p => weekNum >= p.weeks[0] && weekNum <= p.weeks[1]) || PLAN_DATA.phases[0];
  }

  getWeekData(weekNum) {
    if (!PLAN_DATA || !PLAN_DATA.weeks) return null;
    return PLAN_DATA.weeks.find(w => w.week === weekNum) || PLAN_DATA.weeks[0];
  }

  initStreak() {
    const today = new Date().toISOString().split('T')[0];
    if (!this.state.lastActiveDate) {
      this.state.streak = 1;
      this.state.lastActiveDate = today;
    } else if (this.state.lastActiveDate !== today) {
      const last = new Date(this.state.lastActiveDate);
      const curr = new Date(today);
      const diffDays = Math.round((curr - last) / (1000 * 60 * 60 * 24));

      if (diffDays === 1) {
        this.state.streak = (this.state.streak || 0) + 1;
      } else if (diffDays > 1) {
        this.state.streak = 1;
      }
      this.state.lastActiveDate = today;
    }
    this.saveState();
  }

  // ==========================================
  // THEME MANAGEMENT
  // ==========================================

  initTheme() {
    const theme = this.state.theme || 'dark';
    document.documentElement.setAttribute('data-theme', theme);
    this.updateThemeButton();
  }

  toggleTheme() {
    const current = document.documentElement.getAttribute('data-theme') || 'dark';
    const next = current === 'dark' ? 'light' : 'dark';
    this.state.theme = next;
    document.documentElement.setAttribute('data-theme', next);
    this.saveState();
    this.updateThemeButton();

    // Re-render charts with updated theme palette
    this.renderAllCharts();
    this.showToast(`Switched to ${next} mode`, 'info');
  }

  updateThemeButton() {
    const theme = this.state.theme || 'dark';
    const textEl = document.getElementById('themeToggleText');
    const iconEl = document.getElementById('themeToggleIcon');
    if (textEl && iconEl) {
      if (theme === 'dark') {
        textEl.textContent = '🌙 Dark Mode';
        iconEl.textContent = '⚡';
      } else {
        textEl.textContent = '☀️ Light Mode';
        iconEl.textContent = '✨';
      }
    }
    const themeSelect = document.getElementById('settingThemeSelect');
    if (themeSelect) themeSelect.value = theme;
  }

  // ==========================================
  // UI INITIALIZATION & EVENT BINDINGS
  // ==========================================

  initUI() {
    // Populate week dropdown
    const weekSelect = document.getElementById('weekDropdown');
    const journalWeekSelect = document.getElementById('journalWeekSelect');
    
    if (weekSelect && PLAN_DATA.weeks) {
      weekSelect.innerHTML = '';
      if (journalWeekSelect) journalWeekSelect.innerHTML = '';

      PLAN_DATA.weeks.forEach(w => {
        const phase = this.getPhaseForWeek(w.week);
        const opt = document.createElement('option');
        opt.value = w.week;
        opt.textContent = `Week ${w.week} (Phase ${phase.letter}) - ${w.engineering.substring(0, 32)}...`;
        weekSelect.appendChild(opt);

        if (journalWeekSelect) {
          const jOpt = document.createElement('option');
          jOpt.value = w.week;
          jOpt.textContent = `Week ${w.week} - Phase ${phase.letter}`;
          journalWeekSelect.appendChild(jOpt);
        }
      });

      weekSelect.value = this.selectedWeek;
      if (journalWeekSelect) journalWeekSelect.value = this.selectedWeek;
    }

    // Populate Target Companies
    const companiesEl = document.getElementById('targetCompaniesList');
    if (companiesEl && PLAN_DATA.targetCompanies) {
      companiesEl.innerHTML = PLAN_DATA.targetCompanies.map(c => 
        `<span class="category-badge badge-career" style="font-size: 0.85rem; padding: 0.35rem 0.75rem;">🏢 ${c}</span>`
      ).join('');
    }

    // Date picker initial value
    const dateInput = document.getElementById('settingStartDate');
    if (dateInput) dateInput.value = this.state.startDate || '2026-11-02';
  }

  bindEvents() {
    // Navigation items
    document.querySelectorAll('.sidebar-nav .nav-item').forEach(item => {
      item.addEventListener('click', () => {
        const page = item.getAttribute('data-page');
        this.navigateTo(page);
      });
    });

    // Mobile Hamburger
    const menuToggle = document.getElementById('menuToggle');
    const sidebar = document.getElementById('sidebar');
    if (menuToggle && sidebar) {
      menuToggle.addEventListener('click', () => {
        sidebar.classList.toggle('open');
      });
      // Close sidebar when clicking main content on mobile
      document.getElementById('mainContent').addEventListener('click', () => {
        if (window.innerWidth <= 768 && sidebar.classList.contains('open')) {
          sidebar.classList.remove('open');
        }
      });
    }

    // Theme toggling
    document.getElementById('themeToggleBtn')?.addEventListener('click', () => this.toggleTheme());
    document.getElementById('settingThemeSelect')?.addEventListener('change', (e) => {
      if (e.target.value !== this.state.theme) this.toggleTheme();
    });

    // Week navigation controls
    document.getElementById('prevWeekBtn')?.addEventListener('click', () => {
      if (this.selectedWeek > 1) {
        this.selectedWeek--;
        this.renderWeeklyPage();
      }
    });

    document.getElementById('nextWeekBtn')?.addEventListener('click', () => {
      if (this.selectedWeek < 52) {
        this.selectedWeek++;
        this.renderWeeklyPage();
      }
    });

    document.getElementById('weekDropdown')?.addEventListener('change', (e) => {
      this.selectedWeek = parseInt(e.target.value, 10);
      this.renderWeeklyPage();
    });

    document.getElementById('jumpToCurrentWeekBtn')?.addEventListener('click', () => {
      this.selectedWeek = this.getCurrentWeek();
      this.renderWeeklyPage();
    });

    document.getElementById('jumpToWeeklyBtn')?.addEventListener('click', () => {
      this.selectedWeek = this.getCurrentWeek();
      this.navigateTo('weekly');
    });

    // DSA Counter Controls
    document.getElementById('dsaIncBtn')?.addEventListener('click', () => this.updateDSA(1));
    document.getElementById('dsaInc5Btn')?.addEventListener('click', () => this.updateDSA(5));
    document.getElementById('dsaDecBtn')?.addEventListener('click', () => this.updateDSA(-1));

    // Weekly Notes saving
    document.getElementById('saveWeeklyNotesBtn')?.addEventListener('click', () => this.saveWeeklyNotes());

    // Journal Entry saving
    document.getElementById('saveJournalBtn')?.addEventListener('click', () => this.saveJournalEntry());
    document.getElementById('journalWeekSelect')?.addEventListener('change', (e) => {
      this.loadJournalEntry(parseInt(e.target.value, 10));
    });

    // Modal controls for Custom Task
    const modal = document.getElementById('taskModal');
    document.getElementById('openAddTaskModalBtn')?.addEventListener('click', () => {
      document.getElementById('modalWeekNum').textContent = this.selectedWeek;
      document.getElementById('taskTextInput').value = '';
      modal.classList.add('active');
    });
    document.getElementById('closeModalBtn')?.addEventListener('click', () => modal.classList.remove('active'));
    document.getElementById('cancelModalBtn')?.addEventListener('click', () => modal.classList.remove('active'));
    
    document.getElementById('addTaskForm')?.addEventListener('submit', (e) => {
      e.preventDefault();
      const text = document.getElementById('taskTextInput').value.trim();
      const cat = document.getElementById('taskCategorySelect').value;
      if (text) {
        this.addCustomTask(this.selectedWeek, text, cat);
        modal.classList.remove('active');
      }
    });

    // Settings actions
    document.getElementById('settingStartDate')?.addEventListener('change', (e) => {
      this.state.startDate = e.target.value;
      this.saveState();
      this.render();
      this.showToast('Start date updated', 'success');
    });

    document.getElementById('exportDataBtn')?.addEventListener('click', () => this.exportData());
    document.getElementById('importDataBtn')?.addEventListener('click', () => {
      document.getElementById('importFileInput').click();
    });
    document.getElementById('importFileInput')?.addEventListener('change', (e) => {
      if (e.target.files && e.target.files[0]) {
        this.importData(e.target.files[0]);
      }
    });
    document.getElementById('resetDataBtn')?.addEventListener('click', () => this.resetData());
  }

  // ==========================================
  // NAVIGATION
  // ==========================================

  navigateTo(pageId) {
    document.querySelectorAll('.sidebar-nav .nav-item').forEach(item => {
      item.classList.toggle('active', item.getAttribute('data-page') === pageId);
    });

    document.querySelectorAll('.page').forEach(page => {
      page.classList.remove('active');
    });

    const target = document.getElementById(pageId);
    if (target) {
      target.classList.add('active');
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    // Lazy render specific pages on visit
    if (pageId === 'weekly') this.renderWeeklyPage();
    if (pageId === 'progress') this.renderProgressPage();
    if (pageId === 'milestones') this.renderMilestonesPage();
    if (pageId === 'schedule') this.renderSchedulePage();
    if (pageId === 'projects') this.renderProjectsPage();
    if (pageId === 'notes') this.renderNotesPage();
  }

  // ==========================================
  // RENDER MASTER
  // ==========================================

  render() {
    this.renderDashboard();
    this.renderWeeklyPage();
    this.renderProgressPage();
    this.renderMilestonesPage();
    this.renderSchedulePage();
    this.renderProjectsPage();
    this.renderNotesPage();
  }

  // ==========================================
  // DASHBOARD RENDERING
  // ==========================================

  renderDashboard() {
    const currWeek = this.getCurrentWeek();
    const currPhase = this.getPhaseForWeek(currWeek);
    const weekData = this.getWeekData(currWeek);

    // Update Quick Stat Cards
    document.getElementById('dashWeekNum').textContent = `Week ${currWeek}`;
    document.getElementById('dashPhaseName').textContent = `${currPhase.letter}: ${currPhase.name.split('&')[0]}`;
    document.getElementById('dashStreak').textContent = `${this.state.streak || 1} Day${(this.state.streak || 1) > 1 ? 's' : ''}`;
    document.getElementById('dashDsaSolved').textContent = this.state.dsaCount || 0;

    // Completed task calculations
    const totals = this.calculateTaskTotals();
    document.getElementById('dashTasksCompleted').textContent = `${totals.completed} / ${totals.total}`;
    
    const overallPct = totals.total > 0 ? Math.round((totals.completed / totals.total) * 100) : 0;
    const overallText = document.getElementById('overallPercentText');
    const overallBar = document.getElementById('dashOverallBar');
    if (overallText) overallText.textContent = `${overallPct}% Complete`;
    if (overallBar) overallBar.style.width = `${overallPct}%`;

    // Phase Timeline
    const timelineEl = document.getElementById('dashPhaseTimeline');
    if (timelineEl && PLAN_DATA.phases) {
      timelineEl.innerHTML = PLAN_DATA.phases.map(p => {
        const isCurrent = p.id === currPhase.id;
        const isCompleted = currWeek > p.weeks[1];
        const statusClass = isCurrent ? 'current' : (isCompleted ? 'completed' : '');
        return `
          <div class="phase-item ${statusClass}" onclick="window.tracker.viewPhase(${p.weeks[0]})" title="${p.name} (${p.period})">
            <div class="phase-letter" style="${isCurrent ? `background: ${p.color};` : (isCompleted ? 'background: var(--success);' : '')}">
              ${isCompleted ? '✓' : p.letter}
            </div>
            <span class="phase-name">${p.letter}: ${p.name.substring(0, 14)}...</span>
          </div>
        `;
      }).join('');
    }

    // Week Focus Card
    document.getElementById('currentWeekFocusTitle').textContent = `🎯 Week ${currWeek} Focus (${currPhase.period})`;
    document.getElementById('dashEngFocus').textContent = weekData.engineering;
    document.getElementById('dashAiFocus').textContent = weekData.aiReadiness;

    // Quick Task Checklist for Current Week
    const quickList = document.getElementById('dashQuickTaskList');
    const weekTasks = this.getAllTasksForWeek(currWeek);
    const completedWeekTasks = weekTasks.filter(t => this.isTaskCompleted(t.id)).length;
    const weekPct = weekTasks.length > 0 ? Math.round((completedWeekTasks / weekTasks.length) * 100) : 0;
    
    const badge = document.getElementById('weekProgressBadge');
    if (badge) badge.textContent = `${weekPct}% completed`;

    if (quickList) {
      quickList.innerHTML = weekTasks.map(t => {
        const done = this.isTaskCompleted(t.id);
        const catInfo = PLAN_DATA.categoryColors[t.category] || PLAN_DATA.categoryColors.dsa;
        return `
          <div class="task-item ${done ? 'completed' : ''}">
            <input type="checkbox" id="dash-${t.id}" ${done ? 'checked' : ''} onchange="window.tracker.toggleTask('${t.id}', ${currWeek})">
            <label for="dash-${t.id}" class="task-text">${t.text}</label>
            <span class="category-badge" style="background: ${catInfo.bg}; color: ${catInfo.text};">${catInfo.label}</span>
          </div>
        `;
      }).join('');
    }

    // Render Dashboard Chart
    this.renderDashboardProgressChart();
  }

  viewPhase(startWeek) {
    this.selectedWeek = startWeek;
    this.navigateTo('weekly');
  }

  // ==========================================
  // WEEKLY PLAN RENDERING
  // ==========================================

  renderWeeklyPage() {
    const weekNum = this.selectedWeek;
    const weekData = this.getWeekData(weekNum);
    const phase = this.getPhaseForWeek(weekNum);

    // Sync dropdown
    const select = document.getElementById('weekDropdown');
    if (select) select.value = weekNum;

    // Header Info
    const phaseBadge = document.getElementById('weeklyPhaseBadge');
    if (phaseBadge) {
      phaseBadge.textContent = `Phase ${phase.letter}: ${phase.name}`;
      phaseBadge.style.backgroundColor = phase.color + '22';
      phaseBadge.style.color = phase.color;
    }
    document.getElementById('weeklyDateRange').textContent = this.getWeekDateRange(weekNum);
    document.getElementById('weeklyEngFocus').textContent = weekData.engineering;
    document.getElementById('weeklyAiFocus').textContent = weekData.aiReadiness;

    // Tasks List
    const allTasks = this.getAllTasksForWeek(weekNum);
    const completedCount = allTasks.filter(t => this.isTaskCompleted(t.id)).length;
    const pct = allTasks.length > 0 ? Math.round((completedCount / allTasks.length) * 100) : 0;

    document.getElementById('weeklyProgressBar').style.width = `${pct}%`;
    document.getElementById('weeklyTaskCountText').textContent = `${completedCount} of ${allTasks.length} tasks completed`;
    document.getElementById('weeklyTaskPercentText').textContent = `${pct}%`;

    const listEl = document.getElementById('weeklyTaskList');
    if (listEl) {
      listEl.innerHTML = allTasks.map(t => {
        const done = this.isTaskCompleted(t.id);
        const catInfo = PLAN_DATA.categoryColors[t.category] || PLAN_DATA.categoryColors.dsa;
        const isCustom = t.id.startsWith('custom-');
        return `
          <div class="task-item ${done ? 'completed' : ''}">
            <input type="checkbox" id="week-${t.id}" ${done ? 'checked' : ''} onchange="window.tracker.toggleTask('${t.id}', ${weekNum})">
            <label for="week-${t.id}" class="task-text">${t.text}</label>
            <span class="category-badge" style="background: ${catInfo.bg}; color: ${catInfo.text};">${catInfo.label}</span>
            ${isCustom ? `<button class="btn btn-secondary btn-sm" style="padding: 0.1rem 0.4rem; font-size: 0.7rem;" onclick="window.tracker.deleteCustomTask('${t.id}', ${weekNum})">✕</button>` : ''}
          </div>
        `;
      }).join('');
    }

    // Load notes for current week
    const savedNotes = (this.state.weeklyNotes && this.state.weeklyNotes[weekNum]) || {};
    const learnedInput = document.getElementById('weekLearnedNote');
    const aiInput = document.getElementById('weekAiNote');
    if (learnedInput) learnedInput.value = savedNotes.learned || '';
    if (aiInput) aiInput.value = savedNotes.ai || '';
  }

  // ==========================================
  // PROGRESS & STATS RENDERING
  // ==========================================

  renderProgressPage() {
    const totals = this.calculateTaskTotals();
    const overallPct = totals.total > 0 ? Math.round((totals.completed / totals.total) * 100) : 0;

    document.getElementById('statsOverallPercent').textContent = `${overallPct}%`;
    document.getElementById('statsOverallFill').style.width = `${overallPct}%`;
    document.getElementById('statsTasksRemaining').textContent = `${totals.completed} of ${totals.total} planned deliverables completed`;

    // DSA Progress
    const dsaCount = this.state.dsaCount || 0;
    document.getElementById('dsaProblemDisplay').textContent = dsaCount;
    const dsaPct = Math.min(Math.round((dsaCount / 160) * 100), 100);
    document.getElementById('dsaProgressBar').style.width = `${dsaPct}%`;

    // Skill Tracks
    const tracksContainer = document.getElementById('skillTracksContainer');
    if (tracksContainer && PLAN_DATA.skillTracks) {
      tracksContainer.innerHTML = PLAN_DATA.skillTracks.map(track => {
        const areas = track.areas.map(a => `<span class="skill-area-tag">${a}</span>`).join('');
        return `
          <div class="skill-card">
            <div class="skill-header">
              <span class="skill-icon">${track.icon}</span>
              <span class="skill-name">${track.name}</span>
            </div>
            <div class="skill-areas">${areas}</div>
          </div>
        `;
      }).join('');
    }

    // Render Progress Charts
    this.renderCategoryChart();
    this.renderWeeklyBarChart();
  }

  // ==========================================
  // MILESTONES RENDERING
  // ==========================================

  renderMilestonesPage() {
    const grid = document.getElementById('milestonesGrid');
    if (!grid || !PLAN_DATA.milestones) return;

    grid.innerHTML = PLAN_DATA.milestones.map(m => {
      const isDone = !!(this.state.milestoneCompleted && this.state.milestoneCompleted[m.id]);
      
      // Calculate progress of weeks up to targetWeek
      const weeksInPhase = PLAN_DATA.weeks.filter(w => w.week <= m.targetWeek && w.week > (m.targetWeek - 8));
      let total = 0, completed = 0;
      weeksInPhase.forEach(w => {
        const tasks = this.getAllTasksForWeek(w.week);
        total += tasks.length;
        completed += tasks.filter(t => this.isTaskCompleted(t.id)).length;
      });
      const pct = total > 0 ? Math.round((completed / total) * 100) : 0;

      return `
        <div class="milestone-card ${isDone ? 'completed' : ''}">
          <div class="milestone-period">${m.period} · Target: Week ${m.targetWeek}</div>
          <div class="milestone-title">${m.title}</div>
          <div class="milestone-desc">${m.description}</div>
          <div class="progress-bar mb-1">
            <div class="progress-fill green" style="width: ${pct}%;"></div>
          </div>
          <div class="milestone-footer">
            <span class="text-muted" style="font-size: 0.8rem;">${pct}% tasks done</span>
            <label class="flex gap-1" style="align-items: center; cursor: pointer; font-size: 0.85rem; font-weight: 600;">
              <input type="checkbox" ${isDone ? 'checked' : ''} onchange="window.tracker.toggleMilestone(${m.id})">
              <span>${isDone ? 'Milestone Achieved 🎉' : 'Mark Achieved'}</span>
            </label>
          </div>
        </div>
      `;
    }).join('');
  }

  toggleMilestone(id) {
    if (!this.state.milestoneCompleted) this.state.milestoneCompleted = {};
    this.state.milestoneCompleted[id] = !this.state.milestoneCompleted[id];
    this.saveState();
    this.renderMilestonesPage();
    this.showToast(this.state.milestoneCompleted[id] ? `Milestone #${id} marked as achieved! 🚀` : `Milestone #${id} unmarked`, 'success');
  }

  // ==========================================
  // SCHEDULE RENDERING
  // ==========================================

  renderSchedulePage() {
    const tbody = document.getElementById('scheduleTableBody');
    if (!tbody || !PLAN_DATA.weeklySchedule) return;

    const days = [
      { key: "monday", label: "Monday" },
      { key: "tuesday", label: "Tuesday" },
      { key: "wednesday", label: "Wednesday" },
      { key: "thursday", label: "Thursday" },
      { key: "friday", label: "Friday" },
      { key: "saturday", label: "Saturday (Deep Work)" },
      { key: "sunday", label: "Sunday (Review & Labs)" }
    ];

    tbody.innerHTML = days.map(d => {
      const items = PLAN_DATA.weeklySchedule[d.key] || [];
      const totalHours = items.reduce((acc, curr) => acc + curr.hours, 0);
      const activitiesHtml = items.map(act => {
        const catInfo = PLAN_DATA.categoryColors[act.category] || PLAN_DATA.categoryColors.dsa;
        return `
          <span class="activity-tag" style="background: ${catInfo.bg}; color: ${catInfo.text};">
            ${act.activity} (<strong>${act.hours}h</strong>)
          </span>
        `;
      }).join(' ');

      return `
        <tr>
          <td class="day-name">${d.label}</td>
          <td><div class="activity-list">${activitiesHtml}</div></td>
          <td style="font-family: var(--font-mono); font-weight: 600;">${totalHours.toFixed(1)}h</td>
        </tr>
      `;
    }).join('');

    this.renderScheduleChart();
  }

  // ==========================================
  // PROJECTS RENDERING
  // ==========================================

  renderProjectsPage() {
    const grid = document.getElementById('projectsGrid');
    if (!grid || !PLAN_DATA.projects) return;

    grid.innerHTML = PLAN_DATA.projects.map(p => {
      const pState = (this.state.projectProgress && this.state.projectProgress[p.id]) || { progress: 0, status: "not-started", notes: "" };
      const isFlagship = p.type === 'Flagship';
      
      const stackBadges = p.stack.map(s => `<span class="stack-tag">${s}</span>`).join('');
      const deliverablesList = p.deliverables.map(d => `<li style="font-size: 0.85rem; margin-bottom: 0.25rem; color: var(--text-secondary);">${d}</li>`).join('');

      return `
        <div class="project-card">
          <div class="flex" style="justify-content: space-between; align-items: center;">
            <span class="project-type ${isFlagship ? 'flagship' : 'secondary'}">${p.type} Project</span>
            <span class="text-muted" style="font-size: 0.85rem; font-family: var(--font-mono);">${pState.progress}%</span>
          </div>
          <h3>${p.name}</h3>
          <p class="text-muted" style="font-size: 0.85rem; margin-bottom: 0.75rem;">${p.tagline}</p>
          
          <div class="stack-tags">${stackBadges}</div>
          
          <h4 style="font-size: 0.85rem; margin-bottom: 0.35rem;">Core Deliverables:</h4>
          <ul style="padding-left: 1.25rem; margin-bottom: 1rem;">${deliverablesList}</ul>

          <div class="progress-bar mb-1">
            <div class="progress-fill ${isFlagship ? 'green' : 'purple'}" style="width: ${pState.progress}%;"></div>
          </div>
          
          <label style="font-size: 0.8rem; color: var(--text-muted);">Adjust Completion Progress:</label>
          <input type="range" class="progress-slider" min="0" max="100" value="${pState.progress}" 
                 oninput="window.tracker.updateProjectProgress(${p.id}, this.value)">

          <div style="margin-top: 0.75rem;">
            <label style="font-size: 0.8rem; color: var(--text-muted);">Development Status:</label>
            <select onchange="window.tracker.updateProjectStatus(${p.id}, this.value)">
              <option value="not-started" ${pState.status === 'not-started' ? 'selected' : ''}>Not Started</option>
              <option value="in-progress" ${pState.status === 'in-progress' ? 'selected' : ''}>In Progress</option>
              <option value="completed" ${pState.status === 'completed' ? 'selected' : ''}>Completed & Tested</option>
            </select>
          </div>

          <div style="margin-top: 0.75rem;">
            <label style="font-size: 0.8rem; color: var(--text-muted);">Project Implementation Notes:</label>
            <textarea placeholder="e.g., Repo link, architectural decisions, evaluation benchmarks..."
                      onchange="window.tracker.updateProjectNotes(${p.id}, this.value)">${pState.notes || ''}</textarea>
          </div>
        </div>
      `;
    }).join('');
  }

  updateProjectProgress(id, val) {
    if (!this.state.projectProgress) this.state.projectProgress = {};
    if (!this.state.projectProgress[id]) this.state.projectProgress[id] = { progress: 0, status: "not-started", notes: "" };
    this.state.projectProgress[id].progress = parseInt(val, 10);
    this.saveState();
    this.renderProjectsPage();
  }

  updateProjectStatus(id, status) {
    if (!this.state.projectProgress) this.state.projectProgress = {};
    if (!this.state.projectProgress[id]) this.state.projectProgress[id] = { progress: 0, status: "not-started", notes: "" };
    this.state.projectProgress[id].status = status;
    this.saveState();
    this.showToast('Project status updated', 'success');
  }

  updateProjectNotes(id, notes) {
    if (!this.state.projectProgress) this.state.projectProgress = {};
    if (!this.state.projectProgress[id]) this.state.projectProgress[id] = { progress: 0, status: "not-started", notes: "" };
    this.state.projectProgress[id].notes = notes;
    this.saveState();
    this.showToast('Project notes saved', 'success');
  }

  // ==========================================
  // NOTES / JOURNAL RENDERING
  // ==========================================

  renderNotesPage() {
    const sel = document.getElementById('journalWeekSelect');
    const weekNum = sel ? parseInt(sel.value, 10) : this.selectedWeek;
    this.loadJournalEntry(weekNum);
    this.renderPastNotesList();
  }

  loadJournalEntry(weekNum) {
    const data = (this.state.weeklyNotes && this.state.weeklyNotes[weekNum]) || {};
    document.getElementById('journalLearned').value = data.learned || '';
    document.getElementById('journalAi').value = data.ai || '';
    document.getElementById('journalWins').value = data.wins || '';
    document.getElementById('journalImprove').value = data.improve || '';
  }

  saveJournalEntry() {
    const sel = document.getElementById('journalWeekSelect');
    const weekNum = sel ? parseInt(sel.value, 10) : this.selectedWeek;

    if (!this.state.weeklyNotes) this.state.weeklyNotes = {};
    this.state.weeklyNotes[weekNum] = {
      learned: document.getElementById('journalLearned').value.trim(),
      ai: document.getElementById('journalAi').value.trim(),
      wins: document.getElementById('journalWins').value.trim(),
      improve: document.getElementById('journalImprove').value.trim(),
      updatedAt: new Date().toISOString()
    };

    this.saveState();
    this.renderPastNotesList();
    this.showToast(`Journal entry for Week ${weekNum} saved!`, 'success');
  }

  saveWeeklyNotes() {
    const weekNum = this.selectedWeek;
    if (!this.state.weeklyNotes) this.state.weeklyNotes = {};
    const existing = this.state.weeklyNotes[weekNum] || {};

    this.state.weeklyNotes[weekNum] = {
      ...existing,
      learned: document.getElementById('weekLearnedNote').value.trim(),
      ai: document.getElementById('weekAiNote').value.trim(),
      updatedAt: new Date().toISOString()
    };

    this.saveState();
    this.showToast(`Week ${weekNum} reflections saved!`, 'success');
  }

  renderPastNotesList() {
    const container = document.getElementById('pastNotesList');
    if (!container) return;

    const entries = Object.entries(this.state.weeklyNotes || {})
      .filter(([_, val]) => val && (val.learned || val.ai || val.wins || val.improve))
      .sort((a, b) => parseInt(b[0], 10) - parseInt(a[0], 10));

    if (entries.length === 0) {
      container.innerHTML = '<p class="text-muted" style="font-size: 0.9rem;">No reflection entries recorded yet. Select a week above to start journaling!</p>';
      return;
    }

    container.innerHTML = entries.map(([wNum, entry]) => {
      const phase = this.getPhaseForWeek(parseInt(wNum, 10));
      return `
        <div class="past-note-item">
          <div class="flex" style="justify-content: space-between; align-items: center; margin-bottom: 0.5rem;">
            <h4>Week ${wNum} (${phase.letter}: ${phase.name})</h4>
            <span class="text-muted" style="font-size: 0.75rem;">${entry.updatedAt ? new Date(entry.updatedAt).toLocaleDateString() : ''}</span>
          </div>
          ${entry.learned ? `<p><strong>Learned:</strong> ${entry.learned}</p>` : ''}
          ${entry.ai ? `<p><strong>AI Verification Log:</strong> ${entry.ai}</p>` : ''}
          ${entry.wins ? `<p><strong>Wins:</strong> ${entry.wins}</p>` : ''}
          ${entry.improve ? `<p><strong>Improve:</strong> ${entry.improve}</p>` : ''}
        </div>
      `;
    }).join('');
  }

  // ==========================================
  // TASK INTERACTIONS
  // ==========================================

  getAllTasksForWeek(weekNum) {
    const defaultTasks = (this.getWeekData(weekNum) && this.getWeekData(weekNum).tasks) || [];
    const custom = (this.state.customTasks && this.state.customTasks[weekNum]) || [];
    return [...defaultTasks, ...custom];
  }

  isTaskCompleted(taskId) {
    return !!(this.state.taskCompletions && this.state.taskCompletions[taskId]);
  }

  toggleTask(taskId, weekNum) {
    if (!this.state.taskCompletions) this.state.taskCompletions = {};
    this.state.taskCompletions[taskId] = !this.state.taskCompletions[taskId];
    this.saveState();

    this.renderDashboard();
    this.renderWeeklyPage();
    this.renderProgressPage();
    this.renderMilestonesPage();
  }

  addCustomTask(weekNum, text, category) {
    if (!this.state.customTasks) this.state.customTasks = {};
    if (!this.state.customTasks[weekNum]) this.state.customTasks[weekNum] = [];

    const newId = `custom-${weekNum}-${Date.now()}`;
    this.state.customTasks[weekNum].push({
      id: newId,
      text: text,
      category: category,
      completed: false
    });

    this.saveState();
    this.renderWeeklyPage();
    this.renderDashboard();
    this.showToast('Custom task added', 'success');
  }

  deleteCustomTask(taskId, weekNum) {
    if (!this.state.customTasks || !this.state.customTasks[weekNum]) return;
    this.state.customTasks[weekNum] = this.state.customTasks[weekNum].filter(t => t.id !== taskId);
    if (this.state.taskCompletions) delete this.state.taskCompletions[taskId];
    
    this.saveState();
    this.renderWeeklyPage();
    this.renderDashboard();
    this.showToast('Custom task removed', 'info');
  }

  calculateTaskTotals() {
    let total = 0;
    let completed = 0;

    if (PLAN_DATA.weeks) {
      PLAN_DATA.weeks.forEach(w => {
        const tasks = this.getAllTasksForWeek(w.week);
        total += tasks.length;
        completed += tasks.filter(t => this.isTaskCompleted(t.id)).length;
      });
    }

    return { total, completed };
  }

  updateDSA(delta) {
    const current = this.state.dsaCount || 0;
    const next = Math.max(current + delta, 0);
    this.state.dsaCount = next;
    this.saveState();
    this.renderDashboard();
    this.renderProgressPage();
    this.showToast(`DSA solved: ${next} problems! 🚀`, 'success');
  }

  // ==========================================
  // CHART RENDERING (Chart.js)
  // ==========================================

  getChartThemeColors() {
    const isDark = (this.state.theme || 'dark') === 'dark';
    return {
      textColor: isDark ? '#94a3b8' : '#475569',
      gridColor: isDark ? '#334155' : '#e2e8f0',
      bgCard: isDark ? '#1e293b' : '#ffffff'
    };
  }

  destroyChart(key) {
    if (this.charts[key]) {
      this.charts[key].destroy();
      delete this.charts[key];
    }
  }

  renderAllCharts() {
    this.renderDashboardProgressChart();
    this.renderCategoryChart();
    this.renderWeeklyBarChart();
    this.renderScheduleChart();
  }

  renderDashboardProgressChart() {
    const canvas = document.getElementById('progressChart');
    if (!canvas || !window.Chart) return;

    this.destroyChart('progressChart');
    const { textColor, gridColor } = this.getChartThemeColors();

    const phaseLabels = PLAN_DATA.phases.map(p => `Phase ${p.letter}`);
    const phaseCompletions = PLAN_DATA.phases.map(p => {
      let total = 0, comp = 0;
      for (let w = p.weeks[0]; w <= p.weeks[1]; w++) {
        const tasks = this.getAllTasksForWeek(w);
        total += tasks.length;
        comp += tasks.filter(t => this.isTaskCompleted(t.id)).length;
      }
      return total > 0 ? Math.round((comp / total) * 100) : 0;
    });

    this.charts.progressChart = new Chart(canvas, {
      type: 'bar',
      data: {
        labels: phaseLabels,
        datasets: [{
          label: '% Phase Deliverables Completed',
          data: phaseCompletions,
          backgroundColor: PLAN_DATA.phases.map(p => p.color),
          borderRadius: 6
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: { display: false }
        },
        scales: {
          y: {
            beginAtZero: true,
            max: 100,
            grid: { color: gridColor },
            ticks: { color: textColor, callback: v => v + '%' }
          },
          x: {
            grid: { display: false },
            ticks: { color: textColor }
          }
        }
      }
    });
  }

  renderCategoryChart() {
    const canvas = document.getElementById('categoryChart');
    if (!canvas || !window.Chart) return;

    this.destroyChart('categoryChart');
    const { textColor } = this.getChartThemeColors();

    const counts = { dsa: 0, java: 0, ai: 0, security: 0, project: 0, career: 0 };
    PLAN_DATA.weeks.forEach(w => {
      this.getAllTasksForWeek(w.week).forEach(t => {
        if (this.isTaskCompleted(t.id)) {
          const cat = t.category || 'dsa';
          counts[cat] = (counts[cat] || 0) + 1;
        }
      });
    });

    const labels = Object.keys(counts).map(k => PLAN_DATA.categoryColors[k]?.label || k);
    const data = Object.values(counts);

    this.charts.categoryChart = new Chart(canvas, {
      type: 'doughnut',
      data: {
        labels: labels,
        datasets: [{
          data: data.some(v => v > 0) ? data : [1, 1, 1, 1, 1, 1],
          backgroundColor: ['#3b82f6', '#f97316', '#8b5cf6', '#ef4444', '#22c55e', '#6b7280'],
          borderWidth: 2
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'bottom',
            labels: { color: textColor, font: { family: 'Inter', size: 11 } }
          }
        }
      }
    });
  }

  renderWeeklyBarChart() {
    const canvas = document.getElementById('weeklyProgressChart');
    if (!canvas || !window.Chart) return;

    this.destroyChart('weeklyProgressChart');
    const { textColor, gridColor } = this.getChartThemeColors();

    // Group weeks into 4-week milestones (13 intervals)
    const labels = [];
    const values = [];

    for (let i = 1; i <= 52; i += 4) {
      const end = Math.min(i + 3, 52);
      labels.push(`W${i}-${end}`);

      let total = 0, comp = 0;
      for (let w = i; w <= end; w++) {
        const tasks = this.getAllTasksForWeek(w);
        total += tasks.length;
        comp += tasks.filter(t => this.isTaskCompleted(t.id)).length;
      }
      values.push(total > 0 ? Math.round((comp / total) * 100) : 0);
    }

    this.charts.weeklyProgressChart = new Chart(canvas, {
      type: 'line',
      data: {
        labels: labels,
        datasets: [{
          label: 'Completion %',
          data: values,
          borderColor: '#3b82f6',
          backgroundColor: 'rgba(59, 130, 246, 0.1)',
          fill: true,
          tension: 0.3,
          pointRadius: 4
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: { legend: { display: false } },
        scales: {
          y: {
            beginAtZero: true,
            max: 100,
            grid: { color: gridColor },
            ticks: { color: textColor, callback: v => v + '%' }
          },
          x: {
            grid: { display: false },
            ticks: { color: textColor }
          }
        }
      }
    });
  }

  renderScheduleChart() {
    const canvas = document.getElementById('scheduleChart');
    if (!canvas || !window.Chart) return;

    this.destroyChart('scheduleChart');
    const { textColor } = this.getChartThemeColors();

    const breakdown = PLAN_DATA.weeklyHoursBreakdown;
    const labels = Object.keys(breakdown);
    const hours = labels.map(k => breakdown[k].hours);
    const colors = ['#3b82f6', '#f97316', '#8b5cf6', '#22c55e', '#ef4444', '#6b7280'];

    this.charts.scheduleChart = new Chart(canvas, {
      type: 'doughnut',
      data: {
        labels: labels,
        datasets: [{
          data: hours,
          backgroundColor: colors,
          borderWidth: 2
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'right',
            labels: { color: textColor, font: { family: 'Inter', size: 10 } }
          }
        }
      }
    });
  }

  // ==========================================
  // DATA IMPORT / EXPORT & BACKUP
  // ==========================================

  exportData() {
    const dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(this.state, null, 2));
    const dlAnchor = document.createElement('a');
    dlAnchor.setAttribute("href", dataStr);
    dlAnchor.setAttribute("download", `study_tracker_backup_${new Date().toISOString().split('T')[0]}.json`);
    document.body.appendChild(dlAnchor);
    dlAnchor.click();
    dlAnchor.remove();
    this.showToast('Progress exported successfully! 📁', 'success');
  }

  importData(file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      try {
        const imported = JSON.parse(e.target.result);
        if (imported && typeof imported === 'object') {
          this.state = { ...this.getDefaultState(), ...imported };
          this.saveState();
          this.initTheme();
          this.render();
          this.showToast('Backup restored successfully! 🎉', 'success');
        } else {
          throw new Error('Invalid format');
        }
      } catch (err) {
        console.error(err);
        this.showToast('Error importing file: Invalid JSON format', 'error');
      }
    };
    reader.readAsText(file);
  }

  resetData() {
    if (confirm("Are you sure you want to reset all your progress? This cannot be undone unless you have a JSON backup.")) {
      localStorage.removeItem(this.storageKey);
      this.state = this.getDefaultState();
      this.saveState();
      this.initTheme();
      this.render();
      this.showToast('All progress reset to defaults', 'info');
    }
  }

  // ==========================================
  // TOAST NOTIFICATIONS
  // ==========================================

  showToast(message, type = 'success') {
    const container = document.getElementById('toastContainer');
    if (!container) return;

    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.textContent = message;

    container.appendChild(toast);
    setTimeout(() => {
      toast.remove();
    }, 3000);
  }
}

// Global initialization
document.addEventListener('DOMContentLoaded', () => {
  window.tracker = new StudyTrackerApp();
});
