package com.ali.animalsanctuary.service;

import com.ali.animalsanctuary.dto.UserDto;
import com.ali.animalsanctuary.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserService {

    void saveUser(UserDto userDto);
    void updateUser(UserDto userDto);
    void deleteUser(Long id);
    User findByEmail(String email);
    User findById(Long id);
    Optional<User> findByUsername(String username);
    List<UserDto> getUsers();
    UserDto convertToDto(User user);
}
