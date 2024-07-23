package com.ali.animalsanctuary;

import com.ali.animalsanctuary.entity.Role;
import com.ali.animalsanctuary.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindByName() {
        // Given
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        roleRepository.findByName(adminRole.getName());

        Role userRole = new Role();
        userRole.setName("USER");
        roleRepository.save(userRole);

        // When
        Role foundAdminRole = roleRepository.findByName("ADMIN");
        Role foundUserRole = roleRepository.findByName("USER");

        // Then
        assertNotNull(foundAdminRole, "Admin role should be present");
        assertEquals("ADMIN", foundAdminRole.getName(), "Admin role name should match");

        assertNotNull(foundUserRole, "User role should be present");
        assertEquals("USER", foundUserRole.getName(), "User role name should match");
    }
}
