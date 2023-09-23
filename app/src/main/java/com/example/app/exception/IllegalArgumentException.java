package com.example.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class handles code 409 conflict, with illegal argument.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class IllegalArgumentException extends RuntimeException {
    public IllegalArgumentException(String message){
        super(message);
    }
}