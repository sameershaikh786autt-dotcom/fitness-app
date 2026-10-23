package com.example.fitness_app.controller;


import com.example.fitness_app.DTO.RegisterRecommendationDTO;
import com.example.fitness_app.DTO.ResponseRecommendationDTO;
import com.example.fitness_app.service.RecommendationService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @PostMapping("/generate")
    public ResponseEntity<ResponseRecommendationDTO> generateRecommendation(
            @RequestBody RegisterRecommendationDTO  registerRecommendationDTO
    ) {
        ResponseRecommendationDTO responseRecommendationDTO = recommendationService.generateRecommendation(registerRecommendationDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseRecommendationDTO);
    }


    // FOR USER GET RECOMMENDATION BY USER_ID
    @GetMapping("/user/{userid}")
    public ResponseEntity<ResponseRecommendationDTO> getUserRecommendation(@PathVariable("userid") long userid) {
        return null;
    }


    // FOR ACTIVITY GET RECOMMENDATION BY ACTIVITY_ID
    @GetMapping("/activity/{activityid}")
    public ResponseEntity<ResponseRecommendationDTO> getActivityRecommendation(@PathVariable("activityid") long actvityid) {
        return null;
    }

}
