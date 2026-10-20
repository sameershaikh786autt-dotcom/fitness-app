package com.example.fitness_app.controller;


import com.example.fitness_app.DTO.UserRegisterDTO;
import com.example.fitness_app.DTO.UserResponseDTO;
import com.example.fitness_app.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth/")
public class UserController {
    private final UserService userService;


    @PostMapping("/registers")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
//        System.out.println("registerUser: " +  userRegisterDTO);
        UserResponseDTO userRegistered = userService.createUser(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRegistered);
    }

}
