package com.jayasuriya.studytracker.repository;

import com.jayasuriya.studytracker.model.LearningResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LearningResourceRepository extends JpaRepository<LearningResource, Long> {
    List<LearningResource> findByOwnerIdOrderByCreatedAtDesc(Long ownerId);
    Optional<LearningResource> findByIdAndOwnerId(Long id, Long ownerId);

    @Query("SELECT r FROM LearningResource r WHERE r.owner.id = :ownerId AND (LOWER(r.title) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(r.notes) LIKE LOWER(CONCAT('%', :q, '%')))")
    List<LearningResource> searchResources(@Param("ownerId") Long ownerId, @Param("q") String q);
}
