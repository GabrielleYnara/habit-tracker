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

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

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

    public List<Category> getAllCategories() {
        User user = getCurrentLoggedInUser();
        List<Category> categoryList = user.getCategoryList();
        if (categoryList.isEmpty()){
            throw new InformationNotFoundException("No categories found for User id " + user.getId());
        }
        return categoryList;
    }
}
