package com.example.app.service;

import com.example.app.exception.InformationNotFoundException;
import com.example.app.model.Category;
import com.example.app.model.User;
import com.example.app.repository.CategoryRepository;
import com.example.app.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
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
