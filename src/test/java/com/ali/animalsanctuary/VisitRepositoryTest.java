package com.ali.animalsanctuary;

import com.ali.animalsanctuary.entity.Visit;
import com.ali.animalsanctuary.repository.VisitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class VisitRepositoryTest {

    @Autowired
    private VisitRepository visitRepository;

    @Test
    public void testFindAllByAvailableTrue() {
        // Given
        Visit availableVisit1 = new Visit();
        availableVisit1.setAvailable(true);
        visitRepository.save(availableVisit1);

        Visit availableVisit2 = new Visit();
        availableVisit2.setAvailable(true);
        visitRepository.save(availableVisit2);

        Visit unavailableVisit = new Visit();
        unavailableVisit.setAvailable(false);
        visitRepository.save(unavailableVisit);

        // When
        List<Visit> availableVisits = visitRepository.findAllByAvailableTrue();

        // Then
        assertEquals(2, availableVisits.size(), "There should be 2 available visits");
        assertTrue(availableVisits.contains(availableVisit1), "Available visit 1 should be in the list");
        assertTrue(availableVisits.contains(availableVisit2), "Available visit 2 should be in the list");
        assertFalse(availableVisits.contains(unavailableVisit), "Unavailable visit should not be in the list");
    }

    @Test
    public void testFindByIdAndAvailableTrue() {
        // Given
        Visit availableVisit = new Visit();
        availableVisit.setAvailable(true);
        Visit savedVisit = visitRepository.save(availableVisit);

        Visit unavailableVisit = new Visit();
        unavailableVisit.setAvailable(false);
        visitRepository.save(unavailableVisit);

        // When
        Optional<Visit> foundAvailableVisit = visitRepository.findByIdAndAvailableTrue(savedVisit.getId());
        Optional<Visit> foundUnavailableVisit = visitRepository.findByIdAndAvailableTrue(unavailableVisit.getId());

        // Then
        assertTrue(foundAvailableVisit.isPresent(), "Available visit should be found");
        assertEquals(savedVisit.getId(), foundAvailableVisit.get().getId(), "The ID of the found visit should match");

        assertFalse(foundUnavailableVisit.isPresent(), "Unavailable visit should not be found");
    }
}
