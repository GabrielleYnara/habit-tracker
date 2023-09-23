package com.example.app.controller;

import com.example.app.model.Category;
import com.example.app.model.Habit;
import com.example.app.model.PracticeTracker;
import com.example.app.service.CategoryService;
import com.example.app.service.PracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Category Controller for handling category management operations.<br>
 * Note: Imported and refactored from todo project
 * @version 1.3.0
 */
@RestController
@RequestMapping("/api") //http://localhost:9009/api
public class CategoryController {
    private CategoryService categoryService;
    private PracticeService practiceService;
    @Autowired //"Connects" category service class, able to use its methods
    public void setCategoryService(CategoryService categoryService){
        this.categoryService = categoryService;
    }
    @Autowired
    public void setPracticeService(PracticeService practiceService) {
        this.practiceService = practiceService;
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
        return Optional.ofNullable(categoryService.getHabitByIdAndCategory(categoryId, habitId));
    }
    @GetMapping(path = "/habits/") //http://localhost:9009/api/habits/
    public List<Habit> getAllHabits() {
        return categoryService.getAllHabits();
    }

    @PutMapping(path = "/habits/{habitId}/") //http://localhost:9009/api/habits/1/
    public Optional<Habit> updateHabit(@PathVariable(value = "habitId") Long habitId, @RequestBody Habit habit) throws IllegalAccessException {
        habit.setId(habitId);
        return Optional.ofNullable(categoryService.updateHabit(habit));
    }

    @DeleteMapping(path = "/categories/{categoryId}/habits/{habitId}/") //http://localhost:9009/api/categories/1/habits/1/
    public Optional<Habit> deleteHabit(@PathVariable(value = "categoryId") Long categoryId, @PathVariable(value = "habitId") Long habitId) {
        return categoryService.deleteHabit(categoryId, habitId);
    }
    //----------------------------------------- Practice Tracker related -----------------------------------------

    @PostMapping(path = "/habits/{habitId}/practices/") //http://localhost:9009/api/habits/1/practices/
    public PracticeTracker createPractice(@PathVariable(value = "habitId") Long habitId, @RequestBody PracticeTracker practiceTracker) {
        return practiceService.createPractice(habitId, practiceTracker);
    }
    @GetMapping(path = "/practices/{habitId}/") //http://localhost:9009/api/practices/1/
    public PracticeTracker getPracticeById(@PathVariable(value = "habitId") Long habitId){
        return practiceService.getPracticeById(habitId);
    }

    @GetMapping(path = "/practices/{date}/") //http://localhost:9009/api/practices/2023-09-21/
    public List<PracticeTracker> getPracticeById(@PathVariable(value = "date") String date){
        LocalDate localDate = LocalDate.parse(date);
        return practiceService.getPracticeByDate(localDate);
    }
    @GetMapping(path = "/practices/") //http://localhost:9009/api/practices/
    public List<PracticeTracker> getAllPractices(){
        return practiceService.getAllPractices();
    }


    @PutMapping(path = "/practices/{practiceId}") //http://localhost:9009/api/practices/1/
    public PracticeTracker updatePractice(@PathVariable(value = "practiceId") Long practiceId,@RequestBody PracticeTracker practice) throws IllegalAccessException {
        practice.setId(practiceId);
        return practiceService.updatePractice(practice);
    }

    @DeleteMapping(path = "/practices/{practiceId}/") //http://localhost:9009/api//practices/1/
    public Optional<PracticeTracker> deletePractice(@PathVariable(value = "practiceId") Long practiceId) {
        return practiceService.deletePractice(practiceId);
    }

}
