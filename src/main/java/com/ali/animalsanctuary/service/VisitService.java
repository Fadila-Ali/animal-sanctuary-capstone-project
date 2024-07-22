package com.ali.animalsanctuary.service;

import com.ali.animalsanctuary.entity.Visit;
import com.ali.animalsanctuary.dto.VisitDto;

import java.util.List;
import java.util.Optional;

public interface VisitService {

    void createVisit(VisitDto visitDto);
    void updateVisit(VisitDto visitDto);
    void deleteVisit(Long id);
    List<Visit> getAvailableVisits();
    Optional<Visit> getVisitById(Long id);
    void bookVisit(Long visitId, Long userId);
    void cancelVisit(Long visitId);
}
