package com.jayasuriya.studytracker.repository;

import com.jayasuriya.studytracker.model.StudyDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyDocumentRepository extends JpaRepository<StudyDocument, Long> {
    List<StudyDocument> findByOwnerIdOrderByCreatedAtDesc(Long ownerId);
    Optional<StudyDocument> findByIdAndOwnerId(Long id, Long ownerId);

    @Query("SELECT d FROM StudyDocument d WHERE d.owner.id = :ownerId AND (LOWER(d.title) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(d.content) LIKE LOWER(CONCAT('%', :q, '%')))")
    List<StudyDocument> searchDocuments(@Param("ownerId") Long ownerId, @Param("q") String q);
}
