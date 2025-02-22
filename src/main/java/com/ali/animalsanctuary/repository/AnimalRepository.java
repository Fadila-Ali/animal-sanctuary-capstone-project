package com.ali.animalsanctuary.repository;

import com.ali.animalsanctuary.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing {@link Animal} entities.
 * Provides CRUD operations inherited from {@link JpaRepository}.
 *
 * The repository will be automatically implemented by Spring Data JPA.
 *
 * @see Animal
 * @see JpaRepository
 */

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
