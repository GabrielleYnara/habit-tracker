package com.example.app.service;

import com.example.app.model.User;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Represents the User Service, responsible for housing business logic related to users.<br>
 *
 * This class serves as an intermediary between the controller and the repository,
 * invoking the repository to perform CRUD operations on users.
 *
 * @version 1.1.0
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * Injects dependencies and enables userService to access the resources.
     * @param userRepository The repository for user-related CRUD operations.
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Finds a User entity based on the provided email address.
     *
     * @param emailAddress The email address to search for.
     * @return The User entity,
     *         or null if no matching user is found.
     */
    public User findUserByEmailAddress(String emailAddress){
        return userRepository.findUserByEmailAddress(emailAddress);
    }
}
