package com.ali.animalsanctuary.service.impl;

import com.ali.animalsanctuary.dto.UserDto;
import com.ali.animalsanctuary.entity.Role;
import com.ali.animalsanctuary.entity.User;
import com.ali.animalsanctuary.repository.RoleRepository;
import com.ali.animalsanctuary.repository.UserRepository;
import com.ali.animalsanctuary.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstname());
        user.setLastName(userDto.getLastname());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        if (userDto.getImage() != null && !userDto.getImage().isEmpty()) {
            try {
                user.setImage(userDto.getImage().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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

            if (userDto.getImage() != null && !userDto.getImage().isEmpty()) {
                try {
                    user.setImage(userDto.getImage().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private String formatDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

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
        // Convert image byte array to MultipartFile or Base64 String if needed
        return userDto;
    }
}
