-- =============================================================================
-- V2__seed_roadmap_template.sql
-- Baseline Seed Data for 52-Week Career Plan Template
-- =============================================================================

INSERT INTO roadmaps (owner_id, title, start_date, end_date, target_role, target_dsa_min, target_dsa_max, target_weekly_hours, is_template, is_active)
VALUES (NULL, 'AI-Ready TCS-to-Product-Company Career Plan (2026-2027)', '2026-11-02', '2027-10-31', 'AI-augmented Software Engineer -> Secure Product Engineer', 120, 160, 14.0, TRUE, TRUE);

-- Seed 7 Phases (A-G)
INSERT INTO phases (roadmap_id, code, title, description, start_week, end_week, color, icon, display_order)
VALUES (1, 'A', 'Foundations & AI Fluency', 'Establish safe AI-assisted coding workflow, master fundamental DSA and core Java/OOP.', 1, 8, '#3b82f6', '🌱', 1);
INSERT INTO phases (roadmap_id, code, title, description, start_week, end_week, color, icon, display_order)
VALUES (1, 'B', 'Deeper Programming & Databases', 'Recursion, trees, graphs, Spring Boot dependency injection, SQL joins and indexing.', 9, 16, '#8b5cf6', '🌲', 2);
INSERT INTO phases (roadmap_id, code, title, description, start_week, end_week, color, icon, display_order)
VALUES (1, 'C', 'Backend/Full-Stack Confidence', 'REST APIs, JPA/Hibernate, Flutter UI integration, and first LLM/RAG exercises.', 17, 24, '#f59e0b', '⚡', 3);
INSERT INTO phases (roadmap_id, code, title, description, start_week, end_week, color, icon, display_order)
VALUES (1, 'D', 'Flagship AI Portfolio Project', 'Build ''Secure Credit Workflow Copilot'' with Java, Spring Boot, Flutter, RAG and RBAC.', 25, 32, '#22c55e', '🚀', 4);
INSERT INTO phases (roadmap_id, code, title, description, start_week, end_week, color, icon, display_order)
VALUES (1, 'E', 'Security, DevSecOps & Reliability', 'OWASP GenAI Top 10, prompt injection defence, SAST, Docker, CI/CD and LLD basics.', 33, 40, '#ef4444', '🛡️', 5);
INSERT INTO phases (roadmap_id, code, title, description, start_week, end_week, color, icon, display_order)
VALUES (1, 'F', 'Product-Company Applications', 'Resume crafting, targeted referrals, mock interviews, system design and pipeline management.', 41, 48, '#06b6d4', '🎯', 6);
INSERT INTO phases (roadmap_id, code, title, description, start_week, end_week, color, icon, display_order)
VALUES (1, 'G', 'Offer Strategy & Next Steps', 'Company-specific rounds, offer comparisons, 90-day notice handling and career transition.', 49, 52, '#ec4899', '🏆', 7);

-- Seed 52 Weeks & Tasks
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 1, '2026-11-02', '2026-11-08', 1, 'Assess Java skills, Big-O and arrays', 'Establish a safe AI-assisted coding workflow and baseline test', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (1, NULL, 'Review core Java assessment & memory model', 'Core curriculum task for Week 1', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (1, NULL, 'Study Big-O time and space complexity', 'Core curriculum task for Week 1', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (1, NULL, 'Solve 5 array problems on LeetCode', 'Core curriculum task for Week 1', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (1, NULL, 'Configure IDE AI assistant workflow (prompting, analysis before code)', 'Core curriculum task for Week 1', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (1, NULL, 'Establish baseline coding test without AI assist', 'Core curriculum task for Week 1', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 2, '2026-11-09', '2026-11-15', 1, 'Strings and Java collections framework', 'Ask an AI assistant for explanations and test ideas; verify independently', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (2, NULL, 'Deep dive into String, StringBuilder and immutability', 'Core curriculum task for Week 2', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (2, NULL, 'Master List, ArrayList, and LinkedList internals', 'Core curriculum task for Week 2', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (2, NULL, 'Solve 5 LeetCode String problems', 'Core curriculum task for Week 2', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (2, NULL, 'Prompt AI for unit test edge cases on string manipulation', 'Core curriculum task for Week 2', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (2, NULL, 'Validate AI-suggested test cases manually against requirements', 'Core curriculum task for Week 2', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 3, '2026-11-16', '2026-11-22', 1, 'Hash maps, sets and frequency counts', 'Review a small code change with AI, then validate the tests yourself', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (3, NULL, 'Internal working of HashMap (buckets, hashing, load factor, collisions)', 'Core curriculum task for Week 3', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (3, NULL, 'Solve 5 HashMap / HashSet problems (e.g., Two Sum, Group Anagrams)', 'Core curriculum task for Week 3', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (3, NULL, 'Review a refactored utility method with AI assistant', 'Core curriculum task for Week 3', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (3, NULL, 'Write JUnit tests to catch regressions in the refactored code', 'Core curriculum task for Week 3', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (3, NULL, 'Document lessons learned on hash collision handling', 'Core curriculum task for Week 3', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 4, '2026-11-23', '2026-11-29', 1, 'Two pointers and sorting algorithms', 'Record where AI explanations or generated code were incorrect', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (4, NULL, 'Implement Two-Pointer patterns (opposite ends, same direction)', 'Core curriculum task for Week 4', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (4, NULL, 'Revise Quicksort, Mergesort, and Dual-Pivot Quicksort in Java', 'Core curriculum task for Week 4', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (4, NULL, 'Solve 5 two-pointer problems', 'Core curriculum task for Week 4', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (4, NULL, 'Challenge AI with complex sorting edge cases and record errors', 'Core curriculum task for Week 4', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (4, NULL, 'Log incorrect AI assumptions in personal study journal', 'Core curriculum task for Week 4', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 5, '2026-11-30', '2026-12-06', 1, 'Sliding window patterns', 'Practise writing acceptance criteria before requesting code', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (5, NULL, 'Study Fixed and Dynamic Sliding Window algorithms', 'Core curriculum task for Week 5', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (5, NULL, 'Solve 5 sliding window problems', 'Core curriculum task for Week 5', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (5, NULL, 'Draft explicit acceptance criteria & constraints before prompting AI', 'Core curriculum task for Week 5', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (5, NULL, 'Compare solution against acceptance criteria and verify complexity', 'Core curriculum task for Week 5', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (5, NULL, 'Review Java 21 feature highlights (pattern matching, records)', 'Core curriculum task for Week 5', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 6, '2026-12-07', '2026-12-13', 1, 'Binary search and search spaces', 'Use AI to generate edge cases and inspect missing cases', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (6, NULL, 'Master classic binary search and search-on-answer patterns', 'Core curriculum task for Week 6', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (6, NULL, 'Solve 5 binary search problems', 'Core curriculum task for Week 6', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (6, NULL, 'Use AI to brainstorm edge cases (empty array, single element, duplicates)', 'Core curriculum task for Week 6', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (6, NULL, 'Identify gaps where AI missed critical corner conditions', 'Core curriculum task for Week 6', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (6, NULL, 'Practice explaining binary search loop invariants out loud', 'Core curriculum task for Week 6', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 7, '2026-12-14', '2026-12-20', 1, 'Linked lists, pointers and Java exceptions', 'Trace an unfamiliar Java defect through reproduction, fix and test', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (7, NULL, 'Singly & doubly linked list operations (reversal, cycle detection)', 'Core curriculum task for Week 7', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (7, NULL, 'Solve 5 linked list problems', 'Core curriculum task for Week 7', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (7, NULL, 'Deep dive into Java Exception hierarchy & best practices', 'Core curriculum task for Week 7', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (7, NULL, 'Reproduce and trace an unfamiliar defect using debugger', 'Core curriculum task for Week 7', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (7, NULL, 'Write a regression test verifying the bug fix', 'Core curriculum task for Week 7', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 8, '2026-12-21', '2026-12-27', 1, 'Stacks, queues and monotonic structures', 'Build a small REST API and review it with AI without accepting unverified patches', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (8, NULL, 'Master stack applications and Monotonic Stack pattern', 'Core curriculum task for Week 8', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (8, NULL, 'Solve 5 stack/queue problems', 'Core curriculum task for Week 8', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (8, NULL, 'Scaffold a minimal Spring Boot REST API for task management', 'Core curriculum task for Week 8', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (8, NULL, 'Review API design and error handling using AI assistant', 'Core curriculum task for Week 8', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (8, NULL, 'Milestone Checkpoint 1: Complete Foundations & AI Fluency Review', 'Core curriculum task for Week 8', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 9, '2026-12-28', '2027-01-03', 2, 'Recursion, backtracking and state spaces', 'Use AI to compare two algorithms; explain the trade-offs yourself', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (9, NULL, 'Understand call stack, base cases and recurrence relations', 'Core curriculum task for Week 9', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (9, NULL, 'Solve 4 backtracking problems (Subsets, Permutations)', 'Core curriculum task for Week 9', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (9, NULL, 'Ask AI to contrast iterative vs recursive approaches for state exploration', 'Core curriculum task for Week 9', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (9, NULL, 'Write personal explanation of time/space trade-offs', 'Core curriculum task for Week 9', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (9, NULL, 'Explore Java Virtual Threads (Project Loom) basics', 'Core curriculum task for Week 9', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 10, '2027-01-04', '2027-01-10', 2, 'Binary trees and tree traversals', 'Use AI to generate unit tests for tree methods', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (10, NULL, 'Implement In-order, Pre-order, Post-order & Level-order traversals', 'Core curriculum task for Week 10', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (10, NULL, 'Solve 5 binary tree problems', 'Core curriculum task for Week 10', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (10, NULL, 'Generate JUnit test suites for tree serialization with AI', 'Core curriculum task for Week 10', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (10, NULL, 'Refactor generated tests for readability and boundary conditions', 'Core curriculum task for Week 10', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (10, NULL, 'Spring Boot: Deep dive into @Configuration and Bean lifecycle', 'Core curriculum task for Week 10', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 11, '2027-01-11', '2027-01-17', 2, 'BSTs, heaps and priority queues', 'Review the generated tests and add missing edge cases', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (11, NULL, 'Master Binary Search Tree invariants and Top-K heap pattern', 'Core curriculum task for Week 11', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (11, NULL, 'Solve 5 BST & PriorityQueue problems', 'Core curriculum task for Week 11', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (11, NULL, 'Evaluate AI-generated edge cases for heap rebalancing', 'Core curriculum task for Week 11', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (11, NULL, 'Add tests for duplicate elements and capacity limits', 'Core curriculum task for Week 11', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (11, NULL, 'Review Spring Boot starter dependencies and auto-configuration', 'Core curriculum task for Week 11', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 12, '2027-01-18', '2027-01-24', 2, 'Java dependency injection and mock testing', 'Set up service-level tests with JUnit 5 and Mockito', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (12, NULL, 'Master Spring Core DI: Constructor injection, Qualifier, Scopes', 'Core curriculum task for Week 12', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (12, NULL, 'Write unit tests using JUnit 5, Mockito and AssertJ', 'Core curriculum task for Week 12', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (12, NULL, 'Mock external dependencies and database repositories cleanly', 'Core curriculum task for Week 12', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (12, NULL, 'Solve 3 medium tree/heap problems', 'Core curriculum task for Week 12', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (12, NULL, 'Audit test suite code coverage with JaCoCo', 'Core curriculum task for Week 12', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 13, '2027-01-25', '2027-01-31', 2, 'Tree patterns and HTTP protocol fundamentals', 'Learn to explain API request/response flows accurately', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (13, NULL, 'Solve 4 tree path & diameter problems', 'Core curriculum task for Week 13', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (13, NULL, 'Study HTTP/1.1 vs HTTP/2, status codes, headers and idempotent methods', 'Core curriculum task for Week 13', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (13, NULL, 'Diagram request-to-response lifecycle through Spring DispatcherServlet', 'Core curriculum task for Week 13', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (13, NULL, 'Practice explaining an API transaction flow concisely', 'Core curriculum task for Week 13', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (13, NULL, 'Implement custom Spring HTTP exception handlers (@ControllerAdvice)', 'Core curriculum task for Week 13', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 14, '2027-02-01', '2027-02-07', 2, 'SQL joins, indexing and transactions', 'Ask AI to critique a database schema and validate its suggestions', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (14, NULL, 'Practice complex SQL joins, aggregations and window functions', 'Core curriculum task for Week 14', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (14, NULL, 'Study B-Tree indexes, composite indexes and EXPLAIN query plans', 'Core curriculum task for Week 14', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (14, NULL, 'Design schema for a loan application model; ask AI for feedback', 'Core curriculum task for Week 14', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (14, NULL, 'Validate AI normalization and indexing suggestions against real query load', 'Core curriculum task for Week 14', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (14, NULL, 'Solve 3 interval/prefix sum DSA questions', 'Core curriculum task for Week 14', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 15, '2027-02-08', '2027-02-14', 2, 'Graphs: BFS and DFS algorithms', 'Compare algorithm complexity and implement both yourself', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (15, NULL, 'Graph representations: Adjacency List vs Matrix', 'Core curriculum task for Week 15', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (15, NULL, 'Implement Breadth-First Search and Depth-First Search from scratch', 'Core curriculum task for Week 15', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (15, NULL, 'Solve 4 graph traversal problems', 'Core curriculum task for Week 15', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (15, NULL, 'Contrast BFS vs DFS for shortest path in unweighted graphs with AI', 'Core curriculum task for Week 15', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (15, NULL, 'Verify time/space complexity analysis independently', 'Core curriculum task for Week 15', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 16, '2027-02-15', '2027-02-21', 2, 'Mixed DSA assessment and Spring Boot architecture', 'Use AI for a mock interview, then score your answers honestly', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (16, NULL, 'Complete a timed 3-problem mixed DSA assessment', 'Core curriculum task for Week 16', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (16, NULL, 'Conduct an AI-driven technical mock interview on Spring & DBs', 'Core curriculum task for Week 16', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (16, NULL, 'Score answers objectively and highlight weak knowledge areas', 'Core curriculum task for Week 16', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (16, NULL, 'Review Spring Data JPA repositories & query methods', 'Core curriculum task for Week 16', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (16, NULL, 'Milestone Checkpoint 2: Core Programming & DB Milestone Review', 'Core curriculum task for Week 16', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 17, '2027-02-22', '2027-02-28', 3, 'REST API design, DTOs and validation', 'Build proper request validation and error handling', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (17, NULL, 'Design RESTful endpoints following OpenAPI / Swagger standards', 'Core curriculum task for Week 17', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (17, NULL, 'Implement Jakarta Bean Validation (@NotNull, @Valid, custom validators)', 'Core curriculum task for Week 17', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (17, NULL, 'Standardize error response payload with ProblemDetails (RFC 7807)', 'Core curriculum task for Week 17', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (17, NULL, 'Solve 3 graph connectivity problems (Number of Islands, etc.)', 'Core curriculum task for Week 17', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (17, NULL, 'Document API contract for credit application submission', 'Core curriculum task for Week 17', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 18, '2027-03-01', '2027-03-07', 3, 'JPA/Hibernate entities and relationships', 'Use AI to compare persistence approaches; test the chosen implementation', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (18, NULL, 'Map @OneToMany, @ManyToOne, and @ManyToMany relationships correctly', 'Core curriculum task for Week 18', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (18, NULL, 'Understand Hibernate N+1 problem and fix with JOIN FETCH', 'Core curriculum task for Week 18', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (18, NULL, 'Ask AI to compare JPA vs jOOQ vs JDBC for high-throughput banking', 'Core curriculum task for Week 18', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (18, NULL, 'Write repository integration tests with @DataJpaTest and Testcontainers', 'Core curriculum task for Week 18', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (18, NULL, 'Solve 3 topological sort / cycle detection graph problems', 'Core curriculum task for Week 18', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 19, '2027-03-08', '2027-03-14', 3, 'Transactions, concurrency and idempotency', 'Test duplicate requests and failure scenarios', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (19, NULL, 'Study Spring @Transactional propagation, isolation levels and rollback rules', 'Core curriculum task for Week 19', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (19, NULL, 'Implement Idempotency-Key handling for financial API operations', 'Core curriculum task for Week 19', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (19, NULL, 'Write concurrent integration tests simulating race conditions', 'Core curriculum task for Week 19', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (19, NULL, 'Solve 3 Dynamic Programming intro problems', 'Core curriculum task for Week 19', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (19, NULL, 'Review pessimistic vs optimistic locking in JPA (@Version)', 'Core curriculum task for Week 19', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 20, '2027-03-15', '2027-03-21', 3, 'Flutter architecture and API integration', 'Improve UI error handling and make loading states reliable', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (20, NULL, 'Review Flutter state management (Bloc / Riverpod / Provider)', 'Core curriculum task for Week 20', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (20, NULL, 'Connect Flutter mobile client to Spring Boot REST endpoints', 'Core curriculum task for Week 20', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (20, NULL, 'Implement robust offline, loading, and error states in UI', 'Core curriculum task for Week 20', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (20, NULL, 'Solve 3 Dynamic Programming memoization problems', 'Core curriculum task for Week 20', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (20, NULL, 'Review mobile security: secure token storage on Android/iOS', 'Core curriculum task for Week 20', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 21, '2027-03-22', '2027-03-28', 3, 'LLM concepts, tokens, context and model APIs', 'Make a minimal, isolated AI API integration in a learning project', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (21, NULL, 'Learn tokenization, context windows, temperature and top-p sampling', 'Core curriculum task for Week 21', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (21, NULL, 'Explore Spring AI starter dependencies and ChatClient API', 'Core curriculum task for Week 21', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (21, NULL, 'Build a lightweight standalone CLI calling OpenAI / Gemini model', 'Core curriculum task for Week 21', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (21, NULL, 'Protect API keys using environment variables and secrets manager', 'Core curriculum task for Week 21', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (21, NULL, 'Solve 3 DP tabulation problems', 'Core curriculum task for Week 21', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 22, '2027-03-29', '2027-04-04', 3, 'Structured outputs, timeouts and retries', 'Validate model outputs against a schema and handle failure safely', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (22, NULL, 'Configure Spring AI BeanOutputConverter / JSON Schema validation', 'Core curriculum task for Week 22', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (22, NULL, 'Implement client timeouts, exponential backoff and circuit breaker with Resilience4j', 'Core curriculum task for Week 22', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (22, NULL, 'Handle malformed or hallucinated LLM responses gracefully', 'Core curriculum task for Week 22', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (22, NULL, 'Solve 3 greedy algorithms problems', 'Core curriculum task for Week 22', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (22, NULL, 'Calculate token costs and design usage tracking', 'Core curriculum task for Week 22', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 23, '2027-04-05', '2027-04-11', 3, 'Embeddings and semantic vector search', 'Complete an introductory RAG learning exercise', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (23, NULL, 'Study dense embeddings, cosine similarity and vector spaces', 'Core curriculum task for Week 23', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (23, NULL, 'Set up pgvector in PostgreSQL via Docker', 'Core curriculum task for Week 23', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (23, NULL, 'Generate embeddings for sample banking documents using Spring AI', 'Core curriculum task for Week 23', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (23, NULL, 'Perform similarity search queries with metadata filters', 'Core curriculum task for Week 23', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (23, NULL, 'Solve 3 binary tree revision questions', 'Core curriculum task for Week 23', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 24, '2027-04-12', '2027-04-18', 3, 'RAG architecture and evaluation design', 'Design the data flow for a secure AI-enabled portfolio application', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (24, NULL, 'Architecture blueprint: Ingestion -> Chunking -> Embeddings -> Retrieval -> Augmentation', 'Core curriculum task for Week 24', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (24, NULL, 'Design access control boundaries so user retrieval obeys roles', 'Core curriculum task for Week 24', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (24, NULL, 'Draft system architecture diagram for ''Secure Credit Workflow Copilot''', 'Core curriculum task for Week 24', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (24, NULL, 'Solve 3 graph revision problems', 'Core curriculum task for Week 24', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (24, NULL, 'Milestone Checkpoint 3: Backend & LLM Experimentation Review', 'Core curriculum task for Week 24', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 25, '2027-04-19', '2027-04-25', 4, 'Flagship: Java/Spring Boot API and synthetic workflows', 'Build the Java/Spring Boot API, data model and synthetic workflow records', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (25, NULL, 'Initialize ''Secure Credit Workflow Copilot'' repository', 'Core curriculum task for Week 25', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (25, NULL, 'Create JPA entities: LoanApplication, Document, WorkflowStep, AuditLog', 'Core curriculum task for Week 25', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (25, NULL, 'Generate 20+ realistic synthetic credit case files (zero sensitive data)', 'Core curriculum task for Week 25', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (25, NULL, 'Write comprehensive CRUD unit and integration tests', 'Core curriculum task for Week 25', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (25, NULL, 'Solve 3 medium LeetCode DSA questions', 'Core curriculum task for Week 25', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 26, '2027-04-26', '2027-05-02', 4, 'Flagship: Document chunking and vector ingestion', 'Create document ingestion and retrieval for a controlled set of fictional documents', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (26, NULL, 'Implement smart document chunking strategy (sentence/paragraph window)', 'Core curriculum task for Week 26', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (26, NULL, 'Store chunk embeddings in pgvector with metadata tags', 'Core curriculum task for Week 26', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (26, NULL, 'Build IngestionPipeline service with progress updates', 'Core curriculum task for Week 26', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (26, NULL, 'Write tests verifying chunk retrieval accuracy for sample queries', 'Core curriculum task for Week 26', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (26, NULL, 'Solve 3 sliding window revision questions', 'Core curriculum task for Week 26', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 27, '2027-05-03', '2027-05-09', 4, 'Flagship: LLM integration and grounded question-answering', 'Connect an LLM and generate answers grounded in retrieved material', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (27, NULL, 'Implement RetrievalAugmentedGeneration service using Spring AI', 'Core curriculum task for Week 27', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (27, NULL, 'Craft system prompt enforcing answers strictly based on retrieved context', 'Core curriculum task for Week 27', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (27, NULL, 'Handle empty retrieval results with polite fallback messaging', 'Core curriculum task for Week 27', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (27, NULL, 'Add latency logging and request-response audit tracking', 'Core curriculum task for Week 27', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (27, NULL, 'Solve 3 binary search revision questions', 'Core curriculum task for Week 27', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 28, '2027-05-10', '2027-05-16', 4, 'Flagship: Citations and verifiable source inspection', 'Add source snippets or citations so users can inspect supporting material', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (28, NULL, 'Return citation metadata (documentId, page, chunkSnippet) with every response', 'Core curriculum task for Week 28', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (28, NULL, 'Design response DTO with answerText + List<Citation>', 'Core curriculum task for Week 28', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (28, NULL, 'Write unit tests checking citation mapping fidelity', 'Core curriculum task for Week 28', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (28, NULL, 'Perform manual verification on 10 synthetic credit case inquiries', 'Core curriculum task for Week 28', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (28, NULL, 'Solve 3 linked list / stack problems', 'Core curriculum task for Week 28', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 29, '2027-05-17', '2027-05-23', 4, 'Flagship: Flutter frontend interface', 'Add Flutter screens for questions, answers, loading states and errors', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (29, NULL, 'Build clean chat & document inspector interface in Flutter', 'Core curriculum task for Week 29', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (29, NULL, 'Implement expandable source citation cards in the UI', 'Core curriculum task for Week 29', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (29, NULL, 'Add animated loading indicators, streaming response support, and error toasts', 'Core curriculum task for Week 29', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (29, NULL, 'Connect Flutter app to local Spring Boot backend seamlessly', 'Core curriculum task for Week 29', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (29, NULL, 'Solve 3 two-pointer revision questions', 'Core curriculum task for Week 29', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 30, '2027-05-24', '2027-05-30', 4, 'Flagship: Authentication and Role-Based Access Control', 'Implement backend authentication and authorization; restrict document retrieval by user permissions', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (30, NULL, 'Implement Spring Security with JWT authentication', 'Core curriculum task for Week 30', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (30, NULL, 'Define roles: ROLE_ANALYST, ROLE_MANAGER, ROLE_AUDITOR', 'Core curriculum task for Week 30', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (30, NULL, 'Enforce document retrieval filters based on user clearance level in SQL/Vector query', 'Core curriculum task for Week 30', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (30, NULL, 'Write security integration tests verifying unauthorized access rejection', 'Core curriculum task for Week 30', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (30, NULL, 'Solve 3 sorting & searching questions', 'Core curriculum task for Week 30', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 31, '2027-05-31', '2027-06-06', 4, 'Flagship: Automated RAG evaluation dataset', 'Create a repeatable evaluation dataset and test answer relevance, factual grounding and failure handling', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (31, NULL, 'Create golden test dataset of 25 question-context-answer triplets', 'Core curriculum task for Week 31', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (31, NULL, 'Implement automated scoring: Context Relevance, Groundedness, Answer Correctness', 'Core curriculum task for Week 31', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (31, NULL, 'Measure and report hallucination rate across automated regression suite', 'Core curriculum task for Week 31', 'AI_APP_ENGINEERING', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (31, NULL, 'Document evaluation methodology in project documentation', 'Core curriculum task for Week 31', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (31, NULL, 'Solve 3 tree traversal problems', 'Core curriculum task for Week 31', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 32, '2027-06-07', '2027-06-13', 4, 'Flagship: Tests, CI pipeline, architecture docs and demo', 'Add automated tests, a README, architecture diagram and a demonstration video or screenshots', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (32, NULL, 'Achieve >80% test coverage across backend services', 'Core curriculum task for Week 32', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (32, NULL, 'Create clear architecture diagrams (C4 model / data flow)', 'Core curriculum task for Week 32', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (32, NULL, 'Write exemplary README with setup instructions and architectural trade-offs', 'Core curriculum task for Week 32', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (32, NULL, 'Record a 3-minute video walkthrough showcasing RAG & security controls', 'Core curriculum task for Week 32', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (32, NULL, 'Milestone Checkpoint 4: Flagship Portfolio Project Complete', 'Core curriculum task for Week 32', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 33, '2027-06-14', '2027-06-20', 5, 'AI Security Risks & OWASP GenAI Top 10', 'Study the OWASP GenAI Top 10 for 2026', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (33, NULL, 'Study OWASP GenAI 2026: Prompt Injection, Sensitive Info Disclosure, Excessive Agency', 'Core curriculum task for Week 33', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (33, NULL, 'Analyze vulnerabilities in RAG architectures (context poisoning, vector hijacking)', 'Core curriculum task for Week 33', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (33, NULL, 'Draft threat model for the credit copilot project', 'Core curriculum task for Week 33', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (33, NULL, 'Solve 3 medium DSA questions', 'Core curriculum task for Week 33', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (33, NULL, 'Share security insights in study journal', 'Core curriculum task for Week 33', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 34, '2027-06-21', '2027-06-27', 5, 'Prompt injection and data leakage defence', 'Create safe adversarial test cases for the project', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (34, NULL, 'Develop 15 safe adversarial prompts (jailbreaks, context leakage attempts)', 'Core curriculum task for Week 34', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (34, NULL, 'Implement system prompt hardening and input delimiter separation', 'Core curriculum task for Week 34', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (34, NULL, 'Test output guardrails to prevent PII / sensitive internal token leakage', 'Core curriculum task for Week 34', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (34, NULL, 'Verify all adversarial tests are caught and handled gracefully', 'Core curriculum task for Week 34', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (34, NULL, 'Solve 3 graph shortest-path problems', 'Core curriculum task for Week 34', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 35, '2027-06-28', '2027-07-04', 5, 'Output validation and authorization boundaries', 'Ensure an AI response cannot bypass backend access controls', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (35, NULL, 'Enforce strict JSON schema validation on all model responses', 'Core curriculum task for Week 35', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (35, NULL, 'Verify model cannot trigger unauthorized tool actions or elevate privileges', 'Core curriculum task for Week 35', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (35, NULL, 'Implement immutable audit trail for all AI-assisted queries and user IDs', 'Core curriculum task for Week 35', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (35, NULL, 'Solve 3 dynamic programming questions', 'Core curriculum task for Week 35', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (35, NULL, 'Conduct code review on permission boundaries', 'Core curriculum task for Week 35', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 36, '2027-07-05', '2027-07-11', 5, 'Cost controls, rate limiting and abuse prevention', 'Add limits, timeouts, error handling and suitable logging', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (36, NULL, 'Implement Bucket4j rate limiting per IP and user account', 'Core curriculum task for Week 36', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (36, NULL, 'Set token consumption quotas and per-request hard timeout budgets', 'Core curriculum task for Week 36', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (36, NULL, 'Configure structured JSON logging with correlation IDs (Logback/MDC)', 'Core curriculum task for Week 36', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (36, NULL, 'Simulate high-concurrency burst traffic with Apache Benchmark or k6', 'Core curriculum task for Week 36', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (36, NULL, 'Solve 3 mixed DSA problems', 'Core curriculum task for Week 36', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 37, '2027-07-12', '2027-07-18', 5, 'Static analysis and dependency scanning (DevSecOps)', 'Add static analysis and dependency checks to CI', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (37, NULL, 'Integrate SpotBugs / SonarQube rules for Java in build tool', 'Core curriculum task for Week 37', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (37, NULL, 'Add OWASP Dependency-Check / Snyk to flag vulnerable third-party libraries', 'Core curriculum task for Week 37', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (37, NULL, 'Fix all critical and high vulnerability alerts in codebase', 'Core curriculum task for Week 37', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (37, NULL, 'Configure GitHub Actions workflow running checks on pull request', 'Core curriculum task for Week 37', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (37, NULL, 'Solve 3 heap / priority queue questions', 'Core curriculum task for Week 37', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 38, '2027-07-19', '2027-07-25', 5, 'Docker containerization and delivery pipeline', 'Make the project reproducible from a clean environment', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (38, NULL, 'Write multi-stage Dockerfile for Spring Boot (distroless/Alpine base)', 'Core curriculum task for Week 38', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (38, NULL, 'Write Docker Compose orchestrating backend, postgres/pgvector, and redis', 'Core curriculum task for Week 38', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (38, NULL, 'Verify complete project boots and passes tests with `docker compose up`', 'Core curriculum task for Week 38', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (38, NULL, 'Set up CI workflow running unit + integration tests in containers', 'Core curriculum task for Week 38', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (38, NULL, 'Solve 3 binary tree revision questions', 'Core curriculum task for Week 38', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 39, '2027-07-26', '2027-08-01', 5, 'Low-Level Design (LLD) & System Design fundamentals', 'Practise explaining design decisions and failure scenarios', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (39, NULL, 'Practice LLD: Rate Limiter, Notification Service, Cache (LRU)', 'Core curriculum task for Week 39', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (39, NULL, 'Study System Design basics: Caching strategies (Redis), CDN, Sharding', 'Core curriculum task for Week 39', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (39, NULL, 'Draft system design write-up for scalable RAG pipeline', 'Core curriculum task for Week 39', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (39, NULL, 'Practice whiteboard explanation of CAP theorem and distributed consensus', 'Core curriculum task for Week 39', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (39, NULL, 'Solve 3 intervals / greedy DSA problems', 'Core curriculum task for Week 39', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 40, '2027-08-02', '2027-08-08', 5, 'Comprehensive project and technical review', 'Conduct a timed coding assessment and a project walkthrough', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (40, NULL, 'Complete timed 90-minute DSA coding assessment', 'Core curriculum task for Week 40', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (40, NULL, 'Perform 45-minute mock project walkthrough addressing failure modes', 'Core curriculum task for Week 40', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (40, NULL, 'Audit resume bullet points against completed project deliverables', 'Core curriculum task for Week 40', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (40, NULL, 'Refactor minor code smells in copilot repository', 'Core curriculum task for Week 40', 'PORTFOLIO_PROJECT', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (40, NULL, 'Milestone Checkpoint 5: Security & Reliability Review', 'Core curriculum task for Week 40', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 41, '2027-08-09', '2027-08-15', 6, 'Resume crafting and LinkedIn profile optimization', 'Emphasize real TCS work, measurable contributions and demonstrable projects', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (41, NULL, 'Rewrite resume highlighting TCS WCAS defect resolution & architecture insights', 'Core curriculum task for Week 41', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (41, NULL, 'Detail ''Secure Credit Workflow Copilot'' with quantifiable metrics (RAG, 85% test cov)', 'Core curriculum task for Week 41', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (41, NULL, 'Optimize LinkedIn headline: ''Backend Engineer | Java · Spring Boot · RAG & AI Engineering''', 'Core curriculum task for Week 41', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (41, NULL, 'Seek peer/mentor feedback on resume clarity and impact verbs', 'Core curriculum task for Week 41', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (41, NULL, 'Solve 3 high-frequency product-company DSA questions', 'Core curriculum task for Week 41', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 42, '2027-08-16', '2027-08-22', 6, 'DSA pattern revision and timed coding rounds', 'Revisit weak patterns and practise timed coding', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (42, NULL, 'Revisit weak DSA patterns (DP, Graph BFS/DFS, Monotonic Stack)', 'Core curriculum task for Week 42', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (42, NULL, 'Complete 3 timed 45-minute LeetCode contest rounds', 'Core curriculum task for Week 42', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (42, NULL, 'Review NeetCode 150 top frequency list', 'Core curriculum task for Week 42', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (42, NULL, 'Practice explaining algorithmic logic out loud while coding', 'Core curriculum task for Week 42', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (42, NULL, 'Review Java Collections internal implementation details', 'Core curriculum task for Week 42', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 43, '2027-08-23', '2027-08-29', 6, 'Java, Spring Boot & Concurrency interview mastery', 'Practise technical questions and debugging walkthroughs', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (43, NULL, 'Deep dive into Java Memory Model, Garbage Collection (G1/ZGC), and Concurrency', 'Core curriculum task for Week 43', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (43, NULL, 'Review Spring Boot internals: AOP, BeanPostProcessor, Transaction mechanics', 'Core curriculum task for Week 43', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (43, NULL, 'Prepare 5 real defect debugging narratives from TCS WCAS experience', 'Core curriculum task for Week 43', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (43, NULL, 'Mock interview: 1-hour Java technical grilling', 'Core curriculum task for Week 43', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (43, NULL, 'Solve 3 tree / graph interview problems', 'Core curriculum task for Week 43', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 44, '2027-08-30', '2027-09-05', 6, 'Project deep dive and LLD interview readiness', 'Explain architecture, trade-offs and how the AI evaluation works', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (44, NULL, 'Prepare deep-dive slides/notes on RAG architecture and vector search trade-offs', 'Core curriculum task for Week 44', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (44, NULL, 'Practice defending security decisions: Why RBAC in vector query? Why not LLM-only check?', 'Core curriculum task for Week 44', 'APPLICATION_SECURITY', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (44, NULL, 'Practice LLD problem: Design an Idempotent Payment/Workflow API', 'Core curriculum task for Week 44', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (44, NULL, 'Solve 3 medium DSA questions', 'Core curriculum task for Week 44', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (44, NULL, 'Refine 2-minute project pitch for hiring managers', 'Core curriculum task for Week 44', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 45, '2027-09-06', '2027-09-12', 6, 'Start strategic product-company applications', 'Apply to well-matched roles and seek relevant referrals', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (45, NULL, 'Target tier-1/tier-2 product companies (Amazon, Microsoft, Razorpay, PhonePe, etc.)', 'Core curriculum task for Week 45', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (45, NULL, 'Reach out to 10 alumni/engineers on LinkedIn for warm referrals', 'Core curriculum task for Week 45', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (45, NULL, 'Submit 5–8 customized applications matching Java backend / product roles', 'Core curriculum task for Week 45', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (45, NULL, 'Track application statuses in job tracker table', 'Core curriculum task for Week 45', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (45, NULL, 'Maintain daily DSA streak (1 problem/day)', 'Core curriculum task for Week 45', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 46, '2027-09-13', '2027-09-19', 6, 'Live mock interviews and gap closure', 'Record mistakes and address the highest-impact gaps', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (46, NULL, 'Conduct 2 peer or platform mock interviews (DSA + System Design)', 'Core curriculum task for Week 46', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (46, NULL, 'Review recorded mock interviews to identify filler words and hesitation', 'Core curriculum task for Week 46', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (46, NULL, 'Strengthen weakest topics identified in feedback', 'Core curriculum task for Week 46', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (46, NULL, 'Continue weekly targeted applications (5–8 companies)', 'Core curriculum task for Week 46', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (46, NULL, 'Solve 3 high-frequency interview problems', 'Core curriculum task for Week 46', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 47, '2027-09-20', '2027-09-26', 6, 'System design and behavioral interview mastery', 'Prepare examples involving ownership, teamwork and problem solving', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (47, NULL, 'Practice System Design: Scalable URL Shortener, Rate Limiter, Web Crawler', 'Core curriculum task for Week 47', 'JAVA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (47, NULL, 'Prepare STAR-format stories for behavioral questions (conflict, failure, ownership)', 'Core curriculum task for Week 47', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (47, NULL, 'Prepare clear explanation of TCS 90-day notice period for recruiters', 'Core curriculum task for Week 47', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (47, NULL, 'Submit 5–8 new targeted job applications', 'Core curriculum task for Week 47', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (47, NULL, 'Solve 3 DSA problems', 'Core curriculum task for Week 47', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 48, '2027-09-27', '2027-10-03', 6, 'Interview pipeline review and active rounds', 'Evaluate feedback, notice-period concerns and remaining skill gaps', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (48, NULL, 'Review interview pipeline metrics and recruiter callback rates', 'Core curriculum task for Week 48', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (48, NULL, 'Refine application strategy based on market responses', 'Core curriculum task for Week 48', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (48, NULL, 'Participate in initial recruiter screens and technical rounds', 'Core curriculum task for Week 48', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (48, NULL, 'Solve 3 DSA problems', 'Core curriculum task for Week 48', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (48, NULL, 'Milestone Checkpoint 6: Interview Pipeline & Readiness Review', 'Core curriculum task for Week 48', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 49, '2027-10-04', '2027-10-10', 7, 'Company-specific interview preparation', 'Focus on the exact job description and interview format', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (49, NULL, 'Analyze specific tech stack & problem styles for upcoming interview rounds', 'Core curriculum task for Week 49', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (49, NULL, 'Practice company-tagged LeetCode questions for active interviewers', 'Core curriculum task for Week 49', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (49, NULL, 'Research interviewer backgrounds and recent company engineering blog posts', 'Core curriculum task for Week 49', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (49, NULL, 'Perform dry run of personal introduction and technical walkthrough', 'Core curriculum task for Week 49', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (49, NULL, 'Prepare insightful questions to ask the interviewers', 'Core curriculum task for Week 49', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 50, '2027-10-11', '2027-10-17', 7, 'Technical rounds and debrief iterations', 'Review every failure and strengthen weak topics', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (50, NULL, 'Attend scheduled technical coding and system design rounds', 'Core curriculum task for Week 50', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (50, NULL, 'Conduct immediate post-interview debrief: log all asked questions and hiccups', 'Core curriculum task for Week 50', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (50, NULL, 'Resolve any DSA problems that were difficult during the round within 24 hours', 'Core curriculum task for Week 50', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (50, NULL, 'Send polite thank-you / follow-up notes to recruiters', 'Core curriculum task for Week 50', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (50, NULL, 'Stay sharp with 1 medium DSA problem daily', 'Core curriculum task for Week 50', 'DSA', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 51, '2027-10-18', '2027-10-24', 7, 'Offer evaluation and compensation negotiation', 'Compare fixed compensation, equity, variable pay, role, office attendance and benefits', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (51, NULL, 'Evaluate offers against ₹30–40 LPA ambitious target', 'Core curriculum task for Week 51', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (51, NULL, 'Assess base pay vs variable vs ESOP vesting schedule', 'Core curriculum task for Week 51', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (51, NULL, 'Evaluate team engineering culture, work model (remote/hybrid), and tech stack', 'Core curriculum task for Week 51', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (51, NULL, 'Negotiate professionally using competing offers and demonstrated project depth', 'Core curriculum task for Week 51', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (51, NULL, 'Confirm all offer terms in writing before taking action', 'Core curriculum task for Week 51', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);
INSERT INTO week_plans (roadmap_id, week_number, start_date, end_date, phase_id, engineering_focus, ai_readiness_deliverable, planned_hours, status, completion_percentage)
VALUES (1, 52, '2027-10-25', '2027-10-31', 7, 'Transition decision and notice period management', 'Confirm written terms and plan your notice period—or continue preparing if no offer meets your criteria', 14.0, 'NOT_STARTED', 0);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (52, NULL, 'Formally accept winning offer with signed documentation', 'Core curriculum task for Week 52', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 1);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (52, NULL, 'Plan 90-day TCS resignation and smooth project handover', 'Core curriculum task for Week 52', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 2);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (52, NULL, 'Establish knowledge-transfer documentation for WCAS defect coverage', 'Core curriculum task for Week 52', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 3);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (52, NULL, 'Celebrate the journey: from defect fixes to secure AI-ready product engineer! 🎉', 'Core curriculum task for Week 52', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 4);
INSERT INTO study_tasks (week_id, owner_id, title, description, category, priority, estimate_minutes, actual_minutes, status, source, sort_order)
VALUES (52, NULL, 'Plan Day 1 ramp-up strategy for your new product company role', 'Core curriculum task for Week 52', 'CAREER_PREPARATION', 'MEDIUM', 60, 0, 'NOT_STARTED', 'SEEDED', 5);