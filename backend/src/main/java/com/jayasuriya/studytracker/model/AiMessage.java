package com.jayasuriya.studytracker.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "ai_messages")
public class AiMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private AiConversation conversation;

    @Column(nullable = false, length = 20)
    private String role; // "user", "assistant", "system"

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(length = 100)
    private String model;

    @Column(name = "tokens_used")
    private int tokensUsed = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_rating", length = 30)
    private Enums.VerificationRating verificationRating = Enums.VerificationRating.UNRATED;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public AiMessage() {}

    public AiMessage(AiConversation conversation, String role, String content, String model) {
        this.conversation = conversation;
        this.role = role;
        this.content = content;
        this.model = model;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public AiConversation getConversation() { return conversation; }
    public void setConversation(AiConversation conversation) { this.conversation = conversation; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getTokensUsed() { return tokensUsed; }
    public void setTokensUsed(int tokensUsed) { this.tokensUsed = tokensUsed; }

    public Enums.VerificationRating getVerificationRating() { return verificationRating; }
    public void setVerificationRating(Enums.VerificationRating verificationRating) { this.verificationRating = verificationRating; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
