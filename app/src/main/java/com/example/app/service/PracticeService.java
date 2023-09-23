package com.example.app.service;

import com.example.app.exception.InformationNotFoundException;
import com.example.app.model.Habit;
import com.example.app.model.PracticeTracker;
import com.example.app.repository.PracticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PracticeService {
    private final PracticeRepository practiceRepository;
    private final CategoryService categoryService;

    @Autowired
    public PracticeService(PracticeRepository practiceRepository, CategoryService categoryService) {
        this.practiceRepository = practiceRepository;
        this.categoryService = categoryService;
    }

    public PracticeTracker createPractice(Long habitId, PracticeTracker practiceTracker){
        Optional<Habit> habitOptional = Optional.ofNullable(categoryService.getHabitById(habitId));
        if (habitOptional.isPresent()){
            practiceTracker.setHabit(habitOptional.get());
            return practiceRepository.save(practiceTracker);
        } else {
            throw new InformationNotFoundException("Habit with id " + habitId + " not found.");
        }
    }
}
