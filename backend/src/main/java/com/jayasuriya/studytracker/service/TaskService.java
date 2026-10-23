package com.jayasuriya.studytracker.service;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.exception.Exceptions.BadRequestException;
import com.jayasuriya.studytracker.exception.Exceptions.ResourceNotFoundException;
import com.jayasuriya.studytracker.model.*;
import com.jayasuriya.studytracker.repository.StudyTaskRepository;
import com.jayasuriya.studytracker.repository.WeekPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TaskService {

    private final StudyTaskRepository taskRepository;
    private final WeekPlanRepository weekPlanRepository;
    private final RoadmapService roadmapService;

    public TaskService(
            StudyTaskRepository taskRepository,
            WeekPlanRepository weekPlanRepository,
            RoadmapService roadmapService) {
        this.taskRepository = taskRepository;
        this.weekPlanRepository = weekPlanRepository;
        this.roadmapService = roadmapService;
    }

    @Transactional
    public TaskDto createTask(User user, CreateTaskRequest req) {
        WeekPlan week = weekPlanRepository.findById(req.weekId())
                .orElseThrow(() -> new ResourceNotFoundException("Week not found"));

        if (!week.getRoadmap().getOwner().getId().equals(user.getId())) {
            throw new BadRequestException("You do not own this roadmap");
        }

        List<StudyTask> existing = taskRepository.findByWeekPlanIdAndOwnerIdOrderBySortOrderAsc(week.getId(), user.getId());

        StudyTask task = new StudyTask();
        task.setWeekPlan(week);
        task.setOwner(user);
        task.setTitle(req.title());
        task.setDescription(req.description());
        task.setCategory(req.category());
        task.setPriority(req.priority() != null ? req.priority() : Enums.Priority.MEDIUM);
        task.setEstimateMinutes(req.estimateMinutes() != null ? req.estimateMinutes() : 60);
        task.setDueDate(req.dueDate() != null ? req.dueDate() : week.getEndDate());
        task.setStatus(Enums.TaskStatus.NOT_STARTED);
        task.setSource(Enums.TaskSource.CUSTOM);
        task.setSortOrder(existing.size() + 1);

        task = taskRepository.save(task);
        updateWeekPlanCompletion(week.getId(), user.getId());

        return roadmapService.toTaskDto(task);
    }

    @Transactional
    public TaskDto updateTask(User user, Long taskId, UpdateTaskRequest req) {
        StudyTask task = taskRepository.findByIdAndOwnerId(taskId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (req.title() != null && !req.title().isBlank()) task.setTitle(req.title());
        if (req.description() != null) task.setDescription(req.description());
        if (req.category() != null) task.setCategory(req.category());
        if (req.priority() != null) task.setPriority(req.priority());
        if (req.estimateMinutes() != null) task.setEstimateMinutes(req.estimateMinutes());
        if (req.actualMinutes() != null) task.setActualMinutes(req.actualMinutes());
        if (req.dueDate() != null) task.setDueDate(req.dueDate());
        if (req.sortOrder() != null) task.setSortOrder(req.sortOrder());

        if (req.status() != null) {
            task.setStatus(req.status());
            if (req.status() == Enums.TaskStatus.COMPLETED && task.getCompletedAt() == null) {
                task.setCompletedAt(OffsetDateTime.now());
            } else if (req.status() != Enums.TaskStatus.COMPLETED) {
                task.setCompletedAt(null);
            }
        }

        task = taskRepository.save(task);
        updateWeekPlanCompletion(task.getWeekPlan().getId(), user.getId());

        return roadmapService.toTaskDto(task);
    }

    @Transactional
    public TaskDto toggleTask(User user, Long taskId) {
        StudyTask task = taskRepository.findByIdAndOwnerId(taskId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (task.getStatus() == Enums.TaskStatus.COMPLETED) {
            task.setStatus(Enums.TaskStatus.NOT_STARTED);
            task.setCompletedAt(null);
        } else {
            task.setStatus(Enums.TaskStatus.COMPLETED);
            task.setCompletedAt(OffsetDateTime.now());
        }

        task = taskRepository.save(task);
        updateWeekPlanCompletion(task.getWeekPlan().getId(), user.getId());

        return roadmapService.toTaskDto(task);
    }

    @Transactional
    public void deleteTask(User user, Long taskId) {
        StudyTask task = taskRepository.findByIdAndOwnerId(taskId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        Long weekId = task.getWeekPlan().getId();
        taskRepository.delete(task);
        updateWeekPlanCompletion(weekId, user.getId());
    }

    @Transactional
    public TaskDto carryForwardTask(User user, Long taskId, Long targetWeekId) {
        StudyTask task = taskRepository.findByIdAndOwnerId(taskId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        WeekPlan targetWeek = weekPlanRepository.findById(targetWeekId)
                .orElseThrow(() -> new ResourceNotFoundException("Target week not found"));

        if (!targetWeek.getRoadmap().getOwner().getId().equals(user.getId())) {
            throw new BadRequestException("You do not own target week");
        }

        // Mark original task as carried forward
        task.setStatus(Enums.TaskStatus.CARRIED_FORWARD);
        taskRepository.save(task);

        // Create new task in target week with audit note
        StudyTask carried = new StudyTask();
        carried.setWeekPlan(targetWeek);
        carried.setOwner(user);
        carried.setTitle(task.getTitle());
        String auditDesc = (task.getDescription() != null ? task.getDescription() + "\n" : "") +
                "[Carried forward from Week " + task.getWeekPlan().getWeekNumber() + "]";
        carried.setDescription(auditDesc);
        carried.setCategory(task.getCategory());
        carried.setPriority(task.getPriority());
        carried.setEstimateMinutes(task.getEstimateMinutes());
        carried.setActualMinutes(0);
        carried.setDueDate(targetWeek.getEndDate());
        carried.setStatus(Enums.TaskStatus.NOT_STARTED);
        carried.setSource(Enums.TaskSource.CUSTOM);

        List<StudyTask> targetTasks = taskRepository.findByWeekPlanIdAndOwnerIdOrderBySortOrderAsc(targetWeek.getId(), user.getId());
        carried.setSortOrder(targetTasks.size() + 1);

        carried = taskRepository.save(carried);

        updateWeekPlanCompletion(task.getWeekPlan().getId(), user.getId());
        updateWeekPlanCompletion(targetWeek.getId(), user.getId());

        return roadmapService.toTaskDto(carried);
    }

    private void updateWeekPlanCompletion(Long weekId, Long userId) {
        List<StudyTask> tasks = taskRepository.findByWeekPlanIdAndOwnerIdOrderBySortOrderAsc(weekId, userId);
        int total = tasks.size();
        int completed = (int) tasks.stream().filter(t -> t.getStatus() == Enums.TaskStatus.COMPLETED).count();
        int pct = total > 0 ? (int) Math.round((double) completed / total * 100) : 0;

        weekPlanRepository.findById(weekId).ifPresent(w -> {
            w.setCompletionPercentage(pct);
            if (pct == 100) w.setStatus(Enums.TaskStatus.COMPLETED);
            else if (pct > 0) w.setStatus(Enums.TaskStatus.IN_PROGRESS);
            else w.setStatus(Enums.TaskStatus.NOT_STARTED);
            weekPlanRepository.save(w);
        });
    }
}
