package com.example.fitness_app.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRecommendationDTO {
    @NotNull(message = "User ID cannot be null")
    private String user; // USER ID

    @NotNull(message = "Activity ID cannot be null")
    private String activity;  // ACTIVITY ID

    @NotBlank(message = "Type cannot be blank")
    private String type;
    @NotBlank(message = "Recommendation text cannot be blank")
    private String recommendation;

    private List<String> improvements;

    private List<String> suggestions;

    private List<String> safety;
}
