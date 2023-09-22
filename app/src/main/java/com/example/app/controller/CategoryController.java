package com.example.app.controller;

import com.example.app.model.Category;
import com.example.app.model.Habit;
import com.example.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Category Controller for handling category management operations.<br>
 * Note: Imported and refactored from todo project
 * @version 1.1.0
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

    @PutMapping(path = "/categories/{categoryId}") //http://localhost:9009/api/categories/1/
    public Category updateCategory(@PathVariable(value = "categoryId") Long categoryId, @RequestBody Category category){
        return categoryService.updateCategory(categoryId, category);
    }

    @DeleteMapping(path="/categories/{categoryId}/") //http://localhost:9009/api/categories/1/
    public Optional<Category> deleteCategory(@PathVariable(value = "categoryId") Long categoryId){
        return categoryService.deleteCategory(categoryId);
    }

    //--------------------------- Habit related ---------------------------
    @PostMapping(path = "/categories/{categoryId}/habits/") //http://localhost:9009/api/categories/1/habits/
    public Habit createCategoryHabit(@PathVariable(value = "categoryId") Long categoryId, @RequestBody Habit habit){
        return categoryService.createCategoryHabit(categoryId, habit);
    }

    @GetMapping(path = "/categories/{categoryId}/habits/{habitId}/") //http://localhost:9009/api/categories/1/habits/1/
    public Optional<Habit> getHabit(@PathVariable(value = "categoryId") Long categoryId, @PathVariable(value = "habitId") Long habitId) {
        return Optional.ofNullable(categoryService.getHabit(categoryId, habitId));
    }
    @GetMapping(path = "/habits/") //http://localhost:9009/api/habits/
    public List<Habit> getAllHabits() {
        return categoryService.getAllHabits();
    }
}
