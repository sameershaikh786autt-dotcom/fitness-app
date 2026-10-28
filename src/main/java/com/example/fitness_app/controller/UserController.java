package com.example.fitness_app.controller;


import com.example.fitness_app.DTO.LoginDetails;
import com.example.fitness_app.DTO.ResponseLogin;
import com.example.fitness_app.DTO.UserRegisterDTO;
import com.example.fitness_app.DTO.UserResponseDTO;
import com.example.fitness_app.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    // LOGIN USER
    @PostMapping("/login")
    public ResponseEntity<ResponseLogin> login(@RequestBody LoginDetails loginDetails) {
        ResponseLogin response = userService.loginUser(loginDetails);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String getCurrentUser() {
        return "HELLO USER";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String getCurrentAdmin() {
        return "HELLO ADMIN";
    }

    @GetMapping("/all")
    public String getAllUsers() {
        return "Get all users";
    }
}
