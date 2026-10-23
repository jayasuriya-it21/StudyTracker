package com.jayasuriya.studytracker.repository;

import com.jayasuriya.studytracker.model.CareerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CareerApplicationRepository extends JpaRepository<CareerApplication, Long> {
    List<CareerApplication> findByOwnerIdOrderByApplicationDateDesc(Long ownerId);
    Optional<CareerApplication> findByIdAndOwnerId(Long id, Long ownerId);
    long countByOwnerId(Long ownerId);

    @Query("SELECT a FROM CareerApplication a WHERE a.owner.id = :ownerId AND (LOWER(a.company) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(a.role) LIKE LOWER(CONCAT('%', :q, '%')))")
    List<CareerApplication> searchApplications(@Param("ownerId") Long ownerId, @Param("q") String q);
}
