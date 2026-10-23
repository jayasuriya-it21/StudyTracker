package com.jayasuriya.studytracker.repository;

import com.jayasuriya.studytracker.model.Roadmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {
    Optional<Roadmap> findByOwnerIdAndIsActiveTrue(Long ownerId);
    Optional<Roadmap> findFirstByIsTemplateTrue();
    Optional<Roadmap> findByIdAndOwnerId(Long id, Long ownerId);
}
