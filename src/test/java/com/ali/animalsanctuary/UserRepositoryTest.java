package com.ali.animalsanctuary;

import com.ali.animalsanctuary.entity.User;
import com.ali.animalsanctuary.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setFirstName("John");
        user.setLastName("Doe");
        userRepository.save(user);

        // When
        User foundUser = userRepository.findByEmail("test@example.com");

        // Then
        assertNotNull(foundUser, "User should be present");
        assertEquals("test@example.com", foundUser.getEmail(), "Email should match");
    }
}
