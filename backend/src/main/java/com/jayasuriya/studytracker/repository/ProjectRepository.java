package com.jayasuriya.studytracker.repository;

import com.jayasuriya.studytracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwnerIdOrderByCreatedAtAsc(Long ownerId);
    Optional<Project> findByIdAndOwnerId(Long id, Long ownerId);

    @Query("SELECT p FROM Project p WHERE p.owner.id = :ownerId AND (LOWER(p.title) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :q, '%')))")
    List<Project> searchProjects(@Param("ownerId") Long ownerId, @Param("q") String q);
}
