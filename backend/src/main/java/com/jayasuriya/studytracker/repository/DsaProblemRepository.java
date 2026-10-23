package com.jayasuriya.studytracker.repository;

import com.jayasuriya.studytracker.model.DsaProblem;
import com.jayasuriya.studytracker.model.Enums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DsaProblemRepository extends JpaRepository<DsaProblem, Long> {
    List<DsaProblem> findByOwnerIdOrderByDateAttemptedDesc(Long ownerId);
    Optional<DsaProblem> findByIdAndOwnerId(Long id, Long ownerId);
    long countByOwnerIdAndStatus(Long ownerId, Enums.DsaStatus status);
    long countByOwnerId(Long ownerId);

    @Query("SELECT p FROM DsaProblem p WHERE p.owner.id = :ownerId AND p.nextReviewDate <= :today ORDER BY p.nextReviewDate ASC")
    List<DsaProblem> findRevisionQueue(@Param("ownerId") Long ownerId, @Param("today") LocalDate today);

    @Query("SELECT p FROM DsaProblem p WHERE p.owner.id = :ownerId AND (LOWER(p.title) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(p.topicPattern) LIKE LOWER(CONCAT('%', :q, '%')))")
    List<DsaProblem> searchDsa(@Param("ownerId") Long ownerId, @Param("q") String q);
}
