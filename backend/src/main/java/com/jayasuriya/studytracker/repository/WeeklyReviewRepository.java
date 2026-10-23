package com.jayasuriya.studytracker.repository;

import com.jayasuriya.studytracker.model.WeeklyReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklyReviewRepository extends JpaRepository<WeeklyReview, Long> {
    Optional<WeeklyReview> findByOwnerIdAndWeekPlanId(Long ownerId, Long weekId);
    List<WeeklyReview> findByOwnerIdOrderByCreatedAtDesc(Long ownerId);
    Optional<WeeklyReview> findByIdAndOwnerId(Long id, Long ownerId);
}
