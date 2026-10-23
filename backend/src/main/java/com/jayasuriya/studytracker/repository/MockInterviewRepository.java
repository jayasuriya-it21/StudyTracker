package com.jayasuriya.studytracker.repository;

import com.jayasuriya.studytracker.model.MockInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MockInterviewRepository extends JpaRepository<MockInterview, Long> {
    List<MockInterview> findByOwnerIdOrderByInterviewDateDesc(Long ownerId);
    Optional<MockInterview> findByIdAndOwnerId(Long id, Long ownerId);
}
