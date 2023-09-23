package com.example.app.repository;

import com.example.app.model.PracticeTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PracticeRepository extends JpaRepository<PracticeTracker, Long> {
    List<PracticeTracker> findByDate(LocalDate date);
}
