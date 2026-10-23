package com.jayasuriya.studytracker.service;

import com.jayasuriya.studytracker.dto.Dtos.*;
import com.jayasuriya.studytracker.exception.Exceptions.ResourceNotFoundException;
import com.jayasuriya.studytracker.model.*;
import com.jayasuriya.studytracker.repository.CareerApplicationRepository;
import com.jayasuriya.studytracker.repository.MockInterviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CareerService {

    private final CareerApplicationRepository applicationRepository;
    private final MockInterviewRepository interviewRepository;

    public CareerService(
            CareerApplicationRepository applicationRepository,
            MockInterviewRepository interviewRepository) {
        this.applicationRepository = applicationRepository;
        this.interviewRepository = interviewRepository;
    }

    public List<CareerApplicationDto> getApplications(User user) {
        return applicationRepository.findByOwnerIdOrderByApplicationDateDesc(user.getId())
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public CareerApplicationDto createApplication(User user, CreateApplicationRequest req) {
        CareerApplication a = new CareerApplication();
        a.setOwner(user);
        a.setCompany(req.company());
        a.setRole(req.role());
        a.setJobUrl(req.jobUrl());
        a.setSourceReferral(req.sourceReferral());
        a.setApplicationDate(req.applicationDate());
        a.setStatus(req.status() != null ? req.status() : Enums.ApplicationStatus.APPLIED);
        a.setInterviewStage(req.interviewStage());
        a.setNoticePeriodCompatibility(req.noticePeriodCompatibility());
        a.setFixedCompensation(req.fixedCompensation());
        a.setVariableCompensation(req.variableCompensation());
        a.setEquity(req.equity());
        a.setNotes(req.notes());
        a.setNextActionDate(req.nextActionDate());

        a = applicationRepository.save(a);
        return toDto(a);
    }

    @Transactional
    public CareerApplicationDto updateApplication(User user, Long id, UpdateApplicationRequest req) {
        CareerApplication a = applicationRepository.findByIdAndOwnerId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Career application not found"));

        if (req.company() != null && !req.company().isBlank()) a.setCompany(req.company());
        if (req.role() != null && !req.role().isBlank()) a.setRole(req.role());
        if (req.jobUrl() != null) a.setJobUrl(req.jobUrl());
        if (req.sourceReferral() != null) a.setSourceReferral(req.sourceReferral());
        if (req.applicationDate() != null) a.setApplicationDate(req.applicationDate());
        if (req.status() != null) a.setStatus(req.status());
        if (req.interviewStage() != null) a.setInterviewStage(req.interviewStage());
        if (req.noticePeriodCompatibility() != null) a.setNoticePeriodCompatibility(req.noticePeriodCompatibility());
        if (req.fixedCompensation() != null) a.setFixedCompensation(req.fixedCompensation());
        if (req.variableCompensation() != null) a.setVariableCompensation(req.variableCompensation());
        if (req.equity() != null) a.setEquity(req.equity());
        if (req.notes() != null) a.setNotes(req.notes());
        if (req.nextActionDate() != null) a.setNextActionDate(req.nextActionDate());

        a = applicationRepository.save(a);
        return toDto(a);
    }

    @Transactional
    public void deleteApplication(User user, Long id) {
        CareerApplication a = applicationRepository.findByIdAndOwnerId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Career application not found"));
        applicationRepository.delete(a);
    }

    public List<MockInterviewDto> getMockInterviews(User user) {
        return interviewRepository.findByOwnerIdOrderByInterviewDateDesc(user.getId())
                .stream().map(this::toInterviewDto).collect(Collectors.toList());
    }

    @Transactional
    public MockInterviewDto createMockInterview(User user, CreateMockInterviewRequest req) {
        MockInterview m = new MockInterview();
        m.setOwner(user);
        m.setInterviewType(req.interviewType());
        m.setInterviewDate(req.interviewDate());
        m.setDurationMinutes(req.durationMinutes());
        m.setScore(req.score());
        m.setStrengths(req.strengths());
        m.setWeaknesses(req.weaknesses());
        m.setFeedback(req.feedback());
        m.setNextActions(req.nextActions());

        m = interviewRepository.save(m);
        return toInterviewDto(m);
    }

    public CareerApplicationDto toDto(CareerApplication a) {
        return new CareerApplicationDto(
                a.getId(),
                a.getCompany(),
                a.getRole(),
                a.getJobUrl(),
                a.getSourceReferral(),
                a.getApplicationDate(),
                a.getStatus(),
                a.getInterviewStage(),
                a.getNoticePeriodCompatibility(),
                a.getFixedCompensation(),
                a.getVariableCompensation(),
                a.getEquity(),
                a.getNotes(),
                a.getNextActionDate()
        );
    }

    public MockInterviewDto toInterviewDto(MockInterview m) {
        return new MockInterviewDto(
                m.getId(),
                m.getInterviewType(),
                m.getInterviewDate(),
                m.getDurationMinutes(),
                m.getScore(),
                m.getStrengths(),
                m.getWeaknesses(),
                m.getFeedback(),
                m.getNextActions()
        );
    }
}
