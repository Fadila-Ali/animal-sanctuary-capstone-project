package com.ali.animalsanctuary.service;

import com.ali.animalsanctuary.dto.UserDto;
import com.ali.animalsanctuary.entity.User;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link User} entities.
 *
 * This interface defines the operations available for handling user records, including saving, updating,
 * deleting, and retrieving users. It also includes methods for converting between {@link User} entities and {@link UserDto} data transfer objects.
 *
 * <p>Methods:
 * <ul>
 *     <li>{@link #saveUser(UserDto)}: Saves a new {@link User} entity based on the provided {@link UserDto}.</li>
 *     <li>{@link #updateUser(UserDto)}: Updates an existing {@link User} entity based on the provided {@link UserDto}.</li>
 *     <li>{@link #deleteUser(Long)}: Deletes the {@link User} entity with the specified ID.</li>
 *     <li>{@link #findByEmail(String)}: Retrieves the {@link User} entity with the specified email.</li>
 *     <li>{@link #findById(Long)}: Retrieves the {@link User} entity with the specified ID.</li>
 *     <li>{@link #findByUsername(String)}: Retrieves an {@link Optional} containing the {@link User} entity with the specified username.</li>
 *     <li>{@link #getUsers()} : Retrieves a list of all {@link UserDto} objects representing users.</li>
 *     <li>{@link #convertToDto(User)}: Converts a {@link User} entity to a {@link UserDto} object.</li>
 * </ul>
 *
 * @see User
 * @see UserDto
 */

@Component
public interface UserService {

    /**
     * Saves a new {@link User} entity based on the provided {@link UserDto}.
     *
     * @param userDto the {@link UserDto} containing the details of the user to be saved.
     */
    void saveUser(UserDto userDto);

    /**
     * Updates an existing {@link User} entity based on the provided {@link UserDto}.
     *
     * @param userDto the {@link UserDto} containing the updated details of the user.
     */
    void updateUser(UserDto userDto);
    
    /**
     * Deletes the {@link User} entity with the specified ID.
     *
     * @param id the ID of the {@link User} entity to be deleted.
     */
    void deleteUser(Long id);

    /**
     * Retrieves the {@link User} entity with the specified ID.
     *
     * @param email the ID of the {@link User} entity to be retrieved.
     * @return the {@link User} entity with the specified ID.
     */
    User findByEmail(String email);

    /**
     * Retrieves the {@link User} entity with the specified ID.
     *
     * @param id the ID of the {@link User} entity to be retrieved.
     * @return the {@link User} entity with the specified ID.
     */
    User findById(Long id);

    /**
     * Retrieves an {@link Optional} containing the {@link User} entity with the specified username.
     *
     * @param username the username of the {@link User} entity to be retrieved.
     * @return an {@link Optional} containing the {@link User} entity with the specified username, or an empty {@link Optional} if not found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Retrieves a list of all {@link UserDto} objects representing users.
     *
     * @return a list of all {@link UserDto} objects.
     */
    List<UserDto> getUsers();

    /**
     * Converts a {@link User} entity to a {@link UserDto} object.
     *
     * @param user the {@link User} entity to be converted.
     * @return the {@link UserDto} object representing the user.
     */
    UserDto convertToDto(User user);
}
