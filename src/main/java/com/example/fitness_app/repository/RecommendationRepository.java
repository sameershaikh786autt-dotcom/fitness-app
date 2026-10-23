package com.example.fitness_app.repository;

import com.example.fitness_app.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, String> {
    List<Recommendation> findByUserId(String userid);

    List<Recommendation> findByActivityId(String actvityid);
}
