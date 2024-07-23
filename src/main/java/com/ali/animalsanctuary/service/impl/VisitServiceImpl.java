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

/**
 * Implementation of the {@link VisitService} interface.
 *
 * This class provides the actual implementation of the methods defined in the {@link VisitService} interface,
 * handling operations related to {@link Visit} entities, including creating, updating, deleting, and retrieving visits.
 * It also supports booking and canceling visits and retrieving available visits.
 *
 * <p>Methods:
 * <ul>
 *     <li>{@link #createVisit(VisitDto)}: Creates a new {@link Visit} entity based on the provided {@link VisitDto} data transfer object.</li>
 *     <li>{@link #updateVisit(VisitDto)}: Updates an existing {@link Visit} entity with new information from the {@link VisitDto} data transfer object.</li>
 *     <li>{@link #deleteVisit(Long)}: Deletes the {@link Visit} entity with the specified ID.</li>
 *     <li>{@link #getAvailableVisits()}: Retrieves a list of all available {@link Visit} entities.</li>
 *     <li>{@link #getVisitById(Long)}: Retrieves an {@link Optional} containing the {@link Visit} entity with the specified ID, if it is available.</li>
 *     <li>{@link #bookVisit(Long, Long)}: Books a visit slot for a user, making it unavailable if the slot is currently available.</li>
 *     <li>{@link #cancelVisit(Long)}: Cancels a booking for a visit slot, making it available again.</li>
 * </ul>
 *
 * @see Visit
 * @see VisitDto
 * @see VisitService
 */

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a new {@link VisitServiceImpl} with the specified {@link VisitRepository} and {@link UserRepository}.
     *
     * @param visitRepository the repository for managing {@link Visit} entities.
     * @param userRepository  the repository for managing {@link User} entities.
     */
    public VisitServiceImpl(VisitRepository visitRepository, UserRepository userRepository) {
        this.visitRepository = visitRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new {@link Visit} entity based on the provided {@link VisitDto} data transfer object.
     *
     * @param visitDto the {@link VisitDto} containing information to create a new {@link Visit}.
     */
    @Override
    public void createVisit(VisitDto visitDto) {
        Visit visit = new Visit();
        visit.setStartTime(visitDto.getStartTime());
        visit.setEndTime(visitDto.getEndTime());
        visit.setAvailable(true);
        visitRepository.save(visit);
    }

    /**
     * Updates an existing {@link Visit} entity with new information from the provided {@link VisitDto} data transfer object.
     *
     * @param visitDto the {@link VisitDto} containing updated information for an existing {@link Visit}.
     * @throws RuntimeException if the visit with the specified ID is not found.
     */
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

    /**
     * Deletes the {@link Visit} entity with the specified ID.
     *
     * @param id the ID of the {@link Visit} entity to be deleted.
     */
    @Override
    public void deleteVisit(Long id) {
        visitRepository.deleteById(id);
    }

    /**
     * Retrieves a list of all available {@link Visit} entities.
     *
     * @return a list of available {@link Visit} entities.
     */
    @Override
    public List<Visit> getAvailableVisits() {
        return visitRepository.findAllByAvailableTrue();
    }

    /**
     * Retrieves an {@link Optional} containing the {@link Visit} entity with the specified ID, if it is available.
     *
     * @param id the ID of the {@link Visit} entity to be retrieved.
     * @return an {@link Optional} containing the {@link Visit} entity, or {@link Optional#empty()} if not found or not available.
     */
    @Override
    public Optional<Visit> getVisitById(Long id) {
        return visitRepository.findByIdAndAvailableTrue(id);
    }

    /**
     * Books a visit slot for a user, making it unavailable if the slot is currently available.
     *
     * @param visitId the ID of the {@link Visit} entity to be booked.
     * @param userId  the ID of the {@link User} entity booking the visit.
     * @throws RuntimeException if the visit or user is not found, or if the visit slot is already booked.
     */
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

    /**
     * Cancels a booking for a visit slot, making it available again.
     *
     * @param visitId the ID of the {@link Visit} entity to be canceled.
     * @throws RuntimeException if the visit with the specified ID is not found.
     */
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
