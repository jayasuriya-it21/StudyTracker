package com.jayasuriya.studytracker.repository;

import com.jayasuriya.studytracker.model.WeekPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeekPlanRepository extends JpaRepository<WeekPlan, Long> {
    List<WeekPlan> findByRoadmapIdOrderByWeekNumberAsc(Long roadmapId);
    Optional<WeekPlan> findByRoadmapIdAndWeekNumber(Long roadmapId, int weekNumber);
}
