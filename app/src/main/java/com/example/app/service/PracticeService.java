package com.example.app.service;

import com.example.app.exception.InformationNotFoundException;
import com.example.app.model.Habit;
import com.example.app.model.PracticeTracker;
import com.example.app.repository.PracticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class handling business logic related to the practice tracker.
 * <p>
 * Acts as an intermediary between the controller and the practice repository, and the category service.
 * </p>
 * @version 1.0.0
 */
@Service
public class PracticeService {
    private final PracticeRepository practiceRepository;
    private final CategoryService categoryService;

    /**
     * Injects dependencies and enables userService to access resources.
     *
     * @param practiceRepository Repository for practice-related CRUD operations.
     * @param categoryService Service for category-related business logic.
     */
    @Autowired
    public PracticeService(PracticeRepository practiceRepository, CategoryService categoryService) {
        this.practiceRepository = practiceRepository;
        this.categoryService = categoryService;
    }

    /**
     * Creates a practice associated with given Habit id.
     *
     * @param habitId the unique habit Id.
     * @param practiceTracker PracticeTracker object containing practice details.
     * @return The recently created practice.
     */
    public PracticeTracker createPractice(Long habitId, PracticeTracker practiceTracker){
        Optional<Habit> habitOptional = Optional.ofNullable(categoryService.getHabitById(habitId));
        if (habitOptional.isPresent()){
            practiceTracker.setHabit(habitOptional.get());
            return practiceRepository.save(practiceTracker);
        } else {
            throw new InformationNotFoundException("Habit with id " + habitId + " not found.");
        }
    }

    public PracticeTracker getPracticeById(Long habitId){
        Optional<PracticeTracker> practiceOptional = practiceRepository.findById(habitId);

        if (practiceOptional.isPresent()){
            return practiceOptional.get();
        } else {
            throw new InformationNotFoundException("Practice with id " + habitId + "not found.");
        }
    }

    public List<PracticeTracker> getPracticeByDate(LocalDate date){
        List<PracticeTracker> practiceList = practiceRepository.findByDate(date);
        if (practiceList.isEmpty()){
            throw new InformationNotFoundException("No practices found on " + date);
        }
        return practiceList;
    }

    public List<PracticeTracker> getAllPractices(){
        List<PracticeTracker> practiceList = practiceRepository.findAll();
        if (practiceList.isEmpty()){
            throw new InformationNotFoundException("No practices found.");
        }
        return practiceList;
    }

}
