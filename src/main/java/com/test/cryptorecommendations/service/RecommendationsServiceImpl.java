package com.test.cryptorecommendations.service;

import com.test.cryptorecommendations.data.CryptorLoader;
import com.test.cryptorecommendations.data.entity.CryptoEntity;
import com.test.cryptorecommendations.service.exception.CryptoException;
import com.test.cryptorecommendations.service.exception.CryptoNotFoundException;
import com.test.cryptorecommendations.service.model.CryptoModel;
import com.test.cryptorecommendations.service.model.RecommendationModel;
import com.test.cryptorecommendations.utilities.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

// <summary>
// Business logic
// </summary>
@Service
public class RecommendationsServiceImpl implements RecommendationsService {

    @Autowired
    private CryptorLoader cryptoLoader;

    // Get recommendation object (a cryptocurrency with entries list and sensitive data)
    @Override
    public RecommendationModel getRecommendation(String cryptoCode) {
        Assert.notNull(cryptoCode, "cryptoCode is required");

        Set<String> supportedCryptoCodes = getSupportedCryptoCodes();
        Assert.isTrue(supportedCryptoCodes.contains(cryptoCode), ServiceErrorMessages.NOT_SUPPORTED_CRYPTO_CODE);

        List<CryptoEntity> cryptoEntities = cryptoLoader.readCrypto(cryptoCode);
        if(cryptoEntities.isEmpty())
            throw new CryptoNotFoundException();
        List<CryptoModel> cryptoModels = cryptoEntities.stream().map(ModelConverter::cryptoEntityToModel).collect(Collectors.toList());
        RecommendationModel result = new RecommendationModel(cryptoCode, cryptoModels);

        return result;
    }

    // List all currencies
    @Override
    public List<RecommendationModel> getAll() {
        Set<String> codes = getSupportedCryptoCodes();
        return codes.stream().map(this::getRecommendation).collect(Collectors.toList());
    }

    // Get supported crypto codes
    @Override
    public Set<String> getSupportedCryptoCodes() {
        try {
            return cryptoLoader.getSupportedCryptoCodes();
        } catch (IOException ex) {
            throw new CryptoException(ServiceErrorMessages.GET_CRYPTO_CODDES_ERR);
        }
    }

    // Descending sorted list of all the cryptos, comparing the normalized range (i.e. (max-min)/min)
    @Override
    public List<RecommendationModel> getSortedDesc() {
        return getAll().stream()
                .sorted(Comparator.comparing(RecommendationModel::getNormalizedValue).reversed()).collect(Collectors.toList());
    }

    // Currently unused, but retrieves a cryptocurrency with highest normalized range in a file (month).
    @Override
    public RecommendationModel getHighestNormalizedRange() {
        return getAll().stream()
                .sorted(Comparator.comparing(RecommendationModel::getNormalizedValue).reversed()).findFirst().
                orElseThrow(NoSuchElementException::new);
    }

    // Retrieves a cryptocurrency with highest normalized range for a specific day.
    @Override
    public RecommendationModel getWithHighestNormalizedRangeByDay(LocalDate date) {
        return getAll().stream()
                .filter(recommendationModel -> recommendationModel.getMinByDay(date).isPresent())
                .filter(recommendationModel -> recommendationModel.getMaxByDay(date).isPresent())
                .max(Comparator.comparing(recommendationModel -> recommendationModel.getNormalizedValueByDay(date)))
                .orElseThrow(NoSuchElementException::new);
    }

    // Retrieves a cryptocurrency entry the minimum price by currency symbol.
    @Override
    public CryptoModel recommendMinForCrypto(String cryptoCode) {
        Assert.notNull(cryptoCode, "cryptoCode is required");
        return getRecommendation(cryptoCode).getMin();
    }

    // Retrieves a cryptocurrency entry the maximum price by currency symbol.
    @Override
    public CryptoModel recommendMaxForCrypto(String cryptoCode) {
        Assert.notNull(cryptoCode, "cryptoCode is required");
        return getRecommendation(cryptoCode).getMax();
    }

    // Retrieves the most fresh cryptocurrency entry by currency symbol.
    @Override
    public CryptoModel recommendNewestForCrypto(String cryptoCode){
        Assert.notNull(cryptoCode, "cryptoCode is required");
        return getRecommendation(cryptoCode).getNewest();
    }

    // Retrieves the oldest cryptocurrency entry by currency symbol.
    @Override
    public CryptoModel recommendOldestForCrypto(String cryptoCode){
        Assert.notNull(cryptoCode, "cryptoCode is required");
        return getRecommendation(cryptoCode).getOldest();
    }
}
