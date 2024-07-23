package com.ali.animalsanctuary;

import com.ali.animalsanctuary.entity.User;
import com.ali.animalsanctuary.repository.UserRepository;
import com.ali.animalsanctuary.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        // Given
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // When
        User foundUser = userService.findById(1L);

        // Then
        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
    }
}
