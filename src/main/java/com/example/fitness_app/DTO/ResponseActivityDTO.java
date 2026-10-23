package com.example.fitness_app.DTO;


import com.example.fitness_app.Enum.ActivityType;
import com.example.fitness_app.model.Recommendation;
import com.example.fitness_app.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseActivityDTO {
    private String userId;
    private String activityId;
    private Integer duration;
    private Integer caloriesBurned;
    private ActivityType activityType;
    private HashMap<String, Object> additionalDetails;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime startTime;
}
