-- =============================================================================
-- V1__init_schema.sql
-- Production schema for StudyTracker - 52-Week Career Plan Platform
-- =============================================================================

-- 1. Users
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(120) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    timezone VARCHAR(50) NOT NULL DEFAULT 'Asia/Kolkata',
    weekly_target_hours NUMERIC(4, 1) NOT NULL DEFAULT 14.0,
    roadmap_start_date DATE NOT NULL DEFAULT '2026-11-02',
    theme_preference VARCHAR(20) NOT NULL DEFAULT 'dark',
    role VARCHAR(30) NOT NULL DEFAULT 'ROLE_USER',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);

-- 2. Roadmaps
CREATE TABLE roadmaps (
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(150) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    target_role VARCHAR(150) NOT NULL,
    target_dsa_min INT NOT NULL DEFAULT 120,
    target_dsa_max INT NOT NULL DEFAULT 160,
    target_weekly_hours NUMERIC(4, 1) NOT NULL DEFAULT 14.0,
    is_template BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_roadmaps_owner ON roadmaps(owner_id);

-- 3. Phases
CREATE TABLE phases (
    id BIGSERIAL PRIMARY KEY,
    roadmap_id BIGINT NOT NULL REFERENCES roadmaps(id) ON DELETE CASCADE,
    code VARCHAR(5) NOT NULL,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    start_week INT NOT NULL,
    end_week INT NOT NULL,
    color VARCHAR(20) DEFAULT '#3b82f6',
    icon VARCHAR(10) DEFAULT '🌱',
    display_order INT NOT NULL
);

CREATE INDEX idx_phases_roadmap ON phases(roadmap_id);

-- 4. Week Plans
CREATE TABLE week_plans (
    id BIGSERIAL PRIMARY KEY,
    roadmap_id BIGINT NOT NULL REFERENCES roadmaps(id) ON DELETE CASCADE,
    week_number INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    phase_id BIGINT REFERENCES phases(id) ON DELETE SET NULL,
    engineering_focus VARCHAR(255) NOT NULL,
    ai_readiness_deliverable VARCHAR(255) NOT NULL,
    milestone_description TEXT,
    planned_hours NUMERIC(4, 1) NOT NULL DEFAULT 14.0,
    status VARCHAR(30) NOT NULL DEFAULT 'NOT_STARTED',
    completion_percentage INT NOT NULL DEFAULT 0 CHECK (completion_percentage >= 0 AND completion_percentage <= 100),
    CONSTRAINT uq_roadmap_week UNIQUE (roadmap_id, week_number)
);

CREATE INDEX idx_week_plans_roadmap ON week_plans(roadmap_id);
CREATE INDEX idx_week_plans_week ON week_plans(week_number);

-- 5. Study Tasks
CREATE TABLE study_tasks (
    id BIGSERIAL PRIMARY KEY,
    week_id BIGINT NOT NULL REFERENCES week_plans(id) ON DELETE CASCADE,
    owner_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(50) NOT NULL,
    priority VARCHAR(20) NOT NULL DEFAULT 'MEDIUM',
    estimate_minutes INT NOT NULL DEFAULT 60 CHECK (estimate_minutes >= 0),
    actual_minutes INT NOT NULL DEFAULT 0 CHECK (actual_minutes >= 0),
    due_date DATE,
    status VARCHAR(30) NOT NULL DEFAULT 'NOT_STARTED',
    completed_at TIMESTAMP WITH TIME ZONE,
    source VARCHAR(20) NOT NULL DEFAULT 'SEEDED',
    sort_order INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_tasks_week ON study_tasks(week_id);
CREATE INDEX idx_tasks_owner ON study_tasks(owner_id);
CREATE INDEX idx_tasks_status ON study_tasks(status);

-- 6. Study Sessions
CREATE TABLE study_sessions (
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    task_id BIGINT REFERENCES study_tasks(id) ON DELETE SET NULL,
    category VARCHAR(50) NOT NULL,
    session_date DATE NOT NULL,
    duration_minutes INT NOT NULL CHECK (duration_minutes > 0),
    summary TEXT,
    interruption_notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_sessions_owner ON study_sessions(owner_id);
CREATE INDEX idx_sessions_date ON study_sessions(session_date);

-- 7. DSA Problems
CREATE TABLE dsa_problems (
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    platform VARCHAR(50) NOT NULL DEFAULT 'LeetCode',
    url VARCHAR(500),
    topic_pattern VARCHAR(100) NOT NULL,
    difficulty VARCHAR(20) NOT NULL DEFAULT 'MEDIUM',
    date_attempted DATE NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'SOLVED',
    attempt_count INT NOT NULL DEFAULT 1 CHECK (attempt_count >= 1),
    time_taken_minutes INT CHECK (time_taken_minutes >= 0),
    hints_used INT NOT NULL DEFAULT 0 CHECK (hints_used >= 0),
    could_solve_without_ai BOOLEAN NOT NULL DEFAULT TRUE,
    complexity_explanation TEXT,
    notes TEXT,
    next_review_date DATE,
    confidence_score INT NOT NULL DEFAULT 3 CHECK (confidence_score >= 1 AND confidence_score <= 5),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_dsa_owner ON dsa_problems(owner_id);
CREATE INDEX idx_dsa_topic ON dsa_problems(topic_pattern);
CREATE INDEX idx_dsa_next_review ON dsa_problems(next_review_date);

-- 8. Weekly Reviews
CREATE TABLE weekly_reviews (
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    week_id BIGINT NOT NULL REFERENCES week_plans(id) ON DELETE CASCADE,
    what_studied TEXT,
    what_completed TEXT,
    what_ai_got_right TEXT,
    what_ai_got_wrong TEXT,
    what_verified_independently TEXT,
    wins TEXT,
    blockers TEXT,
    areas_to_improve TEXT,
    next_week_priorities TEXT,
    self_rating INT CHECK (self_rating >= 1 AND self_rating <= 5),
    total_actual_hours NUMERIC(4, 1) DEFAULT 0.0,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    finalized_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_review_owner_week UNIQUE (owner_id, week_id)
);

CREATE INDEX idx_reviews_owner ON weekly_reviews(owner_id);

-- 9. Projects
CREATE TABLE projects (
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(150) NOT NULL,
    project_type VARCHAR(50) NOT NULL DEFAULT 'Secondary',
    description TEXT,
    tech_stack VARCHAR(255),
    status VARCHAR(30) NOT NULL DEFAULT 'NOT_STARTED',
    progress_percentage INT NOT NULL DEFAULT 0 CHECK (progress_percentage >= 0 AND progress_percentage <= 100),
    repository_url VARCHAR(500),
    demo_url VARCHAR(500),
    start_date DATE,
    target_date DATE,
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_projects_owner ON projects(owner_id);

-- 10. Project Milestones
CREATE TABLE project_milestones (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    due_date DATE,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    completed_date DATE
);

CREATE INDEX idx_milestones_project ON project_milestones(project_id);

-- 11. Career Applications
CREATE TABLE career_applications (
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    company VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULL,
    job_url VARCHAR(500),
    source_referral VARCHAR(100),
    application_date DATE NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'APPLIED',
    interview_stage VARCHAR(100),
    notice_period_compatibility VARCHAR(100),
    fixed_compensation VARCHAR(50),
    variable_compensation VARCHAR(50),
    equity VARCHAR(50),
    notes TEXT,
    next_action_date DATE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_applications_owner ON career_applications(owner_id);
CREATE INDEX idx_applications_status ON career_applications(status);

-- 12. Mock Interviews
CREATE TABLE mock_interviews (
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    interview_type VARCHAR(100) NOT NULL,
    interview_date DATE NOT NULL,
    duration_minutes INT NOT NULL CHECK (duration_minutes > 0),
    score INT CHECK (score >= 0 AND score <= 100),
    strengths TEXT,
    weaknesses TEXT,
    feedback TEXT,
    next_actions TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_mock_interviews_owner ON mock_interviews(owner_id);

-- 13. Learning Resources
CREATE TABLE learning_resources (
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    url VARCHAR(500) NOT NULL,
    resource_type VARCHAR(50) NOT NULL DEFAULT 'Article',
    category VARCHAR(50) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'TO_READ',
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_resources_owner ON learning_resources(owner_id);

-- 14. Notifications
CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    due_timestamp TIMESTAMP WITH TIME ZONE,
    notification_type VARCHAR(50) NOT NULL DEFAULT 'REMINDER',
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_notifications_owner ON notifications(owner_id);

-- 15. AI Conversations & Messages
CREATE TABLE ai_conversations (
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    context_type VARCHAR(50),
    context_id BIGINT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_ai_conv_owner ON ai_conversations(owner_id);

CREATE TABLE ai_messages (
    id BIGSERIAL PRIMARY KEY,
    conversation_id BIGINT NOT NULL REFERENCES ai_conversations(id) ON DELETE CASCADE,
    role VARCHAR(20) NOT NULL,
    content TEXT NOT NULL,
    model VARCHAR(100),
    tokens_used INT DEFAULT 0,
    verification_rating VARCHAR(30) DEFAULT 'UNRATED',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_ai_msg_conv ON ai_messages(conversation_id);

-- 16. Study Documents (Knowledge Base)
CREATE TABLE study_documents (
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(100) NOT NULL,
    file_size BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_docs_owner ON study_documents(owner_id);
