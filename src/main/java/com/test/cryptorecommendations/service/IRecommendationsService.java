package com.test.cryptorecommendations.service;

import com.test.cryptorecommendations.model.RecommendationDTO;

import java.util.List;

public interface IRecommendationsService {
    public List<RecommendationDTO> readExcel();
    public List<RecommendationDTO> findAll();
}
