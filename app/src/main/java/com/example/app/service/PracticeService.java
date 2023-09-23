package com.example.app.service;

import com.example.app.exception.InformationNotFoundException;
import com.example.app.exception.InformationNotFoundException;
import com.example.app.model.Habit;
import com.example.app.model.PracticeTracker;
import com.example.app.model.User;
import com.example.app.repository.PracticeRepository;
import com.example.app.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

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
    Logger logger = Logger.getLogger(PracticeTracker.class.getName());

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
     * Extracts usr information from context holder
     * @return User object
     */
    public static User getCurrentLoggedInUser(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder //After jwt is generated, Security Context Holder is created to hold the user's state
                .getContext().getAuthentication().getPrincipal(); // the entire User object, with authentication details
        return userDetails.getUser();
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
            if (practiceTracker.getDate().isAfter(LocalDate.now())){
                throw new IllegalArgumentException("Date must equal or before today's date");
            }
            practiceTracker.setUser(getCurrentLoggedInUser());
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

    /**
     * Retrieves a list of practices associated with the current user.
     * @return list of practices
     */
    public List<PracticeTracker> getAllPractices(){
        List<PracticeTracker> practiceList = practiceRepository.findByUserId(getCurrentLoggedInUser().getId());
        if (practiceList.isEmpty()){
            throw new InformationNotFoundException("No practices found.");
        }
        return practiceList;
    }

    /**
     * Updates the specified fields of a given practice using Java Reflection.
     * <p>
     * Only the necessary fields of the class are updated.
     * </p>
     * @param practice Practice Tracker object with the updated properties.
     * @return The updated object.
     */
    public PracticeTracker updatePractice(PracticeTracker practice) throws IllegalAccessException {
        Optional<PracticeTracker> practiceOptional = practiceRepository.findById(practice.getId());
        if (practice.getDate().isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Date must equal or before today's date");
        }
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

    /**
     * Deletes the practice tracker specified by the given id.
     *
     * @param practiceId The unique Practice Tracker Id.
     * @return The deleted Practice.
     * @throws InformationNotFoundException If practice is not found.
     */
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
