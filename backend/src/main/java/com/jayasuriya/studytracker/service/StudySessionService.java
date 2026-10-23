package com.jayasuriya.studytracker.service;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.exception.Exceptions.BadRequestException;
import com.jayasuriya.studytracker.exception.Exceptions.ResourceNotFoundException;
import com.jayasuriya.studytracker.model.*;
import com.jayasuriya.studytracker.repository.StudySessionRepository;
import com.jayasuriya.studytracker.repository.StudyTaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudySessionService {

    private final StudySessionRepository sessionRepository;
    private final StudyTaskRepository taskRepository;

    public StudySessionService(
            StudySessionRepository sessionRepository,
            StudyTaskRepository taskRepository) {
        this.sessionRepository = sessionRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public SessionDto logSession(User user, CreateSessionRequest req) {
        if (req.durationMinutes() <= 0) {
            throw new BadRequestException("Duration must be greater than 0 minutes");
        }

        StudySession session = new StudySession();
        session.setOwner(user);
        session.setCategory(req.category());
        session.setSessionDate(req.sessionDate());
        session.setDurationMinutes(req.durationMinutes());
        session.setSummary(req.summary());
        session.setInterruptionNotes(req.interruptionNotes());

        if (req.taskId() != null) {
            StudyTask task = taskRepository.findByIdAndOwnerId(req.taskId(), user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
            session.setTask(task);
            task.setActualMinutes(task.getActualMinutes() + req.durationMinutes());
            taskRepository.save(task);
        }

        session = sessionRepository.save(session);
        return toDto(session);
    }

    public List<SessionDto> getSessions(User user) {
        return sessionRepository.findByOwnerIdOrderBySessionDateDescCreatedAtDesc(user.getId())
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public SessionStatsDto getStats(User user) {
        List<StudySession> allSessions = sessionRepository.findByOwnerIdOrderBySessionDateDescCreatedAtDesc(user.getId());

        int totalMins = allSessions.stream().mapToInt(StudySession::getDurationMinutes).sum();
        BigDecimal totalHours = toHours(totalMins);

        LocalDate today = LocalDate.now();
        LocalDate weekAgo = today.minusDays(7);
        int weeklyMins = allSessions.stream()
                .filter(s -> !s.getSessionDate().isBefore(weekAgo) && !s.getSessionDate().isAfter(today))
                .mapToInt(StudySession::getDurationMinutes).sum();
        BigDecimal weeklyHours = toHours(weeklyMins);

        LocalDate monthAgo = today.minusDays(30);
        int monthlyMins = allSessions.stream()
                .filter(s -> !s.getSessionDate().isBefore(monthAgo) && !s.getSessionDate().isAfter(today))
                .mapToInt(StudySession::getDurationMinutes).sum();
        BigDecimal monthlyHours = toHours(monthlyMins);

        Map<Enums.TaskCategory, BigDecimal> byCategory = new HashMap<>();
        for (Enums.TaskCategory cat : Enums.TaskCategory.values()) {
            int catMins = allSessions.stream()
                    .filter(s -> s.getCategory() == cat)
                    .mapToInt(StudySession::getDurationMinutes).sum();
            if (catMins > 0) {
                byCategory.put(cat, toHours(catMins));
            }
        }

        return new SessionStatsDto(totalHours, weeklyHours, monthlyHours, byCategory);
    }

    private BigDecimal toHours(int minutes) {
        return BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 1, RoundingMode.HALF_UP);
    }

    private SessionDto toDto(StudySession s) {
        return new SessionDto(
                s.getId(),
                s.getTask() != null ? s.getTask().getId() : null,
                s.getTask() != null ? s.getTask().getTitle() : null,
                s.getCategory(),
                s.getSessionDate(),
                s.getDurationMinutes(),
                s.getSummary(),
                s.getInterruptionNotes(),
                s.getCreatedAt()
        );
    }
}
