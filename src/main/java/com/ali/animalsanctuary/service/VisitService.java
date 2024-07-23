package com.ali.animalsanctuary.service;

import com.ali.animalsanctuary.entity.Visit;
import com.ali.animalsanctuary.dto.VisitDto;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link Visit} entities.
 *
 * This interface defines the operations available for handling visit records, including creation, updating, deletion,
 * and retrieval of visits. It also supports booking and canceling visits.
 *
 * <p>Methods:
 * <ul>
 *     <li>{@link #createVisit(VisitDto)}: Creates a new visit based on the provided {@link VisitDto}.</li>
 *     <li>{@link #updateVisit(VisitDto)}: Updates an existing visit based on the provided {@link VisitDto}.</li>
 *     <li>{@link #deleteVisit(Long)}: Deletes the {@link Visit} entity with the specified ID.</li>
 *     <li>{@link #getAvailableVisits()} : Retrieves a list of all available {@link Visit} entities.</li>
 *     <li>{@link #getVisitById(Long)}: Retrieves an {@link Optional} containing the {@link Visit} entity with the specified ID.</li>
 *     <li>{@link #bookVisit(Long, Long)}: Books a visit for a user with the specified ID.</li>
 *     <li>{@link #cancelVisit(Long)}: Cancels the visit with the specified ID.</li>
 * </ul>
 *
 * @see Visit
 * @see VisitDto
 */

public interface VisitService {

    /**
     * Creates a new visit based on the provided {@link VisitDto}.
     *
     * @param visitDto the {@link VisitDto} containing the details of the visit to be created.
     */
    void createVisit(VisitDto visitDto);

    /**
     * Updates an existing visit based on the provided {@link VisitDto}.
     *
     * @param visitDto the {@link VisitDto} containing the updated details of the visit.
     */
    void updateVisit(VisitDto visitDto);

    /**
     * Deletes the {@link Visit} entity with the specified ID.
     *
     * @param id the ID of the {@link Visit} entity to be deleted.
     */
    void deleteVisit(Long id);

    /**
     * Retrieves a list of all available {@link Visit} entities.
     *
     * @return a list of all available {@link Visit} entities.
     */
    List<Visit> getAvailableVisits();

    /**
     * Retrieves an {@link Optional} containing the {@link Visit} entity with the specified ID.
     *
     * @param id the ID of the {@link Visit} entity to be retrieved.
     * @return an {@link Optional} containing the {@link Visit} entity with the specified ID, or an empty {@link Optional} if not found.
     */
    Optional<Visit> getVisitById(Long id);

    /**
     * Books a visit for a user with the specified ID.
     *
     * @param visitId the ID of the visit to be booked.
     * @param userId  the ID of the user booking the visit.
     */
    void bookVisit(Long visitId, Long userId);

    /**
     * Cancels the visit with the specified ID.
     *
     * @param visitId the ID of the visit to be canceled.
     */
    void cancelVisit(Long visitId);
}
