package com.jayasuriya.studytracker.repository;

import com.jayasuriya.studytracker.model.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Long> {
    List<Phase> findByRoadmapIdOrderByDisplayOrderAsc(Long roadmapId);
}
