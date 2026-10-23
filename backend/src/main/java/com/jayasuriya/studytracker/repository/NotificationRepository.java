package com.jayasuriya.studytracker.repository;

import com.jayasuriya.studytracker.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByOwnerIdOrderByCreatedAtDesc(Long ownerId);
    List<Notification> findByOwnerIdAndIsReadFalse(Long ownerId);
    Optional<Notification> findByIdAndOwnerId(Long id, Long ownerId);
}
