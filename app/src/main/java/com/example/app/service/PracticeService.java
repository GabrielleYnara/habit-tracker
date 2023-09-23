package com.example.app.service;

import com.example.app.exception.InformationNotFoundException;
import com.example.app.model.Habit;
import com.example.app.model.PracticeTracker;
import com.example.app.repository.PracticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
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
    private Object newValue;

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

    public PracticeTracker updatePractice(PracticeTracker practice) throws IllegalAccessException {
        Optional<PracticeTracker> practiceOptional = practiceRepository.findById(practice.getId());
        if (practiceOptional.isPresent()){
            try {
                for (Field field : PracticeTracker.class.getDeclaredFields()) { //loop through class fields
                    field.setAccessible(true); //make private fields accessible
                    Object newValue = field.get(practice);
                    Object originalValue = field.get(practiceOptional.get());
                    if (newValue != null && !newValue.equals(originalValue)) { //if not null and different from original
                        field.set(practiceOptional.get(), newValue);
                    }
                }
                return practiceRepository.save(practiceOptional.get());
            } catch (IllegalArgumentException e){
                throw new IllegalAccessException(e.getMessage());
            }
        } else {
            throw new InformationNotFoundException("Practice with id " + practice.getId() + "not found.");
        }
    }

    public Optional<PracticeTracker> deletePractice(Long practiceId) {
        Optional<PracticeTracker> practiceOptional = practiceRepository.findById(practiceId);
        if (practiceOptional.isPresent()) {
            practiceRepository.deleteById(practiceId);
            return practiceOptional;
        } else {
            throw new InformationNotFoundException("practice with id " + practiceId + " not found");
        }
    }
}
