package com.example.fitness_app.controller;

import com.example.fitness_app.DTO.RegisterActivityDTO;
import com.example.fitness_app.DTO.ResponseActivityDTO;
import com.example.fitness_app.model.Activity;
import com.example.fitness_app.service.ActivityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class ActivitiesController {
    private final ActivityService activityService;



    @PostMapping("/activities")
    public ResponseEntity<ResponseActivityDTO> addActivity(@Valid @RequestBody RegisterActivityDTO registerActivityDTO) {
        ResponseActivityDTO responseActivityDTO = activityService.createActivity(registerActivityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseActivityDTO);
    }


    @GetMapping("/activities")
    public ResponseEntity<List<ResponseActivityDTO>> getUserActivities(
            @RequestHeader(value = "X-USER-ID") String userId
    ) {

        return ResponseEntity.ok(activityService.getUserActivity(userId));
    }
}
