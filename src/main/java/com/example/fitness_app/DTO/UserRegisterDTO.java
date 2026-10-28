package com.example.fitness_app.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    @NotBlank(message = "First name is required")
    private String firstName;
    private String lastName;
    private String name;

    @NotBlank
    @Email(message = "Invalid email")
    private String email;

    @NotBlank
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 6 characters")
    private String password;
}
