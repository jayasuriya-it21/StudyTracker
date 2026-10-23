package com.jayasuriya.studytracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayasuriya.studytracker.exception.Exceptions.BadRequestException;
import com.jayasuriya.studytracker.model.*;
import com.jayasuriya.studytracker.repository.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.util.*;

@Service
public class DataPortabilityService {

    private final StudyTaskRepository taskRepository;
    private final StudySessionRepository sessionRepository;
    private final DsaProblemRepository dsaRepository;
    private final WeeklyReviewRepository reviewRepository;
    private final ProjectRepository projectRepository;
    private final CareerApplicationRepository applicationRepository;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public DataPortabilityService(
            StudyTaskRepository taskRepository,
            StudySessionRepository sessionRepository,
            DsaProblemRepository dsaRepository,
            WeeklyReviewRepository reviewRepository,
            ProjectRepository projectRepository,
            CareerApplicationRepository applicationRepository) {
        this.taskRepository = taskRepository;
        this.sessionRepository = sessionRepository;
        this.dsaRepository = dsaRepository;
        this.reviewRepository = reviewRepository;
        this.projectRepository = projectRepository;
        this.applicationRepository = applicationRepository;
    }

    public Map<String, Object> exportUserJson(User user) {
        Map<String, Object> data = new HashMap<>();
        data.put("version", "1.0");
        data.put("exportDate", new Date());
        data.put("user", Map.of(
                "name", user.getName(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "timezone", user.getTimezone(),
                "weeklyTargetHours", user.getWeeklyTargetHours(),
                "roadmapStartDate", user.getRoadmapStartDate()
        ));

        // Tasks
        List<StudyTask> tasks = taskRepository.findByOwnerId(user.getId());
        List<Map<String, Object>> taskList = new ArrayList<>();
        for (StudyTask t : tasks) {
            taskList.add(Map.of(
                    "title", t.getTitle(),
                    "category", t.getCategory().name(),
                    "priority", t.getPriority().name(),
                    "status", t.getStatus().name(),
                    "actualMinutes", t.getActualMinutes(),
                    "estimateMinutes", t.getEstimateMinutes(),
                    "weekNumber", t.getWeekPlan().getWeekNumber()
            ));
        }
        data.put("tasks", taskList);

        // Sessions
        List<StudySession> sessions = sessionRepository.findByOwnerIdOrderBySessionDateDescCreatedAtDesc(user.getId());
        List<Map<String, Object>> sessionList = new ArrayList<>();
        for (StudySession s : sessions) {
            sessionList.add(Map.of(
                    "date", s.getSessionDate().toString(),
                    "durationMinutes", s.getDurationMinutes(),
                    "category", s.getCategory().name(),
                    "summary", s.getSummary() != null ? s.getSummary() : ""
            ));
        }
        data.put("studySessions", sessionList);

        // DSA Problems
        List<DsaProblem> dsa = dsaRepository.findByOwnerIdOrderByDateAttemptedDesc(user.getId());
        data.put("dsaProblems", dsa);

        // Reviews
        List<WeeklyReview> reviews = reviewRepository.findByOwnerIdOrderByCreatedAtDesc(user.getId());
        data.put("weeklyReviews", reviews);

        // Projects
        List<Project> projects = projectRepository.findByOwnerIdOrderByCreatedAtAsc(user.getId());
        data.put("projects", projects);

        // Applications
        List<CareerApplication> apps = applicationRepository.findByOwnerIdOrderByApplicationDateDesc(user.getId());
        data.put("applications", apps);

        return data;
    }

    public String exportSessionsCsv(User user) {
        try {
            StringWriter sw = new StringWriter();
            CSVFormat format = CSVFormat.Builder.create()
                    .setHeader("Date", "Category", "DurationMinutes", "TaskTitle", "Summary")
                    .build();
            try (CSVPrinter printer = new CSVPrinter(sw, format)) {
                List<StudySession> sessions = sessionRepository.findByOwnerIdOrderBySessionDateDescCreatedAtDesc(user.getId());
                for (StudySession s : sessions) {
                    printer.printRecord(
                            s.getSessionDate(),
                            s.getCategory().name(),
                            s.getDurationMinutes(),
                            s.getTask() != null ? s.getTask().getTitle() : "",
                            s.getSummary() != null ? s.getSummary() : ""
                    );
                }
            }
            return sw.toString();
        } catch (Exception e) {
            throw new BadRequestException("Failed to generate CSV: " + e.getMessage());
        }
    }

    public String exportDsaCsv(User user) {
        try {
            StringWriter sw = new StringWriter();
            CSVFormat format = CSVFormat.Builder.create()
                    .setHeader("Title", "Platform", "TopicPattern", "Difficulty", "DateAttempted", "Status", "Confidence", "CouldSolveWithoutAI")
                    .build();
            try (CSVPrinter printer = new CSVPrinter(sw, format)) {
                List<DsaProblem> list = dsaRepository.findByOwnerIdOrderByDateAttemptedDesc(user.getId());
                for (DsaProblem p : list) {
                    printer.printRecord(
                            p.getTitle(),
                            p.getPlatform(),
                            p.getTopicPattern(),
                            p.getDifficulty().name(),
                            p.getDateAttempted(),
                            p.getStatus().name(),
                            p.getConfidenceScore(),
                            p.isCouldSolveWithoutAi()
                    );
                }
            }
            return sw.toString();
        } catch (Exception e) {
            throw new BadRequestException("Failed to generate DSA CSV: " + e.getMessage());
        }
    }

    @Transactional
    public Map<String, Object> importUserJson(User user, Map<String, Object> data) {
        if (data == null || !data.containsKey("version")) {
            throw new BadRequestException("Invalid backup format. Missing 'version' header.");
        }

        int importedSessions = 0;
        int importedDsa = 0;

        // Import sessions if present
        if (data.containsKey("studySessions")) {
            List<Map<String, Object>> sessions = (List<Map<String, Object>>) data.get("studySessions");
            for (Map<String, Object> sMap : sessions) {
                StudySession s = new StudySession();
                s.setOwner(user);
                s.setCategory(Enums.TaskCategory.valueOf((String) sMap.get("category")));
                s.setDurationMinutes(((Number) sMap.get("durationMinutes")).intValue());
                s.setSessionDate(java.time.LocalDate.parse((String) sMap.get("date")));
                s.setSummary((String) sMap.get("summary"));
                sessionRepository.save(s);
                importedSessions++;
            }
        }

        return Map.of(
                "success", true,
                "message", "Data imported successfully",
                "importedSessions", importedSessions,
                "importedDsa", importedDsa
        );
    }
}
