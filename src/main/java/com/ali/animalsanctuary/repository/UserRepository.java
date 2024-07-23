package com.ali.animalsanctuary.repository;

import com.ali.animalsanctuary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing and managing {@link User} entities.
 * Provides CRUD operations inherited from {@link JpaRepository}.
 *
 * Includes custom query methods to find users by email and username.
 *
 * @see User
 * @see JpaRepository
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a {@link User} entity by its email.
     *
     * @param email the email of the user.
     * @return the {@link User} entity with the specified email.
     */
    User findByEmail(String email);

    /**
     * Finds a {@link User} entity by its username.
     *
     * @param username the username of the user.
     * @return an {@link Optional} containing the {@link User} entity if found.
     */
    Optional<User> findByUsername(String username);
}
