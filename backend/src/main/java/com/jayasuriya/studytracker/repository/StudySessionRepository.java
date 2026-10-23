package com.jayasuriya.studytracker.repository;

import com.jayasuriya.studytracker.model.StudySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudySessionRepository extends JpaRepository<StudySession, Long> {
    List<StudySession> findByOwnerIdOrderBySessionDateDescCreatedAtDesc(Long ownerId);
    List<StudySession> findByOwnerIdAndSessionDateBetween(Long ownerId, LocalDate start, LocalDate end);
    Optional<StudySession> findByIdAndOwnerId(Long id, Long ownerId);

    @Query("SELECT COALESCE(SUM(s.durationMinutes), 0) FROM StudySession s WHERE s.owner.id = :ownerId AND s.sessionDate BETWEEN :start AND :end")
    Integer getTotalMinutesBetween(@Param("ownerId") Long ownerId, @Param("start") LocalDate start, @Param("end") LocalDate end);
}
