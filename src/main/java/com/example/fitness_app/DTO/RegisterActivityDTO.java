package com.example.fitness_app.DTO;


import com.example.fitness_app.Enum.ActivityType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterActivityDTO {
    @NotNull(message = "User ID cannot be null")
    private String user; // USER ID

    @NotNull(message = "Activity type is required")
    private ActivityType activityType;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than 0")
    private Integer duration; // Minutes

    @PositiveOrZero(message = "Calories burned cannot be negative")
    private Integer caloriesBurned; // Optional ya manual entry

    private LocalDateTime startTime; // Workout kab shuru kiya

    private HashMap<String, Object> additionalDetails; // Steps, distance, avg heart rate, etc.
}
