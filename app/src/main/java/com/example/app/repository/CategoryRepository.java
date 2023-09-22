package com.example.app.repository;

import com.example.app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> { // Entity and primary key data type
    Category findByNameAndUserId(String categoryName, Long userId);
}
