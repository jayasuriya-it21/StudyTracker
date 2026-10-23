package com.jayasuriya.studytracker.service;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.exception.Exceptions.BadRequestException;
import com.jayasuriya.studytracker.exception.Exceptions.ResourceNotFoundException;
import com.jayasuriya.studytracker.model.*;
import com.jayasuriya.studytracker.repository.StudySessionRepository;
import com.jayasuriya.studytracker.repository.WeekPlanRepository;
import com.jayasuriya.studytracker.repository.WeeklyReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;

@Service
public class WeeklyReviewService {

    private final WeeklyReviewRepository reviewRepository;
    private final WeekPlanRepository weekPlanRepository;
    private final StudySessionRepository sessionRepository;
    private final RoadmapService roadmapService;

    public WeeklyReviewService(
            WeeklyReviewRepository reviewRepository,
            WeekPlanRepository weekPlanRepository,
            StudySessionRepository sessionRepository,
            RoadmapService roadmapService) {
        this.reviewRepository = reviewRepository;
        this.weekPlanRepository = weekPlanRepository;
        this.sessionRepository = sessionRepository;
        this.roadmapService = roadmapService;
    }

    public WeeklyReviewDto getReview(User user, Long weekId) {
        return reviewRepository.findByOwnerIdAndWeekPlanId(user.getId(), weekId)
                .map(roadmapService::toReviewDto)
                .orElse(null);
    }

    @Transactional
    public WeeklyReviewDto saveReview(User user, SaveReviewRequest req) {
        WeekPlan week = weekPlanRepository.findById(req.weekId())
                .orElseThrow(() -> new ResourceNotFoundException("Week not found"));

        if (!week.getRoadmap().getOwner().getId().equals(user.getId())) {
            throw new BadRequestException("You do not own this week");
        }

        WeeklyReview review = reviewRepository.findByOwnerIdAndWeekPlanId(user.getId(), week.getId())
                .orElseGet(() -> {
                    WeeklyReview r = new WeeklyReview();
                    r.setOwner(user);
                    r.setWeekPlan(week);
                    return r;
                });

        if (req.whatStudied() != null) review.setWhatStudied(req.whatStudied());
        if (req.whatCompleted() != null) review.setWhatCompleted(req.whatCompleted());
        if (req.whatAiGotRight() != null) review.setWhatAiGotRight(req.whatAiGotRight());
        if (req.whatAiGotWrong() != null) review.setWhatAiGotWrong(req.whatAiGotWrong());
        if (req.whatVerifiedIndependently() != null) review.setWhatVerifiedIndependently(req.whatVerifiedIndependently());
        if (req.wins() != null) review.setWins(req.wins());
        if (req.blockers() != null) review.setBlockers(req.blockers());
        if (req.areasToImprove() != null) review.setAreasToImprove(req.areasToImprove());
        if (req.nextWeekPriorities() != null) review.setNextWeekPriorities(req.nextWeekPriorities());
        if (req.selfRating() != null) review.setSelfRating(req.selfRating());

        // Automatically calculate actual hours from study sessions during this week
        Integer totalMins = sessionRepository.getTotalMinutesBetween(user.getId(), week.getStartDate(), week.getEndDate());
        BigDecimal totalHours = BigDecimal.valueOf(totalMins != null ? totalMins : 0)
                .divide(BigDecimal.valueOf(60), 1, RoundingMode.HALF_UP);
        review.setTotalActualHours(totalHours);

        if (Boolean.TRUE.equals(req.finalizeReview())) {
            review.setStatus(Enums.ReviewStatus.FINALIZED);
            review.setFinalizedAt(OffsetDateTime.now());
        } else if (review.getStatus() == null) {
            review.setStatus(Enums.ReviewStatus.DRAFT);
        }

        review = reviewRepository.save(review);
        return roadmapService.toReviewDto(review);
    }
}
