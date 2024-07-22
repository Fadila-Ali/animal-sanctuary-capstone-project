package com.ali.animalsanctuary.repository;

import com.ali.animalsanctuary.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findAllByAvailableTrue();
    Optional<Visit> findByIdAndAvailableTrue(Long id);
}
