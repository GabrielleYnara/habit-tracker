package com.example.app.service;

import com.example.app.exception.InformationExistException;
import com.example.app.exception.InformationNotFoundException;
import com.example.app.model.Category;
import com.example.app.model.User;
import com.example.app.repository.CategoryRepository;
import com.example.app.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Represents the Category Service, responsible for handling business logic related to categories.
 * This class serves as an intermediary between the controller and the category repository,
 * invoking the repository to perform CRUD operations on categories.
 * Note: Imported and refactored from todo project
 * @version 1.0.0
 */
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * Injects a CategoryRepository dependency automatically.
     * And enables the service to access the repository's interface methods.
     * @param categoryRepository The repository for category-related CRUD operations.
     */
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
}
