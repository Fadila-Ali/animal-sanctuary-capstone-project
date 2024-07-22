package com.ali.animalsanctuary.service.impl;

import com.ali.animalsanctuary.dto.VisitDto;
import com.ali.animalsanctuary.entity.User;
import com.ali.animalsanctuary.entity.Visit;
import com.ali.animalsanctuary.repository.UserRepository;
import com.ali.animalsanctuary.repository.VisitRepository;
import com.ali.animalsanctuary.service.VisitService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final UserRepository userRepository;

    public VisitServiceImpl(VisitRepository visitRepository, UserRepository userRepository) {
        this.visitRepository = visitRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createVisit(VisitDto visitDto) {
        Visit visit = new Visit();
        visit.setStartTime(visitDto.getStartTime());
        visit.setEndTime(visitDto.getEndTime());
        visit.setAvailable(true);
        visitRepository.save(visit);
    }

    @Override
    public void updateVisit(VisitDto visitDto) {
        Optional<Visit> optionalVisit = visitRepository.findById(visitDto.getId());
        if (optionalVisit.isPresent()) {
            Visit visit = optionalVisit.get();
            visit.setStartTime(visitDto.getStartTime());
            visit.setEndTime(visitDto.getEndTime());
            visitRepository.save(visit);
        } else {
            throw new RuntimeException("Visit not found");
        }
    }

    @Override
    public void deleteVisit(Long id) {
        visitRepository.deleteById(id);
    }

    @Override
    public List<Visit> getAvailableVisits() {
        return visitRepository.findAllByAvailableTrue();
    }

    @Override
    public Optional<Visit> getVisitById(Long id) {
        return visitRepository.findByIdAndAvailableTrue(id);
    }

    @Override
    public void bookVisit(Long visitId, Long userId) {
        Optional<Visit> optionalVisit = visitRepository.findById(visitId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalVisit.isPresent() && optionalUser.isPresent()) {
            Visit visit = optionalVisit.get();
            if (visit.isAvailable()) {
                visit.setUser(optionalUser.get());
                visit.setAvailable(false);
                visitRepository.save(visit);
            } else {
                throw new RuntimeException("Visit slot is already booked");
            }
        } else {
            throw new RuntimeException("Visit or User not found");
        }
    }

    @Override
    public void cancelVisit(Long visitId) {
        Optional<Visit> optionalVisit = visitRepository.findById(visitId);
        if (optionalVisit.isPresent()) {
            Visit visit = optionalVisit.get();
            visit.setUser(null);
            visit.setAvailable(true);
            visitRepository.save(visit);
        } else {
            throw new RuntimeException("Visit not found");
        }
    }
}
