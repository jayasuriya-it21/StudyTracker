import json

plan_info = {
    "title": "AI-Ready TCS-to-Product-Company Career Plan",
    "owner": "Jayasuriya",
    "startDate": "2026-11-02",
    "endDate": "2027-10-31",
    "weeklyHours": 14,
    "compensationTarget": "₹30–40 LPA",
    "targetProfile": "AI-augmented Software Engineer → Secure Product Engineer"
}

phases = [
    {
        "id": "A",
        "letter": "A",
        "name": "Foundations & AI Fluency",
        "weeks": [1, 8],
        "period": "Nov–Dec 2026",
        "color": "#3b82f6",
        "icon": "🌱",
        "description": "Establish safe AI-assisted coding workflow, master fundamental DSA and core Java/OOP."
    },
    {
        "id": "B",
        "letter": "B",
        "name": "Deeper Programming & Databases",
        "weeks": [9, 16],
        "period": "Dec 2026–Feb 2027",
        "color": "#8b5cf6",
        "icon": "🌲",
        "description": "Recursion, trees, graphs, Spring Boot dependency injection, SQL joins and indexing."
    },
    {
        "id": "C",
        "letter": "C",
        "name": "Backend/Full-Stack Confidence",
        "weeks": [17, 24],
        "period": "Feb–Apr 2027",
        "color": "#f59e0b",
        "icon": "⚡",
        "description": "REST APIs, JPA/Hibernate, Flutter UI integration, and first LLM/RAG exercises."
    },
    {
        "id": "D",
        "letter": "D",
        "name": "Flagship AI Portfolio Project",
        "weeks": [25, 32],
        "period": "Apr–Jun 2027",
        "color": "#22c55e",
        "icon": "🚀",
        "description": "Build 'Secure Credit Workflow Copilot' with Java, Spring Boot, Flutter, RAG and RBAC."
    },
    {
        "id": "E",
        "letter": "E",
        "name": "Security, DevSecOps & Reliability",
        "weeks": [33, 40],
        "period": "Jun–Aug 2027",
        "color": "#ef4444",
        "icon": "🛡️",
        "description": "OWASP GenAI Top 10, prompt injection defence, SAST, Docker, CI/CD and LLD basics."
    },
    {
        "id": "F",
        "letter": "F",
        "name": "Product-Company Applications",
        "weeks": [41, 48],
        "period": "Aug–Oct 2027",
        "color": "#06b6d4",
        "icon": "🎯",
        "description": "Resume crafting, targeted referrals, mock interviews, system design and pipeline management."
    },
    {
        "id": "G",
        "letter": "G",
        "name": "Offer Strategy & Next Steps",
        "weeks": [49, 52],
        "period": "Oct 2027",
        "color": "#ec4899",
        "icon": "🏆",
        "description": "Company-specific rounds, offer comparisons, 90-day notice handling and career transition."
    }
]

weekly_schedule = {
    "monday": [{"activity": "DSA & Problem Solving", "hours": 1.0, "category": "dsa"}],
    "tuesday": [{"activity": "Java / Spring Boot Practice", "hours": 1.0, "category": "java"}],
    "wednesday": [
        {"activity": "DSA Patterns", "hours": 1.0, "category": "dsa"},
        {"activity": "AI Concepts & Assistants", "hours": 0.5, "category": "ai"}
    ],
    "thursday": [{"activity": "Portfolio Project Development", "hours": 1.0, "category": "project"}],
    "friday": [
        {"activity": "DSA Coding Practice", "hours": 1.0, "category": "dsa"},
        {"activity": "Application Security", "hours": 0.5, "category": "security"}
    ],
    "saturday": [
        {"activity": "Project Work (Deep Work)", "hours": 3.0, "category": "project"},
        {"activity": "Engineering Fundamentals & Tests", "hours": 1.0, "category": "java"}
    ],
    "sunday": [
        {"activity": "DSA Timed Challenge", "hours": 1.0, "category": "dsa"},
        {"activity": "AI & Security Labs", "hours": 1.5, "category": "ai"},
        {"activity": "Weekly Reflection & Application Prep", "hours": 1.0, "category": "career"}
    ]
}

weekly_hours_breakdown = {
    "DSA & Coding Interviews": {"hours": 4.0, "category": "dsa"},
    "Java, Spring Boot, SQL & Fundamentals": {"hours": 2.5, "category": "java"},
    "AI Tools & Concepts": {"hours": 2.0, "category": "ai"},
    "Portfolio Project": {"hours": 3.0, "category": "project"},
    "Application Security / DevSecOps": {"hours": 1.5, "category": "security"},
    "Career Preparation & Review": {"hours": 1.0, "category": "career"}
}

milestones = [
    {
        "id": 1,
        "period": "Nov–Dec 2026",
        "title": "Core Java & DSA Foundations",
        "description": "Core Java and DSA foundations; consistent AI-assisted coding workflow; ~30 DSA solved.",
        "targetWeek": 8
    },
    {
        "id": 2,
        "period": "Jan–Feb 2027",
        "title": "Java, Spring Boot & SQL Progress",
        "description": "50+ DSA problems understood; basic Spring Boot and database transaction architecture explained.",
        "targetWeek": 16
    },
    {
        "id": 3,
        "period": "Mar–Apr 2027",
        "title": "Working REST API & LLM Experiments",
        "description": "Working REST API with proper validation, Flutter client, and first isolated Spring AI/LLM integration.",
        "targetWeek": 24
    },
    {
        "id": 4,
        "period": "May–Jun 2027",
        "title": "Flagship Portfolio Application",
        "description": "Secure Credit Workflow Copilot complete with synthetic docs, RAG, auth, evaluations and automated tests.",
        "targetWeek": 32
    },
    {
        "id": 5,
        "period": "Jul–Aug 2027",
        "title": "Security, CI/CD & Reliability",
        "description": "OWASP GenAI mitigations, CI pipeline with SAST/dependency checks, Dockerized setup, system design readiness.",
        "targetWeek": 40
    },
    {
        "id": 6,
        "period": "Sep–Oct 2027",
        "title": "Interview Ready & Active Pipeline",
        "description": "120–160 DSA questions mastered; tailored applications sent; interview pipeline active with ₹30-40 LPA target.",
        "targetWeek": 48
    }
]

skill_tracks = [
    {
        "id": "engineering",
        "name": "Software Engineering Fundamentals",
        "icon": "⚙️",
        "color": "#3b82f6",
        "areas": ["Java", "Spring Boot", "Flutter", "APIs", "SQL", "DSA", "Testing (JUnit/Mockito)", "Git", "System Design"]
    },
    {
        "id": "ai-dev",
        "name": "AI-Assisted Development",
        "icon": "🤖",
        "color": "#8b5cf6",
        "areas": ["Coding Assistants", "Code Analysis before Code Gen", "Verification Workflows", "Edge Case Generation"]
    },
    {
        "id": "ai-eng",
        "name": "AI Application Engineering",
        "icon": "🧠",
        "color": "#f59e0b",
        "areas": ["LLM APIs", "Structured Outputs", "Embeddings & Vector Search", "RAG", "Evaluation", "Tool Calling", "Spring AI"]
    },
    {
        "id": "security",
        "name": "Application Security & DevSecOps",
        "icon": "🔒",
        "color": "#ef4444",
        "areas": ["OWASP GenAI Top 10 (2026)", "API Security", "RBAC/Authorization", "Dependency Scanning", "Docker CI/CD"]
    }
]

target_companies = [
    "Amazon", "Microsoft", "Adobe", "Atlassian", "Salesforce", "Intuit",
    "Walmart Global Tech", "Freshworks", "Zoho", "Postman", "Razorpay",
    "PhonePe", "Flipkart", "Groww", "Visa", "Mastercard", "PayPal"
]

projects = [
    {
        "id": 1,
        "name": "Secure Credit Workflow Copilot",
        "type": "Flagship",
        "tagline": "RAG-powered synthetic credit application assistant with strict RBAC",
        "stack": ["Java 21", "Spring Boot 3", "Spring AI", "PostgreSQL / pgvector", "Flutter", "Docker"],
        "deliverables": [
            "Synthetic credit workflow documents & data model",
            "Vector retrieval with grounded contextual responses",
            "Citations and source inspectability",
            "Backend permission enforcement (never bypass access controls)",
            "Automated answer quality and hallucination evaluations"
        ]
    },
    {
        "id": 2,
        "name": "AI-Assisted Defect Triage Lab",
        "type": "Secondary",
        "tagline": "Automated defect classifier & test generator for enterprise issues",
        "stack": ["Java", "JUnit 5", "LLM API", "Mockito"],
        "deliverables": [
            "Categorize synthetic bug reports into subsystems",
            "Generate proposed regression test case suites",
            "Evaluate hallucination rates with human-in-the-loop review"
        ]
    },
    {
        "id": 3,
        "name": "Secure Mobile/API Demo",
        "type": "Secondary",
        "tagline": "Hardened Flutter client with JWT/OAuth2 and SAST checks",
        "stack": ["Flutter", "Java/Spring Boot", "OWASP ZAP", "GitHub Actions"],
        "deliverables": [
            "Secure token storage and encrypted transmission",
            "Automated API contract validation and dependency checks",
            "SAST pipeline blocking known vulnerabilities"
        ]
    }
]

category_colors = {
    "dsa": {"bg": "#dbeafe", "text": "#1e40af", "label": "DSA"},
    "java": {"bg": "#ffedd5", "text": "#c2410c", "label": "Java / Backend"},
    "ai": {"bg": "#ede9fe", "text": "#6d28d9", "label": "AI Engineering"},
    "security": {"bg": "#fee2e2", "text": "#dc2626", "label": "Security & DevOps"},
    "project": {"bg": "#dcfce7", "text": "#16a34a", "label": "Project"},
    "career": {"bg": "#f3f4f6", "text": "#4b5563", "label": "Career Prep"}
}

# 52 weeks raw definitions
weeks_meta = [
    # Phase A (1-8)
    (1, "A", "Assess Java skills, Big-O and arrays", "Establish a safe AI-assisted coding workflow and baseline test", [
        ("Review core Java assessment & memory model", "java"),
        ("Study Big-O time and space complexity", "dsa"),
        ("Solve 5 array problems on LeetCode", "dsa"),
        ("Configure IDE AI assistant workflow (prompting, analysis before code)", "ai"),
        ("Establish baseline coding test without AI assist", "career")
    ]),
    (2, "A", "Strings and Java collections framework", "Ask an AI assistant for explanations and test ideas; verify independently", [
        ("Deep dive into String, StringBuilder and immutability", "java"),
        ("Master List, ArrayList, and LinkedList internals", "java"),
        ("Solve 5 LeetCode String problems", "dsa"),
        ("Prompt AI for unit test edge cases on string manipulation", "ai"),
        ("Validate AI-suggested test cases manually against requirements", "ai")
    ]),
    (3, "A", "Hash maps, sets and frequency counts", "Review a small code change with AI, then validate the tests yourself", [
        ("Internal working of HashMap (buckets, hashing, load factor, collisions)", "java"),
        ("Solve 5 HashMap / HashSet problems (e.g., Two Sum, Group Anagrams)", "dsa"),
        ("Review a refactored utility method with AI assistant", "ai"),
        ("Write JUnit tests to catch regressions in the refactored code", "java"),
        ("Document lessons learned on hash collision handling", "career")
    ]),
    (4, "A", "Two pointers and sorting algorithms", "Record where AI explanations or generated code were incorrect", [
        ("Implement Two-Pointer patterns (opposite ends, same direction)", "dsa"),
        ("Revise Quicksort, Mergesort, and Dual-Pivot Quicksort in Java", "dsa"),
        ("Solve 5 two-pointer problems", "dsa"),
        ("Challenge AI with complex sorting edge cases and record errors", "ai"),
        ("Log incorrect AI assumptions in personal study journal", "ai")
    ]),
    (5, "A", "Sliding window patterns", "Practise writing acceptance criteria before requesting code", [
        ("Study Fixed and Dynamic Sliding Window algorithms", "dsa"),
        ("Solve 5 sliding window problems", "dsa"),
        ("Draft explicit acceptance criteria & constraints before prompting AI", "ai"),
        ("Compare solution against acceptance criteria and verify complexity", "ai"),
        ("Review Java 21 feature highlights (pattern matching, records)", "java")
    ]),
    (6, "A", "Binary search and search spaces", "Use AI to generate edge cases and inspect missing cases", [
        ("Master classic binary search and search-on-answer patterns", "dsa"),
        ("Solve 5 binary search problems", "dsa"),
        ("Use AI to brainstorm edge cases (empty array, single element, duplicates)", "ai"),
        ("Identify gaps where AI missed critical corner conditions", "ai"),
        ("Practice explaining binary search loop invariants out loud", "career")
    ]),
    (7, "A", "Linked lists, pointers and Java exceptions", "Trace an unfamiliar Java defect through reproduction, fix and test", [
        ("Singly & doubly linked list operations (reversal, cycle detection)", "dsa"),
        ("Solve 5 linked list problems", "dsa"),
        ("Deep dive into Java Exception hierarchy & best practices", "java"),
        ("Reproduce and trace an unfamiliar defect using debugger", "java"),
        ("Write a regression test verifying the bug fix", "java")
    ]),
    (8, "A", "Stacks, queues and monotonic structures", "Build a small REST API and review it with AI without accepting unverified patches", [
        ("Master stack applications and Monotonic Stack pattern", "dsa"),
        ("Solve 5 stack/queue problems", "dsa"),
        ("Scaffold a minimal Spring Boot REST API for task management", "java"),
        ("Review API design and error handling using AI assistant", "ai"),
        ("Milestone Checkpoint 1: Complete Foundations & AI Fluency Review", "career")
    ]),

    # Phase B (9-16)
    (9, "B", "Recursion, backtracking and state spaces", "Use AI to compare two algorithms; explain the trade-offs yourself", [
        ("Understand call stack, base cases and recurrence relations", "dsa"),
        ("Solve 4 backtracking problems (Subsets, Permutations)", "dsa"),
        ("Ask AI to contrast iterative vs recursive approaches for state exploration", "ai"),
        ("Write personal explanation of time/space trade-offs", "career"),
        ("Explore Java Virtual Threads (Project Loom) basics", "java")
    ]),
    (10, "B", "Binary trees and tree traversals", "Use AI to generate unit tests for tree methods", [
        ("Implement In-order, Pre-order, Post-order & Level-order traversals", "dsa"),
        ("Solve 5 binary tree problems", "dsa"),
        ("Generate JUnit test suites for tree serialization with AI", "ai"),
        ("Refactor generated tests for readability and boundary conditions", "java"),
        ("Spring Boot: Deep dive into @Configuration and Bean lifecycle", "java")
    ]),
    (11, "B", "BSTs, heaps and priority queues", "Review the generated tests and add missing edge cases", [
        ("Master Binary Search Tree invariants and Top-K heap pattern", "dsa"),
        ("Solve 5 BST & PriorityQueue problems", "dsa"),
        ("Evaluate AI-generated edge cases for heap rebalancing", "ai"),
        ("Add tests for duplicate elements and capacity limits", "java"),
        ("Review Spring Boot starter dependencies and auto-configuration", "java")
    ]),
    (12, "B", "Java dependency injection and mock testing", "Set up service-level tests with JUnit 5 and Mockito", [
        ("Master Spring Core DI: Constructor injection, Qualifier, Scopes", "java"),
        ("Write unit tests using JUnit 5, Mockito and AssertJ", "java"),
        ("Mock external dependencies and database repositories cleanly", "java"),
        ("Solve 3 medium tree/heap problems", "dsa"),
        ("Audit test suite code coverage with JaCoCo", "security")
    ]),
    (13, "B", "Tree patterns and HTTP protocol fundamentals", "Learn to explain API request/response flows accurately", [
        ("Solve 4 tree path & diameter problems", "dsa"),
        ("Study HTTP/1.1 vs HTTP/2, status codes, headers and idempotent methods", "java"),
        ("Diagram request-to-response lifecycle through Spring DispatcherServlet", "java"),
        ("Practice explaining an API transaction flow concisely", "career"),
        ("Implement custom Spring HTTP exception handlers (@ControllerAdvice)", "java")
    ]),
    (14, "B", "SQL joins, indexing and transactions", "Ask AI to critique a database schema and validate its suggestions", [
        ("Practice complex SQL joins, aggregations and window functions", "java"),
        ("Study B-Tree indexes, composite indexes and EXPLAIN query plans", "java"),
        ("Design schema for a loan application model; ask AI for feedback", "ai"),
        ("Validate AI normalization and indexing suggestions against real query load", "java"),
        ("Solve 3 interval/prefix sum DSA questions", "dsa")
    ]),
    (15, "B", "Graphs: BFS and DFS algorithms", "Compare algorithm complexity and implement both yourself", [
        ("Graph representations: Adjacency List vs Matrix", "dsa"),
        ("Implement Breadth-First Search and Depth-First Search from scratch", "dsa"),
        ("Solve 4 graph traversal problems", "dsa"),
        ("Contrast BFS vs DFS for shortest path in unweighted graphs with AI", "ai"),
        ("Verify time/space complexity analysis independently", "career")
    ]),
    (16, "B", "Mixed DSA assessment and Spring Boot architecture", "Use AI for a mock interview, then score your answers honestly", [
        ("Complete a timed 3-problem mixed DSA assessment", "dsa"),
        ("Conduct an AI-driven technical mock interview on Spring & DBs", "career"),
        ("Score answers objectively and highlight weak knowledge areas", "career"),
        ("Review Spring Data JPA repositories & query methods", "java"),
        ("Milestone Checkpoint 2: Core Programming & DB Milestone Review", "career")
    ]),

    # Phase C (17-24)
    (17, "C", "REST API design, DTOs and validation", "Build proper request validation and error handling", [
        ("Design RESTful endpoints following OpenAPI / Swagger standards", "java"),
        ("Implement Jakarta Bean Validation (@NotNull, @Valid, custom validators)", "java"),
        ("Standardize error response payload with ProblemDetails (RFC 7807)", "java"),
        ("Solve 3 graph connectivity problems (Number of Islands, etc.)", "dsa"),
        ("Document API contract for credit application submission", "project")
    ]),
    (18, "C", "JPA/Hibernate entities and relationships", "Use AI to compare persistence approaches; test the chosen implementation", [
        ("Map @OneToMany, @ManyToOne, and @ManyToMany relationships correctly", "java"),
        ("Understand Hibernate N+1 problem and fix with JOIN FETCH", "java"),
        ("Ask AI to compare JPA vs jOOQ vs JDBC for high-throughput banking", "ai"),
        ("Write repository integration tests with @DataJpaTest and Testcontainers", "java"),
        ("Solve 3 topological sort / cycle detection graph problems", "dsa")
    ]),
    (19, "C", "Transactions, concurrency and idempotency", "Test duplicate requests and failure scenarios", [
        ("Study Spring @Transactional propagation, isolation levels and rollback rules", "java"),
        ("Implement Idempotency-Key handling for financial API operations", "java"),
        ("Write concurrent integration tests simulating race conditions", "java"),
        ("Solve 3 Dynamic Programming intro problems", "dsa"),
        ("Review pessimistic vs optimistic locking in JPA (@Version)", "java")
    ]),
    (20, "C", "Flutter architecture and API integration", "Improve UI error handling and make loading states reliable", [
        ("Review Flutter state management (Bloc / Riverpod / Provider)", "project"),
        ("Connect Flutter mobile client to Spring Boot REST endpoints", "project"),
        ("Implement robust offline, loading, and error states in UI", "project"),
        ("Solve 3 Dynamic Programming memoization problems", "dsa"),
        ("Review mobile security: secure token storage on Android/iOS", "security")
    ]),
    (21, "C", "LLM concepts, tokens, context and model APIs", "Make a minimal, isolated AI API integration in a learning project", [
        ("Learn tokenization, context windows, temperature and top-p sampling", "ai"),
        ("Explore Spring AI starter dependencies and ChatClient API", "ai"),
        ("Build a lightweight standalone CLI calling OpenAI / Gemini model", "ai"),
        ("Protect API keys using environment variables and secrets manager", "security"),
        ("Solve 3 DP tabulation problems", "dsa")
    ]),
    (22, "C", "Structured outputs, timeouts and retries", "Validate model outputs against a schema and handle failure safely", [
        ("Configure Spring AI BeanOutputConverter / JSON Schema validation", "ai"),
        ("Implement client timeouts, exponential backoff and circuit breaker with Resilience4j", "java"),
        ("Handle malformed or hallucinated LLM responses gracefully", "ai"),
        ("Solve 3 greedy algorithms problems", "dsa"),
        ("Calculate token costs and design usage tracking", "ai")
    ]),
    (23, "C", "Embeddings and semantic vector search", "Complete an introductory RAG learning exercise", [
        ("Study dense embeddings, cosine similarity and vector spaces", "ai"),
        ("Set up pgvector in PostgreSQL via Docker", "ai"),
        ("Generate embeddings for sample banking documents using Spring AI", "ai"),
        ("Perform similarity search queries with metadata filters", "ai"),
        ("Solve 3 binary tree revision questions", "dsa")
    ]),
    (24, "C", "RAG architecture and evaluation design", "Design the data flow for a secure AI-enabled portfolio application", [
        ("Architecture blueprint: Ingestion -> Chunking -> Embeddings -> Retrieval -> Augmentation", "ai"),
        ("Design access control boundaries so user retrieval obeys roles", "security"),
        ("Draft system architecture diagram for 'Secure Credit Workflow Copilot'", "project"),
        ("Solve 3 graph revision problems", "dsa"),
        ("Milestone Checkpoint 3: Backend & LLM Experimentation Review", "career")
    ]),

    # Phase D (25-32)
    (25, "D", "Flagship: Java/Spring Boot API and synthetic workflows", "Build the Java/Spring Boot API, data model and synthetic workflow records", [
        ("Initialize 'Secure Credit Workflow Copilot' repository", "project"),
        ("Create JPA entities: LoanApplication, Document, WorkflowStep, AuditLog", "project"),
        ("Generate 20+ realistic synthetic credit case files (zero sensitive data)", "project"),
        ("Write comprehensive CRUD unit and integration tests", "java"),
        ("Solve 3 medium LeetCode DSA questions", "dsa")
    ]),
    (26, "D", "Flagship: Document chunking and vector ingestion", "Create document ingestion and retrieval for a controlled set of fictional documents", [
        ("Implement smart document chunking strategy (sentence/paragraph window)", "project"),
        ("Store chunk embeddings in pgvector with metadata tags", "ai"),
        ("Build IngestionPipeline service with progress updates", "project"),
        ("Write tests verifying chunk retrieval accuracy for sample queries", "ai"),
        ("Solve 3 sliding window revision questions", "dsa")
    ]),
    (27, "D", "Flagship: LLM integration and grounded question-answering", "Connect an LLM and generate answers grounded in retrieved material", [
        ("Implement RetrievalAugmentedGeneration service using Spring AI", "project"),
        ("Craft system prompt enforcing answers strictly based on retrieved context", "ai"),
        ("Handle empty retrieval results with polite fallback messaging", "ai"),
        ("Add latency logging and request-response audit tracking", "java"),
        ("Solve 3 binary search revision questions", "dsa")
    ]),
    (28, "D", "Flagship: Citations and verifiable source inspection", "Add source snippets or citations so users can inspect supporting material", [
        ("Return citation metadata (documentId, page, chunkSnippet) with every response", "project"),
        ("Design response DTO with answerText + List<Citation>", "java"),
        ("Write unit tests checking citation mapping fidelity", "java"),
        ("Perform manual verification on 10 synthetic credit case inquiries", "ai"),
        ("Solve 3 linked list / stack problems", "dsa")
    ]),
    (29, "D", "Flagship: Flutter frontend interface", "Add Flutter screens for questions, answers, loading states and errors", [
        ("Build clean chat & document inspector interface in Flutter", "project"),
        ("Implement expandable source citation cards in the UI", "project"),
        ("Add animated loading indicators, streaming response support, and error toasts", "project"),
        ("Connect Flutter app to local Spring Boot backend seamlessly", "project"),
        ("Solve 3 two-pointer revision questions", "dsa")
    ]),
    (30, "D", "Flagship: Authentication and Role-Based Access Control", "Implement backend authentication and authorization; restrict document retrieval by user permissions", [
        ("Implement Spring Security with JWT authentication", "security"),
        ("Define roles: ROLE_ANALYST, ROLE_MANAGER, ROLE_AUDITOR", "security"),
        ("Enforce document retrieval filters based on user clearance level in SQL/Vector query", "security"),
        ("Write security integration tests verifying unauthorized access rejection", "security"),
        ("Solve 3 sorting & searching questions", "dsa")
    ]),
    (31, "D", "Flagship: Automated RAG evaluation dataset", "Create a repeatable evaluation dataset and test answer relevance, factual grounding and failure handling", [
        ("Create golden test dataset of 25 question-context-answer triplets", "ai"),
        ("Implement automated scoring: Context Relevance, Groundedness, Answer Correctness", "ai"),
        ("Measure and report hallucination rate across automated regression suite", "ai"),
        ("Document evaluation methodology in project documentation", "project"),
        ("Solve 3 tree traversal problems", "dsa")
    ]),
    (32, "D", "Flagship: Tests, CI pipeline, architecture docs and demo", "Add automated tests, a README, architecture diagram and a demonstration video or screenshots", [
        ("Achieve >80% test coverage across backend services", "java"),
        ("Create clear architecture diagrams (C4 model / data flow)", "project"),
        ("Write exemplary README with setup instructions and architectural trade-offs", "project"),
        ("Record a 3-minute video walkthrough showcasing RAG & security controls", "career"),
        ("Milestone Checkpoint 4: Flagship Portfolio Project Complete", "career")
    ]),

    # Phase E (33-40)
    (33, "E", "AI Security Risks & OWASP GenAI Top 10", "Study the OWASP GenAI Top 10 for 2026", [
        ("Study OWASP GenAI 2026: Prompt Injection, Sensitive Info Disclosure, Excessive Agency", "security"),
        ("Analyze vulnerabilities in RAG architectures (context poisoning, vector hijacking)", "security"),
        ("Draft threat model for the credit copilot project", "security"),
        ("Solve 3 medium DSA questions", "dsa"),
        ("Share security insights in study journal", "career")
    ]),
    (34, "E", "Prompt injection and data leakage defence", "Create safe adversarial test cases for the project", [
        ("Develop 15 safe adversarial prompts (jailbreaks, context leakage attempts)", "security"),
        ("Implement system prompt hardening and input delimiter separation", "security"),
        ("Test output guardrails to prevent PII / sensitive internal token leakage", "security"),
        ("Verify all adversarial tests are caught and handled gracefully", "security"),
        ("Solve 3 graph shortest-path problems", "dsa")
    ]),
    (35, "E", "Output validation and authorization boundaries", "Ensure an AI response cannot bypass backend access controls", [
        ("Enforce strict JSON schema validation on all model responses", "security"),
        ("Verify model cannot trigger unauthorized tool actions or elevate privileges", "security"),
        ("Implement immutable audit trail for all AI-assisted queries and user IDs", "security"),
        ("Solve 3 dynamic programming questions", "dsa"),
        ("Conduct code review on permission boundaries", "security")
    ]),
    (36, "E", "Cost controls, rate limiting and abuse prevention", "Add limits, timeouts, error handling and suitable logging", [
        ("Implement Bucket4j rate limiting per IP and user account", "security"),
        ("Set token consumption quotas and per-request hard timeout budgets", "security"),
        ("Configure structured JSON logging with correlation IDs (Logback/MDC)", "java"),
        ("Simulate high-concurrency burst traffic with Apache Benchmark or k6", "security"),
        ("Solve 3 mixed DSA problems", "dsa")
    ]),
    (37, "E", "Static analysis and dependency scanning (DevSecOps)", "Add static analysis and dependency checks to CI", [
        ("Integrate SpotBugs / SonarQube rules for Java in build tool", "security"),
        ("Add OWASP Dependency-Check / Snyk to flag vulnerable third-party libraries", "security"),
        ("Fix all critical and high vulnerability alerts in codebase", "security"),
        ("Configure GitHub Actions workflow running checks on pull request", "security"),
        ("Solve 3 heap / priority queue questions", "dsa")
    ]),
    (38, "E", "Docker containerization and delivery pipeline", "Make the project reproducible from a clean environment", [
        ("Write multi-stage Dockerfile for Spring Boot (distroless/Alpine base)", "security"),
        ("Write Docker Compose orchestrating backend, postgres/pgvector, and redis", "project"),
        ("Verify complete project boots and passes tests with `docker compose up`", "project"),
        ("Set up CI workflow running unit + integration tests in containers", "security"),
        ("Solve 3 binary tree revision questions", "dsa")
    ]),
    (39, "E", "Low-Level Design (LLD) & System Design fundamentals", "Practise explaining design decisions and failure scenarios", [
        ("Practice LLD: Rate Limiter, Notification Service, Cache (LRU)", "java"),
        ("Study System Design basics: Caching strategies (Redis), CDN, Sharding", "java"),
        ("Draft system design write-up for scalable RAG pipeline", "project"),
        ("Practice whiteboard explanation of CAP theorem and distributed consensus", "career"),
        ("Solve 3 intervals / greedy DSA problems", "dsa")
    ]),
    (40, "E", "Comprehensive project and technical review", "Conduct a timed coding assessment and a project walkthrough", [
        ("Complete timed 90-minute DSA coding assessment", "dsa"),
        ("Perform 45-minute mock project walkthrough addressing failure modes", "career"),
        ("Audit resume bullet points against completed project deliverables", "career"),
        ("Refactor minor code smells in copilot repository", "project"),
        ("Milestone Checkpoint 5: Security & Reliability Review", "career")
    ]),

    # Phase F (41-48)
    (41, "F", "Resume crafting and LinkedIn profile optimization", "Emphasize real TCS work, measurable contributions and demonstrable projects", [
        ("Rewrite resume highlighting TCS WCAS defect resolution & architecture insights", "career"),
        ("Detail 'Secure Credit Workflow Copilot' with quantifiable metrics (RAG, 85% test cov)", "career"),
        ("Optimize LinkedIn headline: 'Backend Engineer | Java · Spring Boot · RAG & AI Engineering'", "career"),
        ("Seek peer/mentor feedback on resume clarity and impact verbs", "career"),
        ("Solve 3 high-frequency product-company DSA questions", "dsa")
    ]),
    (42, "F", "DSA pattern revision and timed coding rounds", "Revisit weak patterns and practise timed coding", [
        ("Revisit weak DSA patterns (DP, Graph BFS/DFS, Monotonic Stack)", "dsa"),
        ("Complete 3 timed 45-minute LeetCode contest rounds", "dsa"),
        ("Review NeetCode 150 top frequency list", "dsa"),
        ("Practice explaining algorithmic logic out loud while coding", "career"),
        ("Review Java Collections internal implementation details", "java")
    ]),
    (43, "F", "Java, Spring Boot & Concurrency interview mastery", "Practise technical questions and debugging walkthroughs", [
        ("Deep dive into Java Memory Model, Garbage Collection (G1/ZGC), and Concurrency", "java"),
        ("Review Spring Boot internals: AOP, BeanPostProcessor, Transaction mechanics", "java"),
        ("Prepare 5 real defect debugging narratives from TCS WCAS experience", "career"),
        ("Mock interview: 1-hour Java technical grilling", "career"),
        ("Solve 3 tree / graph interview problems", "dsa")
    ]),
    (44, "F", "Project deep dive and LLD interview readiness", "Explain architecture, trade-offs and how the AI evaluation works", [
        ("Prepare deep-dive slides/notes on RAG architecture and vector search trade-offs", "career"),
        ("Practice defending security decisions: Why RBAC in vector query? Why not LLM-only check?", "security"),
        ("Practice LLD problem: Design an Idempotent Payment/Workflow API", "java"),
        ("Solve 3 medium DSA questions", "dsa"),
        ("Refine 2-minute project pitch for hiring managers", "career")
    ]),
    (45, "F", "Start strategic product-company applications", "Apply to well-matched roles and seek relevant referrals", [
        ("Target tier-1/tier-2 product companies (Amazon, Microsoft, Razorpay, PhonePe, etc.)", "career"),
        ("Reach out to 10 alumni/engineers on LinkedIn for warm referrals", "career"),
        ("Submit 5–8 customized applications matching Java backend / product roles", "career"),
        ("Track application statuses in job tracker table", "career"),
        ("Maintain daily DSA streak (1 problem/day)", "dsa")
    ]),
    (46, "F", "Live mock interviews and gap closure", "Record mistakes and address the highest-impact gaps", [
        ("Conduct 2 peer or platform mock interviews (DSA + System Design)", "career"),
        ("Review recorded mock interviews to identify filler words and hesitation", "career"),
        ("Strengthen weakest topics identified in feedback", "career"),
        ("Continue weekly targeted applications (5–8 companies)", "career"),
        ("Solve 3 high-frequency interview problems", "dsa")
    ]),
    (47, "F", "System design and behavioral interview mastery", "Prepare examples involving ownership, teamwork and problem solving", [
        ("Practice System Design: Scalable URL Shortener, Rate Limiter, Web Crawler", "java"),
        ("Prepare STAR-format stories for behavioral questions (conflict, failure, ownership)", "career"),
        ("Prepare clear explanation of TCS 90-day notice period for recruiters", "career"),
        ("Submit 5–8 new targeted job applications", "career"),
        ("Solve 3 DSA problems", "dsa")
    ]),
    (48, "F", "Interview pipeline review and active rounds", "Evaluate feedback, notice-period concerns and remaining skill gaps", [
        ("Review interview pipeline metrics and recruiter callback rates", "career"),
        ("Refine application strategy based on market responses", "career"),
        ("Participate in initial recruiter screens and technical rounds", "career"),
        ("Solve 3 DSA problems", "dsa"),
        ("Milestone Checkpoint 6: Interview Pipeline & Readiness Review", "career")
    ]),

    # Phase G (49-52)
    (49, "G", "Company-specific interview preparation", "Focus on the exact job description and interview format", [
        ("Analyze specific tech stack & problem styles for upcoming interview rounds", "career"),
        ("Practice company-tagged LeetCode questions for active interviewers", "dsa"),
        ("Research interviewer backgrounds and recent company engineering blog posts", "career"),
        ("Perform dry run of personal introduction and technical walkthrough", "career"),
        ("Prepare insightful questions to ask the interviewers", "career")
    ]),
    (50, "G", "Technical rounds and debrief iterations", "Review every failure and strengthen weak topics", [
        ("Attend scheduled technical coding and system design rounds", "career"),
        ("Conduct immediate post-interview debrief: log all asked questions and hiccups", "career"),
        ("Resolve any DSA problems that were difficult during the round within 24 hours", "dsa"),
        ("Send polite thank-you / follow-up notes to recruiters", "career"),
        ("Stay sharp with 1 medium DSA problem daily", "dsa")
    ]),
    (51, "G", "Offer evaluation and compensation negotiation", "Compare fixed compensation, equity, variable pay, role, office attendance and benefits", [
        ("Evaluate offers against ₹30–40 LPA ambitious target", "career"),
        ("Assess base pay vs variable vs ESOP vesting schedule", "career"),
        ("Evaluate team engineering culture, work model (remote/hybrid), and tech stack", "career"),
        ("Negotiate professionally using competing offers and demonstrated project depth", "career"),
        ("Confirm all offer terms in writing before taking action", "career")
    ]),
    (52, "G", "Transition decision and notice period management", "Confirm written terms and plan your notice period—or continue preparing if no offer meets your criteria", [
        ("Formally accept winning offer with signed documentation", "career"),
        ("Plan 90-day TCS resignation and smooth project handover", "career"),
        ("Establish knowledge-transfer documentation for WCAS defect coverage", "career"),
        ("Celebrate the journey: from defect fixes to secure AI-ready product engineer! 🎉", "career"),
        ("Plan Day 1 ramp-up strategy for your new product company role", "career")
    ])
]

weeks = []
for (wnum, phase_id, eng, ai_del, task_list) in weeks_meta:
    tasks = []
    for idx, (ttext, tcat) in enumerate(task_list, 1):
        tasks.append({
            "id": f"w{wnum}-t{idx}",
            "text": ttext,
            "category": tcat,
            "completed": False
        })
    weeks.append({
        "week": wnum,
        "phase": phase_id,
        "engineering": eng,
        "aiReadiness": ai_del,
        "tasks": tasks
    })

data = {
    "planInfo": plan_info,
    "phases": phases,
    "weeklySchedule": weekly_schedule,
    "weeklyHoursBreakdown": weekly_hours_breakdown,
    "milestones": milestones,
    "skillTracks": skill_tracks,
    "targetCompanies": target_companies,
    "projects": projects,
    "categoryColors": category_colors,
    "weeks": weeks
}

js_content = "/**\n * StudyTracker - 52-Week Career Plan Data Module\n * Generated for Jayasuriya's AI-ready TCS-to-Product Career Roadmap (2026-2027)\n */\n\nconst PLAN_DATA = " + json.dumps(data, indent=2) + ";\n"

with open(r"C:\Users\Jayasuriya\.gemini\antigravity\scratch\StudyTracker\data.js", "w", encoding="utf-8") as f:
    f.write(js_content)

print(f"data.js successfully generated! Weeks: {len(weeks)}, Milestones: {len(milestones)}")
