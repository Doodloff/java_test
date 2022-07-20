package com.test.cryptorecommendations.service;

import com.opencsv.CSVReader;
import com.test.cryptorecommendations.controller.dto.RecommendationDTO;
import com.test.cryptorecommendations.data.CryptorLoader;
import com.test.cryptorecommendations.data.entity.CryptoEntity;
import com.test.cryptorecommendations.service.model.CryptoModel;
import com.test.cryptorecommendations.service.model.RecommendationModel;
import com.test.cryptorecommendations.utilities.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecommendationsServiceImpl implements RecommendationsService {

    @Autowired
    CryptorLoader cryptoLoader;

    @Override
    public RecommendationModel getRecommendation(String cryptoCode){

        List<CryptoEntity> cryptoEntities = cryptoLoader.readCrypto(cryptoCode);
        List<CryptoModel> cryptoModels = new ArrayList<>();

        for(CryptoEntity e : cryptoEntities) {
            CryptoModel model = ModelConverter.cryptoEntityToModel(e);
            cryptoModels.add(model);
        }

        RecommendationModel result = new RecommendationModel(cryptoCode, cryptoModels);

        //result = getMAx/Min/...

        return result;
    }

    @Override
    public List<RecommendationModel> getAll() throws IOException {
        Map<String, List<CryptoEntity>> entityMap = cryptoLoader.getAllCryptos();
        List<RecommendationModel> result = new ArrayList<>();

        for (Map.Entry<String, List<CryptoEntity>> entry : entityMap.entrySet()) {
            List<CryptoModel> modelList = new ArrayList<>();
            for (CryptoEntity entity : entry.getValue()) {
                modelList.add(ModelConverter.cryptoEntityToModel(entity));
            }
            RecommendationModel recommendationModel = new RecommendationModel(entry.getKey(), modelList);
            result.add(recommendationModel);
        }
        return result;
    }

    @Override
    public List<RecommendationModel> getSortedDesc() throws IOException {
        List<RecommendationModel> result = getAll();

//        List<RecommendationModel> reverseSortedLetters = result.stream()
//                .sorted(Comparator.reverseOrder())
//                .collect(Collectors.toList());

//        return result.stream().sorted().collect(Collectors.toList());

        return result.stream().sorted(Comparator.comparing(RecommendationModel::getNormalizedValue).reversed()).collect(Collectors.toList());
    }

    @Override
    public CryptoModel getMinForCrypto(String cryptoCode){
        List<CryptoEntity> cryptoEntities = cryptoLoader.readCrypto(cryptoCode);

        CryptoEntity entity = cryptoEntities.stream()
                .min(Comparator.comparing(CryptoEntity::getPrice))
                .orElseThrow(NoSuchElementException::new);

        return ModelConverter.cryptoEntityToModel(entity);
    }

    @Override
    public CryptoModel getMaxForCrypto(String cryptoCode){
        List<CryptoEntity> cryptoEntities = cryptoLoader.readCrypto(cryptoCode);

        CryptoEntity entity = cryptoEntities.stream()
                .max(Comparator.comparing(CryptoEntity::getPrice))
                .orElseThrow(NoSuchElementException::new);

        return ModelConverter.cryptoEntityToModel(entity);
    }

    @Override
    public CryptoModel getNewestForCrypto(String cryptoCode){
        List<CryptoEntity> cryptoEntities = cryptoLoader.readCrypto(cryptoCode);

        CryptoEntity entity = cryptoEntities.stream()
                .min(Comparator.comparing(CryptoEntity::getTimestamp))
                .orElseThrow(NoSuchElementException::new);

        return ModelConverter.cryptoEntityToModel(entity);
    }

    @Override
    public CryptoModel getOldestForCrypto(String cryptoCode){
        List<CryptoEntity> cryptoEntities = cryptoLoader.readCrypto(cryptoCode);

        CryptoEntity entity = cryptoEntities.stream()
                .max(Comparator.comparing(CryptoEntity::getTimestamp))
                .orElseThrow(NoSuchElementException::new);

        return ModelConverter.cryptoEntityToModel(entity);
    }
}
