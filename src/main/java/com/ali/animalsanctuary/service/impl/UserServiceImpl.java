package com.ali.animalsanctuary.service.impl;

import com.ali.animalsanctuary.dto.UserDto;
import com.ali.animalsanctuary.entity.Role;
import com.ali.animalsanctuary.entity.User;
import com.ali.animalsanctuary.repository.RoleRepository;
import com.ali.animalsanctuary.repository.UserRepository;
import com.ali.animalsanctuary.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link UserService} interface.
 *
 * This class provides the actual implementation of the methods defined in the {@link UserService} interface,
 * handling operations related to {@link User} entities, including saving, updating, deleting, and retrieving users.
 * It also supports managing user roles and converting between {@link User} entities and {@link UserDto} data transfer objects.
 *
 * <p>Methods:
 * <ul>
 *     <li>{@link #saveUser(UserDto)}: Saves a new {@link User} entity based on the provided {@link UserDto} data transfer object.</li>
 *     <li>{@link #updateUser(UserDto)}: Updates an existing {@link User} entity with new information from the {@link UserDto} data transfer object.</li>
 *     <li>{@link #deleteUser(Long)}: Deletes the {@link User} entity with the specified ID.</li>
 *     <li>{@link #findByEmail(String)}: Retrieves the {@link User} entity with the specified email.</li>
 *     <li>{@link #findById(Long)}: Retrieves the {@link User} entity with the specified ID.</li>
 *     <li>{@link #findByUsername(String)}: Retrieves an {@link Optional} containing the {@link User} entity with the specified username.</li>
 *     <li>{@link #getUsers()}: Retrieves a list of all {@link UserDto} data transfer objects representing all users.</li>
 *     <li>{@link #convertToDto(User)}: Converts a {@link User} entity to a {@link UserDto} data transfer object.</li>
 * </ul>
 *
 * @see User
 * @see UserDto
 * @see UserService
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new {@link UserServiceImpl} with the specified {@link UserRepository}, {@link RoleRepository},
     * and {@link PasswordEncoder}.
     *
     * @param userRepository    the repository for managing {@link User} entities.
     * @param roleRepository    the repository for managing {@link Role} entities.
     * @param passwordEncoder   the encoder for encoding user passwords.
     */
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Saves a new {@link User} entity based on the provided {@link UserDto} data transfer object.
     *
     * @param userDto the {@link UserDto} containing information to create a new {@link User}.
     */
    @Override
    public void saveUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstname());
        user.setLastName(userDto.getLastname());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

//        if (userDto.getImage() != null && !userDto.getImage().isEmpty()) {
//            try {
//                user.setImage(userDto.getImage().getBytes());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (profile_img != null && !profile_img.isEmpty()) {
//            user.setProfile_img(profile_img.getBytes());
//        }

        String roleName = userDto.isAdminRegistration() ? "ROLE_ADMIN" : "ROLE_USER";
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
        user.setRoles((Collections.singletonList(role)));

        userRepository.save(user);
    }

    /**
     * Updates an existing {@link User} entity with new information from the provided {@link UserDto} data transfer object.
     *
     * @param userDto the {@link UserDto} containing updated information for an existing {@link User}.
     * @throws RuntimeException if the user with the specified ID is not found.
     */
    @Override
    public void updateUser(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userDto.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            user.setFirstName(userDto.getFirstname());
            user.setLastName(userDto.getLastname());
            user.setUpdatedAt(LocalDateTime.now());

//            if (profile_img != null && !profile_img.isEmpty()) {
//                user.setProfile_img(profile_img.getBytes());
//            }

            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    /**
     * Deletes the {@link User} entity with the specified ID.
     *
     * @param id the ID of the {@link User} entity to be deleted.
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Retrieves the {@link User} entity with the specified email.
     *
     * @param email the email of the {@link User} to be retrieved.
     * @return the {@link User} entity with the specified email.
     */
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Retrieves the {@link User} entity with the specified ID.
     *
     * @param id the ID of the {@link User} entity to be retrieved.
     * @return the {@link User} entity with the specified ID, or {@code null} if not found.
     */
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves an {@link Optional} containing the {@link User} entity with the specified username.
     *
     * @param username the username of the {@link User} to be retrieved.
     * @return an {@link Optional} containing the {@link User} entity, or {@link Optional#empty()} if not found.
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Retrieves a list of all {@link UserDto} data transfer objects representing all users.
     *
     * @return a list of {@link UserDto} objects representing all users.
     */
    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Formats a {@link LocalDateTime} object as a string in the format "yyyy-MM-dd HH:mm:ss".
     *
     * @param localDateTime the {@link LocalDateTime} object to be formatted.
     * @return the formatted date-time string.
     */
    private String formatDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    /**
     * Converts a {@link User} entity to a {@link UserDto} data transfer object.
     *
     * @param user the {@link User} entity to be converted.
     * @return the corresponding {@link UserDto} data transfer object.
     */
    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setFirstname(user.getFirstName());
        userDto.setLastname(user.getLastName());
        userDto.setCreatedAt(formatDateTime(user.getCreatedAt()));
        userDto.setUpdatedAt(formatDateTime(user.getUpdatedAt()));

        return userDto;
    }
}
