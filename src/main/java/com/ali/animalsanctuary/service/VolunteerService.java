package com.ali.animalsanctuary.service;

import com.ali.animalsanctuary.entity.User;
import com.ali.animalsanctuary.entity.Volunteer;

import java.util.List;

/**
 * Service interface for managing {@link Volunteer} entities.
 *
 * This interface defines the operations available for handling volunteer records, including saving, retrieving,
 * and deleting volunteers. It also supports associating users with volunteers.
 *
 * <p>Methods:
 * <ul>
 *     <li>{@link #saveVolunteer(Volunteer)}: Saves a new {@link Volunteer} entity.</li>
 *     <li>{@link #getVolunteerById(Long)}: Retrieves the {@link Volunteer} entity with the specified ID.</li>
 *     <li>{@link #getAllVolunteers()} : Retrieves a list of all {@link Volunteer} entities.</li>
 *     <li>{@link #getVolunteersByUserId(Long)}: Retrieves a list of {@link Volunteer} entities associated with the specified user ID.</li>
 *     <li>{@link #deleteVolunteer(Long)}: Deletes the {@link Volunteer} entity with the specified ID.</li>
 *     <li>{@link #addUserToVolunteer(Long, User)}: Associates a {@link User} with a {@link Volunteer} entity.</li>
 * </ul>
 *
 * @see Volunteer
 * @see User
 */

public interface VolunteerService {
    /**
     * Saves a new {@link Volunteer} entity.
     *
     * @param volunteer the {@link Volunteer} entity to be saved.
     * @return the saved {@link Volunteer} entity.
     */
    Volunteer saveVolunteer(Volunteer volunteer);

    /**
     * Retrieves the {@link Volunteer} entity with the specified ID.
     *
     * @param id the ID of the {@link Volunteer} entity to be retrieved.
     * @return the {@link Volunteer} entity with the specified ID.
     */
    Volunteer getVolunteerById(Long id);

    /**
     * Retrieves a list of all {@link Volunteer} entities.
     *
     * @return a list of all {@link Volunteer} entities.
     */
    List<Volunteer> getAllVolunteers();

    /**
     * Retrieves a list of {@link Volunteer} entities associated with the specified user ID.
     *
     * @param userId the ID of the {@link User} whose associated volunteers are to be retrieved.
     * @return a list of {@link Volunteer} entities associated with the specified user ID.
     */
    List<Volunteer> getVolunteersByUserId(Long userId);

    /**
     * Deletes the {@link Volunteer} entity with the specified ID.
     *
     * @param id the ID of the {@link Volunteer} entity to be deleted.
     */
    void deleteVolunteer(Long id);

    /**
     * Associates a {@link User} with a {@link Volunteer} entity.
     *
     * @param volunteerId the ID of the {@link Volunteer} entity.
     * @param user        the {@link User} to be associated with the volunteer.
     */
    void addUserToVolunteer(Long volunteerId, User user);
}
