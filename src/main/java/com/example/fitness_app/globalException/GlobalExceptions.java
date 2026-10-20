package com.example.fitness_app.globalException;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(PasswordValidation.class)
    public ResponseEntity<Map<String, Object>> handlePasswordValidation(PasswordValidation passwordValidation) {
        HashMap<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.BAD_REQUEST.value()); // Returns integer code 400
        error.put("error", "Bad Request");
        error.put("message", passwordValidation.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // valid argument
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        HashMap<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("error", "Validation Failed");
        error.put("message", ex.getMessage());

        // DTO ke annotation me likha hua message yahan se aayega
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        error.put("message", errorMessage);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}

