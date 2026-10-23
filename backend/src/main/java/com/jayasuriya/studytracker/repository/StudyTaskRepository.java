package com.jayasuriya.studytracker.repository;

import com.jayasuriya.studytracker.model.Enums;
import com.jayasuriya.studytracker.model.StudyTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudyTaskRepository extends JpaRepository<StudyTask, Long> {
    List<StudyTask> findByWeekPlanIdAndOwnerIdOrderBySortOrderAsc(Long weekId, Long ownerId);
    List<StudyTask> findByWeekPlanIdAndOwnerIsNullOrderBySortOrderAsc(Long weekId);
    List<StudyTask> findByOwnerId(Long ownerId);
    Optional<StudyTask> findByIdAndOwnerId(Long id, Long ownerId);
    long countByOwnerIdAndStatus(Long ownerId, Enums.TaskStatus status);
    long countByOwnerId(Long ownerId);

    @Query("SELECT t FROM StudyTask t WHERE t.owner.id = :ownerId AND t.status != 'COMPLETED' AND t.dueDate < :today")
    List<StudyTask> findOverdueTasks(@Param("ownerId") Long ownerId, @Param("today") LocalDate today);

    @Query("SELECT t FROM StudyTask t WHERE t.owner.id = :ownerId AND t.dueDate = :today")
    List<StudyTask> findTodayTasks(@Param("ownerId") Long ownerId, @Param("today") LocalDate today);

    @Query("SELECT t FROM StudyTask t WHERE t.owner.id = :ownerId AND (LOWER(t.title) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(t.description) LIKE LOWER(CONCAT('%', :q, '%')))")
    List<StudyTask> searchTasks(@Param("ownerId") Long ownerId, @Param("q") String q);
}
