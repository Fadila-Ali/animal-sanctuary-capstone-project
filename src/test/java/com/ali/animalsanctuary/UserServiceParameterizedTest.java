package com.ali.animalsanctuary;

import com.ali.animalsanctuary.entity.User;
import com.ali.animalsanctuary.repository.UserRepository;
import com.ali.animalsanctuary.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceParameterizedTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @ParameterizedTest
    @CsvSource({
            "1, true",
            "2, false"
    })
    public void testFindById(Long id, boolean shouldExist) {
        // Given
        User user = new User();
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(shouldExist ? Optional.of(user) : Optional.empty());

        // When
        User foundUser = userService.findById(id);

        // Then
        if (shouldExist) {
            assertNotNull(foundUser);
            assertEquals(id, foundUser.getId());
        } else {
            assertNull(foundUser);
        }
    }
}
