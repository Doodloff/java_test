package com.test.cryptorecommendations.service;

import com.test.cryptorecommendations.service.model.CryptoModel;
import com.test.cryptorecommendations.service.model.RecommendationModel;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface RecommendationsService {
    // Get recommendation object (a cryptocurrency with entries list and sensitive data)
    RecommendationModel getRecommendation(String cryptoCode);
    // List all currencies
    List<RecommendationModel> getAll();
    // Retrieves a cryptocurrency with highest normalized range for a specific day.
    RecommendationModel getWithHighestNormalizedRangeByDay(LocalDate date);
    // Get supported crypto codes
    Set<String> getSupportedCryptoCodes();
    // Descending sorted list of all the cryptos, comparing the normalized range (i.e. (max-min)/min)
    List<RecommendationModel> getSortedDesc();
    // Currently unused, but retrieves a cryptocurrency with highest normalized range in a file (month).
    RecommendationModel getHighestNormalizedRange();
    // Retrieves a cryptocurrency entry the minimum price by currency symbol.
    CryptoModel recommendMinForCrypto(String cryptoCode);
    // Retrieves a cryptocurrency entry the maximum price by currency symbol.
    CryptoModel recommendMaxForCrypto(String cryptoCode);
    // Retrieves the most fresh cryptocurrency entry by currency symbol.
    CryptoModel recommendNewestForCrypto(String cryptoCode);
    // Retrieves the oldest cryptocurrency entry by currency symbol.
    CryptoModel recommendOldestForCrypto(String cryptoCode);
}
