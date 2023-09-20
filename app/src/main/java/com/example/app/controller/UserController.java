package com.example.app.controller;

import com.example.app.model.User;
import com.example.app.model.request.LoginRequest;
import com.example.app.model.response.LoginResponse;
import com.example.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * User Controller for handling user operations including authentication, registration, and profile management.
 *
 * @version 1.1.0
 */
@RestController
@RequestMapping(path = "/auth/users") //http://localhost:9009/auth/users
public class UserController {
    private UserService userService;

    /**
     * Injects dependencies and enables userController to access the resources
     * @param userService The Service responsible for user business logic.
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    /**
     * Handles user registration by creating a new user.
     *
     * @param user User object containing the information of the user to be registered.
     * @return The newly created User object.
     */
    @PostMapping(path = "/register/") //http://localhost:9009/auth/users/register/
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    /**
     * Handles user authentication by generating a JWT token if the credentials are valid.
     *
     * @param loginRequest The LoginRequest containing the user's login credentials.
     * @return A ResponseEntity with a LoginResponse, which includes the JWT token if successful,
     *         or an error message otherwise.
     */
    @PostMapping(path="/login/") //http://localhost:9009/auth/users/login/
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<String> jwtToken = userService.loginUser(loginRequest);
        if (jwtToken.isPresent()){
            return ResponseEntity.ok(new LoginResponse(jwtToken.get()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Authentication failed!"));
        }
    }
}
