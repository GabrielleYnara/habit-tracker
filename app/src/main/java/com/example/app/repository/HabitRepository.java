package com.example.app.repository;

import com.example.app.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    Habit findByName(String habitName);
}
