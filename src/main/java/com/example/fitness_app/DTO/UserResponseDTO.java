package com.example.fitness_app.DTO;

import com.example.fitness_app.Enum.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String name;
    private String email;
    private Roles role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
