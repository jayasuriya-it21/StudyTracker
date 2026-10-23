package com.jayasuriya.studytracker.controller;

import com.jayasuriya.studytracker.model.Notification;
import com.jayasuriya.studytracker.model.User;
import com.jayasuriya.studytracker.repository.NotificationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notifications", description = "System alerts, study reminders, and review prompts")
public class NotificationController {

    public record NotificationDto(
            Long id,
            String title,
            String message,
            String notificationType,
            boolean isRead,
            OffsetDateTime createdAt
    ) {}

    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @GetMapping
    @Operation(summary = "Get user's notifications")
    public ResponseEntity<List<NotificationDto>> getNotifications(@AuthenticationPrincipal User user) {
        List<NotificationDto> dtos = notificationRepository.findByOwnerIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(n -> new NotificationDto(
                        n.getId(),
                        n.getTitle(),
                        n.getMessage(),
                        n.getNotificationType(),
                        n.isRead(),
                        n.getCreatedAt()
                ))
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PatchMapping("/{id}/read")
    @Operation(summary = "Mark a notification as read")
    public ResponseEntity<Void> markAsRead(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        notificationRepository.findByIdAndOwnerId(id, user.getId()).ifPresent(n -> {
            n.setRead(true);
            notificationRepository.save(n);
        });
        return ResponseEntity.noContent().build();
    }
}
