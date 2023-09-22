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
 *
 * This class serves as an intermediary between the controller and the category repository,
 * invoking the repository to perform CRUD operations on categories.
 *
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
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder //After jwt is generated, Security Context Holder is create to hold the user's state
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
}
