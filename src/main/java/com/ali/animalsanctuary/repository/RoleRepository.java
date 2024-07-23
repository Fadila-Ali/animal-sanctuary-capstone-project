package com.ali.animalsanctuary.repository;

import com.ali.animalsanctuary.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing {@link Role} entities.
 * Provides CRUD operations inherited from {@link JpaRepository}.
 *
 * Includes custom query method to find a role by its name.
 *
 * @see Role
 * @see JpaRepository
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a {@link Role} entity by its name.
     *
     * @param name the name of the role.
     * @return the {@link Role} entity with the specified name.
     */

    Role findByName(String name);
}
