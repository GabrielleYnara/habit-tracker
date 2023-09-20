package com.example.app.repository;

import com.example.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  A Spring Data JPA repository for managing User entities.<br>
 *  It extends JpaRepository, which provides methods for basic CRUD operations.
 *
 * @version 1.1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> { //Entity and primary key data type
    /**
     * Checks if a user with given emailAddress exists.
     * @param emailAddress
     * @return A User if it finds a match,
     *         null otherwise.
     */
    User findUserByEmailAddress(String emailAddress);
}
