package com.ali.animalsanctuary.repository;

import com.ali.animalsanctuary.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing {@link Volunteer} entities.
 * Provides CRUD operations inherited from {@link JpaRepository}.
 *
 * The repository will be automatically implemented by Spring Data JPA.
 *
 * @see Volunteer
 * @see JpaRepository
 */


@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
