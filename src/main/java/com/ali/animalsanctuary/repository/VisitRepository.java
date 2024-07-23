package com.ali.animalsanctuary.repository;

import com.ali.animalsanctuary.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and managing {@link Visit} entities.
 * Provides CRUD operations inherited from {@link JpaRepository}.
 *
 * Includes custom query methods to find visits that are available and by ID if available.
 *
 * @see Visit
 * @see JpaRepository
 */

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    /**
     * Finds all {@link Visit} entities that are currently available.
     *
     * @return a list of available {@link Visit} entities.
     */
    List<Visit> findAllByAvailableTrue();

    /**
     * Finds a {@link Visit} entity by its ID if it is available.
     *
     * @param id the ID of the visit.
     * @return an {@link Optional} containing the {@link Visit} entity if found and available.
     */
    Optional<Visit> findByIdAndAvailableTrue(Long id);
}
