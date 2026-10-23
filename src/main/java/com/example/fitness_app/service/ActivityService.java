package com.example.fitness_app.service;

import com.example.fitness_app.DTO.RegisterActivityDTO;
import com.example.fitness_app.DTO.ResponseActivityDTO;
import com.example.fitness_app.model.Activity;
import com.example.fitness_app.model.User;
import com.example.fitness_app.repository.ActivitiesRepository;
import com.example.fitness_app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Builder
public class ActivityService {
    private final ActivitiesRepository activitiesRepository;
    private final UserRepository userRepository;

    public ResponseActivityDTO createActivity(RegisterActivityDTO registerActivityDTO) {

        // ACTIVITY CREATE KRNE K LIYE
        Activity newActivity = mapToActivityEntity(registerActivityDTO);

        // SAVE KRNE K LIYE
        Activity savedActivity = saveActivity(newActivity);

        // RESPONSE DENE K LIYE
        ResponseActivityDTO responseActivityDTO = newResponseEntityToDto(savedActivity);
        return responseActivityDTO;
    }
    private Activity mapToActivityEntity(RegisterActivityDTO registerActivityDTO) {
        User user = userRepository.findById(registerActivityDTO.getUser())
                .orElseThrow(() -> new RuntimeException("User not found: " +  registerActivityDTO.getUser()));

        Activity newActivity = Activity.builder()
                .user(user)
                .activityType(registerActivityDTO.getActivityType())
                .caloriesBurned(registerActivityDTO.getCaloriesBurned())
                .duration(registerActivityDTO.getDuration())
                .additionalDetails(registerActivityDTO.getAdditionalDetails())
                .startTime(registerActivityDTO.getStartTime())
                .build();

        return newActivity;
    }

    private Activity saveActivity(Activity newActivity) {
        return activitiesRepository.save(newActivity);
    }

    private ResponseActivityDTO newResponseEntityToDto(Activity savedActivity) {
        ResponseActivityDTO responseActivityDTO = ResponseActivityDTO.builder()
                .userId(savedActivity.getUser().getId())
                .activityType(savedActivity.getActivityType())
                .caloriesBurned(savedActivity.getCaloriesBurned())
                .duration(savedActivity.getDuration())
                .startTime(savedActivity.getStartTime())
                .activityId(savedActivity.getId())
                .createdAt(savedActivity.getCreatedAt())
                .updatedAt(savedActivity.getUpdatedAt())
                .additionalDetails(savedActivity.getAdditionalDetails())
                .build();

        return responseActivityDTO;
    }

    public List<ResponseActivityDTO> getUserActivity(String userId) {
        System.out.println("Searching for userId: [" + userId + "]");
        List<Activity> activityList = activitiesRepository.findByUserId(userId);


        return activityList.stream()
                .map(this::newResponseEntityToDto)
                .toList();
    }
}
