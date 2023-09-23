package com.example.app.service;

import com.example.app.exception.InformationExistException;
import com.example.app.exception.InformationNotFoundException;
import com.example.app.model.Category;
import com.example.app.model.Habit;
import com.example.app.model.User;
import com.example.app.repository.CategoryRepository;
import com.example.app.repository.HabitRepository;
import com.example.app.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Represents the Category Service, responsible for handling business logic related to categories.<br>
 * This class serves as an intermediary between the controller and the category repository,
 * invoking the repository to perform CRUD operations on categories.<br>
 * Note: Imported and refactored from todo project
 * @version 1.1.0
 */
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final HabitRepository habitRepository;

    /**
     * Injects a CategoryRepository dependency automatically.
     * And enables the service to access the repository's interface methods.
     * @param categoryRepository The repository for category-related CRUD operations.
     */
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, HabitRepository habitRepository) {
        this.categoryRepository = categoryRepository;
        this.habitRepository = habitRepository;
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
     * Creates a Category and saves it in the database.
     * @param category The Category object.
     * @return The category recently created.
     * @throws InformationExistException if Category already exists.
     */
    public Category createCategory(Category category) {
        Optional<Category> categoryOptional = Optional.ofNullable(categoryRepository.findByNameAndUserId(category.getName(), getCurrentLoggedInUser().getId()));
        if (categoryOptional.isPresent()) {
            throw new InformationExistException("Category with name " + category.getName() + " already exists.");
        } else {
            category.setUser(getCurrentLoggedInUser());
            return categoryRepository.save(category);
        }
    }

    /**
     * Retrieves a single category by its categoryId.
     *
     * @param categoryId The unique ID of the category to retrieve.
     * @return An Optional containing the Category object if found.
     * @throws InformationNotFoundException if the category is not found.
     */
    public Optional<Category> getCategory(Long categoryId){
        Optional<Category> categoryOptional = Optional.of(categoryRepository.findByIdAndUserId(categoryId, CategoryService.getCurrentLoggedInUser().getId()));
        if (categoryOptional.isPresent()){
            return categoryOptional;
        } else {
            throw new InformationNotFoundException("Category with id " + categoryId + " not found.");
        }
    }

    /**
     * Retrieves all categories from database.
     * @return A list of all Categories stored in the database.
     */
    public List<Category> getAllCategories() {
        User user = getCurrentLoggedInUser();
        List<Category> categoryList = user.getCategoryList();
        if (categoryList.isEmpty()){
            throw new InformationNotFoundException("No categories found for User id " + user.getId());
        }
        return categoryList;
    }

    /**
     * Updates a given category.
     *
     * @param categoryId A Long value to indicate the category's unique ID.
     * @param category A Category object containing the updated properties.
     * @return The updated Category.
     * @throws InformationExistException If the updated properties are the same as those in the database.
     * @throws InformationNotFoundException If a category with the given categoryId is not found.
     */
    public Category updateCategory(Long categoryId, Category category){
        Optional<Category> categoryOptional = Optional.ofNullable(categoryRepository.findByIdAndUserId(categoryId, getCurrentLoggedInUser().getId()));
        if (categoryOptional.isPresent()){ // Category found in db
            if (category.getName() != null && category.getName().equals(categoryOptional.get().getName()) &&
                    category.getDescription() != null && category.getDescription().equals(categoryOptional.get().getDescription())){
                throw new InformationExistException("The category " + category.getName() + " with description " + category.getDescription() + " already exists.");
            } else {
                // updates name if not null and different from original
                if (category.getName() != null && !String.valueOf(categoryOptional.get().getName()).equals(category.getName())){
                    categoryOptional.get().setName(category.getName());
                }
                // updates name if not null and different from original
                if (category.getDescription() != null && !String.valueOf(categoryOptional.get().getDescription()).equals(category.getDescription())){
                    categoryOptional.get().setDescription(category.getDescription());
                }
                return categoryRepository.save(categoryOptional.get());
            }
        } else {
            throw new InformationNotFoundException("Category with id " + categoryId + " not found.");
        }
    }

    public Optional<Category> deleteCategory(Long categoryId){
        Optional<Category> categoryOptional = Optional.ofNullable(categoryRepository.findByIdAndUserId(categoryId, getCurrentLoggedInUser().getId()));
        if (categoryOptional.isPresent()){
            categoryRepository.deleteById(categoryId);
            return categoryOptional;
        } else {
            throw new InformationNotFoundException("Category with id " + categoryId + " not found.");
        }
    }

    //--------------------------- Habit related ---------------------------
    /**
     * Creates and saves a Habit associated with the specified category and current user.
     *
     * @param categoryId Category's unique ID.
     * @param habit Habit object to be created.
     * @return Recently created habit.
     * @throws InformationNotFoundException If the category is not found.
     * @throws InformationExistException If a habit with the same name already exists.
     */
    public Habit createCategoryHabit(Long categoryId, Habit habit){
        Optional<Category> categoryOptional = Optional.ofNullable(categoryRepository.findByIdAndUserId(categoryId, getCurrentLoggedInUser().getId()));
        if (categoryOptional.isPresent()) {
            Optional<Habit> habitOptional = Optional.ofNullable(habitRepository.findByName(habit.getName()));
            if (habitOptional.isEmpty()) {
                habit.setCategory(categoryOptional.get());
                habit.setUser(getCurrentLoggedInUser());
                return habitRepository.save(habit);
            } else {
                throw new InformationExistException("Habit " + habit.getName() + " already exists.");
            }
        } else {
            throw new InformationNotFoundException("Category with id " + categoryId + " not found.");
        }
    }

    /**
     * Retrieves a habit by its ID, associated with the given category and current user.
     *
     * @param categoryId Category's unique ID.
     * @param habitId Habit's unique ID.
     * @return Retrieved habit.
     * @throws InformationNotFoundException if the habit is not found.
     */
    public Habit getHabitByIdAndCategory(Long categoryId, Long habitId){
        Optional<Category> categoryOptional = getCategory(categoryId);
        Optional<Habit> habitOptional = categoryOptional
                .get()
                .getHabitList()
                .stream()
                .filter(habit -> habit.getId() == habitId).findFirst();
        if(habitOptional.isPresent()) {
            return habitOptional.get();
        } else {
            throw new InformationNotFoundException("habit with id " + habitId + " not found");
        }
    }

    /**
     * Retrieves a habit by its ID.
     * @param habitId Habit's unique ID.
     * @return Retrieved habit.
     */
    public Habit getHabitById(Long habitId){
        Optional<Habit> habitOptional = habitRepository.findById(habitId);
        if (habitOptional.isPresent()){
            return habitOptional.get();
        } else {
            throw new InformationNotFoundException("Habit with id " + habitId + " not found.");
        }
    }

    /**
     * Retrieves a list of habits associated with the current user.
     * @return List of user's habits.
     * @throws InformationNotFoundException If no habits are found for the user.
     */
    public List<Habit> getAllHabits() {
        User user = getCurrentLoggedInUser();
        List<Habit> habitList = user.getHabitList();

        if (habitList.isEmpty()){
            throw new InformationNotFoundException("No habits found for User id " + user.getId());
        }
        return habitList;
    }

    /**
     * Updates the specified fields of a given habit using Java Reflection.
     * <p>
     * Only the necessary fields of the class are updated.
     *</p>
     * @References: <a href="https://docs.oracle.com/javase/tutorial/reflect/class/classMembers.html">Using Java Reflection</a><br>
     *              <a href="https://docs.oracle.com/javase/tutorial/reflect/class/classMembers.html">Discovering Class Members</a><br>
     *              <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html">Class Field</a><br>
     * @param habit Habit object with the updated properties.
     * @return The updated Habit object.
     * @throws InformationNotFoundException If no habit was found if given id.
     */
    public Habit updateHabit(Habit habit) throws IllegalAccessException {
        Optional<Habit> habitOptional = habitRepository.findById(habit.getId());
        if (habitOptional.isPresent()){
            try {
                for (Field field : Habit.class.getDeclaredFields()) { // Java Reflection allows to loop through class fields
                    field.setAccessible(true); //make private fields accessible
                    Object newValue = field.get(habit); //assigns the current field's value from habit to newValue
                    Object originalValue = field.get(habitOptional.get()); //assigns the current field's value from habitOptional to originalValue
                    if (newValue != null && !newValue.equals(originalValue)) { //if not null and different from original
                        field.set(habitOptional.get(), newValue);
                    }
                }
                return habitRepository.save(habitOptional.get());
            } catch (IllegalArgumentException e){
                throw new IllegalArgumentException(e.getMessage());
            }
        } else {
            throw new InformationNotFoundException("Habit with id " + habit.getId() + " not found.");
        }
    }

    /**
     * Deletes the habit specified by the given categoryId and habitId.
     *
     * @param categoryId The unique Category Id.
     * @param habitId The unique Habit Id.
     * @return The deleted Habit.
     */
    public Optional<Habit> deleteHabit(Long categoryId, Long habitId) {
        Optional<Habit> habitOptional = Optional.ofNullable(getHabitByIdAndCategory(categoryId, habitId));
        if (habitOptional.isPresent()) {
            habitRepository.deleteById(habitId);
            return habitOptional;
        } else {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }
}
