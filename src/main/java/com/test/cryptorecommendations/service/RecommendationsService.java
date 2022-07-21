package com.test.cryptorecommendations.service;

import com.test.cryptorecommendations.controller.dto.RecommendationDTO;
import com.test.cryptorecommendations.service.exception.CryptoNotFoundException;
import com.test.cryptorecommendations.service.model.CryptoModel;
import com.test.cryptorecommendations.service.model.RecommendationModel;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface RecommendationsService {
    RecommendationModel getRecommendation(String cryptoCode);

    List<RecommendationModel> getAll();

    RecommendationModel getWithHighestNormalizedRangeByDay(LocalDate date);

    Set<String> getSupportedCryptoCodes();

    List<RecommendationModel> getSortedDesc();

    RecommendationModel getHighestNormalizedRange();

    CryptoModel recommendMinForCrypto(String cryptoCode);

    CryptoModel recommendMaxForCrypto(String cryptoCode);

    CryptoModel recommendNewestForCrypto(String cryptoCode);

    CryptoModel recommendOldestForCrypto(String cryptoCode);
}
