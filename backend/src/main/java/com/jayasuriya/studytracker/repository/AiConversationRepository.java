package com.jayasuriya.studytracker.repository;

import com.jayasuriya.studytracker.model.AiConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AiConversationRepository extends JpaRepository<AiConversation, Long> {
    List<AiConversation> findByOwnerIdOrderByUpdatedAtDesc(Long ownerId);
    Optional<AiConversation> findByIdAndOwnerId(Long id, Long ownerId);
}
