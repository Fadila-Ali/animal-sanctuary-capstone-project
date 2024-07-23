package com.ali.animalsanctuary.service.impl;

import com.ali.animalsanctuary.entity.User;
import com.ali.animalsanctuary.entity.Volunteer;
import com.ali.animalsanctuary.repository.UserRepository;
import com.ali.animalsanctuary.repository.VolunteerRepository;
import com.ali.animalsanctuary.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link VolunteerService} interface.
 *
 * This class provides the actual implementation of the methods defined in the {@link VolunteerService} interface,
 * handling operations related to {@link Volunteer} entities, including saving, retrieving, deleting volunteers,
 * and managing user associations with volunteer tasks.
 *
 * <p>Methods:
 * <ul>
 *     <li>{@link #saveVolunteer(Volunteer)}: Saves a new or updated {@link Volunteer} entity.</li>
 *     <li>{@link #getVolunteerById(Long)}: Retrieves a {@link Volunteer} entity by its ID.</li>
 *     <li>{@link #getAllVolunteers()}: Retrieves a list of all {@link Volunteer} entities.</li>
 *     <li>{@link #getVolunteersByUserId(Long)}: Retrieves a list of {@link Volunteer} entities associated with a specific user ID.</li>
 *     <li>{@link #deleteVolunteer(Long)}: Deletes the {@link Volunteer} entity with the specified ID.</li>
 *     <li>{@link #addUserToVolunteer(Long, User)}: Adds a {@link User} to a specific {@link Volunteer} entity.</li>
 * </ul>
 *
 * @see Volunteer
 * @see User
 * @see VolunteerService
 */

@Service
public class VolunteerServiceImpl implements VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Saves a new or updated {@link Volunteer} entity.
     *
     * @param volunteer the {@link Volunteer} entity to be saved.
     * @return the saved {@link Volunteer} entity.
     */
    @Override
    public Volunteer saveVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    /**
     * Retrieves a {@link Volunteer} entity by its ID.
     *
     * @param id the ID of the {@link Volunteer} entity to be retrieved.
     * @return the {@link Volunteer} entity with the specified ID, or {@code null} if not found.
     */
    @Override
    public Volunteer getVolunteerById(Long id) {
        return volunteerRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves a list of all {@link Volunteer} entities.
     *
     * @return a list of all {@link Volunteer} entities.
     */
    @Override
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    /**
     * Retrieves a list of {@link Volunteer} entities associated with a specific user ID.
     *
     * @param userId the ID of the {@link User} whose associated volunteers are to be retrieved.
     * @return a list of {@link Volunteer} entities associated with the specified user ID.
     */
    @Override
    public List<Volunteer> getVolunteersByUserId(Long userId) {
        return volunteerRepository.findAll().stream()
                .filter(volunteer -> volunteer.getUsers().stream()
                        .anyMatch(user -> user.getId().equals(userId)))
                .toList();
    }

    /**
     * Deletes the {@link Volunteer} entity with the specified ID.
     *
     * @param id the ID of the {@link Volunteer} entity to be deleted.
     */
    @Override
    public void deleteVolunteer(Long id) {
        volunteerRepository.deleteById(id);
    }

    /**
     * Adds a {@link User} to a specific {@link Volunteer} entity.
     *
     * @param volunteerId the ID of the {@link Volunteer} entity to which the user is to be added.
     * @param user the {@link User} to be added to the volunteer.
     * @throws RuntimeException if the {@link Volunteer} entity with the specified ID is not found.
     */
    @Override
    public void addUserToVolunteer(Long volunteerId, User user) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer work not found"));
        volunteer.getUsers().add(user);
        volunteerRepository.save(volunteer);
    }
}
