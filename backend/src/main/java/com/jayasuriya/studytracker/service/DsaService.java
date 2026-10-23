package com.jayasuriya.studytracker.service;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.exception.Exceptions.ResourceNotFoundException;
import com.jayasuriya.studytracker.model.*;
import com.jayasuriya.studytracker.repository.DsaProblemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DsaService {

    private final DsaProblemRepository dsaRepository;

    public DsaService(DsaProblemRepository dsaRepository) {
        this.dsaRepository = dsaRepository;
    }

    @Transactional
    public DsaProblemDto createProblem(User user, CreateDsaRequest req) {
        DsaProblem p = new DsaProblem();
        p.setOwner(user);
        p.setTitle(req.title());
        p.setPlatform(req.platform() != null ? req.platform() : "LeetCode");
        p.setUrl(req.url());
        p.setTopicPattern(req.topicPattern());
        p.setDifficulty(req.difficulty() != null ? req.difficulty() : Enums.Difficulty.MEDIUM);
        p.setDateAttempted(req.dateAttempted());
        p.setStatus(req.status() != null ? req.status() : Enums.DsaStatus.SOLVED);
        p.setAttemptCount(req.attemptCount() != null ? req.attemptCount() : 1);
        p.setTimeTakenMinutes(req.timeTakenMinutes());
        p.setHintsUsed(req.hintsUsed() != null ? req.hintsUsed() : 0);
        p.setCouldSolveWithoutAi(req.couldSolveWithoutAi() != null ? req.couldSolveWithoutAi() : true);
        p.setComplexityExplanation(req.complexityExplanation());
        p.setNotes(req.notes());
        p.setNextReviewDate(req.nextReviewDate() != null ? req.nextReviewDate() : req.dateAttempted().plusDays(7));
        p.setConfidenceScore(req.confidenceScore() != null ? req.confidenceScore() : 3);

        p = dsaRepository.save(p);
        return toDto(p);
    }

    @Transactional
    public DsaProblemDto updateProblem(User user, Long id, UpdateDsaRequest req) {
        DsaProblem p = dsaRepository.findByIdAndOwnerId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("DSA problem not found"));

        if (req.title() != null && !req.title().isBlank()) p.setTitle(req.title());
        if (req.platform() != null) p.setPlatform(req.platform());
        if (req.url() != null) p.setUrl(req.url());
        if (req.topicPattern() != null) p.setTopicPattern(req.topicPattern());
        if (req.difficulty() != null) p.setDifficulty(req.difficulty());
        if (req.dateAttempted() != null) p.setDateAttempted(req.dateAttempted());
        if (req.status() != null) p.setStatus(req.status());
        if (req.attemptCount() != null) p.setAttemptCount(req.attemptCount());
        if (req.timeTakenMinutes() != null) p.setTimeTakenMinutes(req.timeTakenMinutes());
        if (req.hintsUsed() != null) p.setHintsUsed(req.hintsUsed());
        if (req.couldSolveWithoutAi() != null) p.setCouldSolveWithoutAi(req.couldSolveWithoutAi());
        if (req.complexityExplanation() != null) p.setComplexityExplanation(req.complexityExplanation());
        if (req.notes() != null) p.setNotes(req.notes());
        if (req.nextReviewDate() != null) p.setNextReviewDate(req.nextReviewDate());
        if (req.confidenceScore() != null) p.setConfidenceScore(req.confidenceScore());

        p = dsaRepository.save(p);
        return toDto(p);
    }

    @Transactional
    public void deleteProblem(User user, Long id) {
        DsaProblem p = dsaRepository.findByIdAndOwnerId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("DSA problem not found"));
        dsaRepository.delete(p);
    }

    public List<DsaProblemDto> getProblems(User user) {
        return dsaRepository.findByOwnerIdOrderByDateAttemptedDesc(user.getId())
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<DsaProblemDto> getRevisionQueue(User user) {
        return dsaRepository.findRevisionQueue(user.getId(), LocalDate.now())
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public DsaStatsDto getStats(User user) {
        List<DsaProblem> list = dsaRepository.findByOwnerIdOrderByDateAttemptedDesc(user.getId());

        long solved = list.stream().filter(p -> p.getStatus() == Enums.DsaStatus.SOLVED).count();
        long attempted = list.size();

        Map<Enums.Difficulty, Long> byDiff = list.stream()
                .collect(Collectors.groupingBy(DsaProblem::getDifficulty, Collectors.counting()));

        Map<String, Long> byTopic = list.stream()
                .collect(Collectors.groupingBy(DsaProblem::getTopicPattern, Collectors.counting()));

        // Weak topics: where average confidence <= 2.5 or unsolved > 0
        List<String> weakTopics = list.stream()
                .filter(p -> p.getConfidenceScore() <= 2 || !p.isCouldSolveWithoutAi())
                .map(DsaProblem::getTopicPattern)
                .distinct()
                .collect(Collectors.toList());

        long dueCount = list.stream()
                .filter(p -> p.getNextReviewDate() != null && !p.getNextReviewDate().isAfter(LocalDate.now()))
                .count();

        return new DsaStatsDto(solved, attempted, 120, 160, byDiff, byTopic, weakTopics, dueCount);
    }

    public DsaProblemDto toDto(DsaProblem p) {
        return new DsaProblemDto(
                p.getId(),
                p.getTitle(),
                p.getPlatform(),
                p.getUrl(),
                p.getTopicPattern(),
                p.getDifficulty(),
                p.getDateAttempted(),
                p.getStatus(),
                p.getAttemptCount(),
                p.getTimeTakenMinutes(),
                p.getHintsUsed(),
                p.isCouldSolveWithoutAi(),
                p.getComplexityExplanation(),
                p.getNotes(),
                p.getNextReviewDate(),
                p.getConfidenceScore()
        );
    }
}
