package com.example.fitness_app.globalException;


public class PasswordValidation extends RuntimeException  {
    public PasswordValidation(String message) {
        super(message);
    }

}
