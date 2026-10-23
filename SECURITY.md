# Security Policy & Architecture

## Security Philosophy

StudyTracker was built with the **Secure Product Engineer** mindset:
1. **User Data Isolation**: Every resource (roadmaps, study sessions, DSA problems, applications, AI conversations) is strictly partitioned by `user_id`. Queries enforce ownership boundaries at the repository and service levels to prevent Insecure Direct Object References (IDOR).
2. **Confidentiality & Synthetic Data**: Zero confidential TCS, Commercial Bank of Dubai (CBD), Wholesale Credit Application System (WCAS), customer, banking, or employer data is present. All portfolio and demo artifacts use purely fictional scenarios (e.g. Acme Commercial Credit).
3. **Stateless JWT & HttpOnly Cookie Authentication**: Passwords are hashed with BCrypt. JWT authentication supports both `Authorization: Bearer <token>` headers and secure `Set-Cookie` with `HttpOnly`, `SameSite=Strict`, and `Secure` attributes.
4. **Educational AI Guardrails**:
   - **Hints-First Pedagogy**: System prompt prevents the assistant from spoon-feeding raw solutions to encourage active cognitive retention.
   - **Verification Advisories**: Every AI response includes a mandatory reminder that LLM outputs can hallucinate or introduce security flaws and must be tested.
   - **Prompt Injection Defense**: User queries are sanitized for common system override tokens (`ignore previous instructions`, `system prompt`, `you are now DAN`).
   - **Rate Limiting**: Daily token/request quotas protect against denial-of-wallet and API exhaustion.

## Secret Management

- Secrets are injected strictly via environment variables.
- No real credentials, API keys, or JWT secrets are stored in version control.
- An `.env.example` file is provided with safe placeholders.

## Reporting a Vulnerability

If you discover a security issue or vulnerability within StudyTracker, please open a private security advisory on GitHub or email the maintainer directly. All legitimate reports will receive prompt attention and coordinated disclosure.
