package com.example.fitness_app.service;

import com.example.fitness_app.DTO.RegisterRecommendationDTO;
import com.example.fitness_app.DTO.ResponseRecommendationDTO;
import com.example.fitness_app.model.Activity;
import com.example.fitness_app.model.Recommendation;
import com.example.fitness_app.model.User;
import com.example.fitness_app.repository.ActivitiesRepository;
import com.example.fitness_app.repository.RecommendationRepository;
import com.example.fitness_app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository  recommendationRepository;
    private final UserRepository userRepository;
    private final ActivitiesRepository activitiesRepository;


    public ResponseRecommendationDTO generateRecommendation(RegisterRecommendationDTO registerRecommendationDTO) {
        Recommendation recommendation = DTOToEntity(registerRecommendationDTO);

        Recommendation saved = saveRecommendation(recommendation);

        ResponseRecommendationDTO responseRecommendationDTO = EntityToDTO(saved);

        return responseRecommendationDTO;
    }

    // FOR SET THE VALUES INTO A ENTITY
    public Recommendation DTOToEntity(RegisterRecommendationDTO registerRecommendationDTO) {
        // USER ID GET KRNE K LIYE
        User user = userRepository.findById(registerRecommendationDTO.getUser())
                .orElseThrow(() -> new RuntimeException("User not found: " +  registerRecommendationDTO.getUser()));

        // ACTIVITY ID K LIYE
        Activity activity = activitiesRepository.findById(registerRecommendationDTO.getActivity())
                .orElseThrow(() -> new RuntimeException("Activity not found: " +  registerRecommendationDTO.getActivity()));

        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .activity(activity)
                .type(registerRecommendationDTO.getType())
                .recommendation(registerRecommendationDTO.getRecommendation())
                .improvements(registerRecommendationDTO.getImprovements())
                .safety(registerRecommendationDTO.getSafety())
                .suggestions(registerRecommendationDTO.getSuggestions())
                .build();

        return recommendation;
    }


    // FOR SAVE THE RECOMMENDATION INTO A ENTITY
    private Recommendation saveRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }

    // FOR RESPONSE K LIYE
    private ResponseRecommendationDTO EntityToDTO(Recommendation recommendation) {
        if (recommendation == null) {
            return null;
        }

        ResponseRecommendationDTO response = ResponseRecommendationDTO.builder()
                .id(recommendation.getId() != null ? recommendation.getId().toString() : null)

                .user(recommendation.getUser() != null && recommendation.getUser().getId() != null
                        ? recommendation.getUser().getId().toString() : null)

                .activity(recommendation.getActivity() != null && recommendation.getActivity().getId() != null
                        ? recommendation.getActivity().getId().toString() : null)

                .type(recommendation.getType())
                .recommendation(recommendation.getRecommendation())
                .improvements(recommendation.getImprovements())
                .safety(recommendation.getSafety())
                .suggestions(recommendation.getSuggestions())
                .createdDate(recommendation.getCreatedDate())
                .updatedDate(recommendation.getUpdatedDate())
                .build();


        return response;
    }

    public List<Recommendation> getRecommendationsByUserId(String userid) {
        return recommendationRepository.findByUserId(userid);
    }

    public List<Recommendation> getRecommendationsByActivityId(String actvityid) {
        return recommendationRepository.findByActivityId(actvityid);
    }
}
