package com.test.cryptorecommendations.service;

import com.test.cryptorecommendations.controller.dto.RecommendationDTO;
import com.test.cryptorecommendations.service.model.CryptoModel;
import com.test.cryptorecommendations.service.model.RecommendationModel;

import java.io.IOException;
import java.util.List;

public interface RecommendationsService {
    RecommendationModel getRecommendation(String cryptoCode);

    List<RecommendationModel> getAll() throws IOException;

    List<RecommendationModel> getSortedDesc() throws IOException;

    CryptoModel getMinForCrypto(String cryptoCode);

    CryptoModel getMaxForCrypto(String cryptoCode);

    CryptoModel getNewestForCrypto(String cryptoCode);

    CryptoModel getOldestForCrypto(String cryptoCode);
}
