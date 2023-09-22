package com.example.app.controller;

import com.example.app.model.Category;
import com.example.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/**
 * Category Controller for handling category management operations.
 *
 * Note: Imported and refactored from todo project
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api") //http://localhost:9009/api
public class CategoryController {
    private CategoryService categoryService;
    @Autowired //"Connects" category service class, able to use its methods
    public void setCategoryService(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping(path = "/categories/") //http://localhost:9009/api/categories/
    public Category createCategory(@RequestBody Category categoryObj) {
        return categoryService.createCategory(categoryObj);
    }
    @GetMapping(path = "/categories/{categoryId}/") //http://localhost:9009/api/categories/1/
    public Optional<Category> getCategory(@PathVariable(value = "categoryId") Long categoryId){
        return categoryService.getCategory(categoryId);
    }

    @GetMapping(path = "/categories/") //http://localhost:9009/api/categories/
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }
}
