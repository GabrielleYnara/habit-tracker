package com.example.app.repository;

import com.example.app.model.PracticeTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeRepository extends JpaRepository<PracticeTracker, Long> {
}
