package com.test.cryptorecommendations.controller;

import com.test.cryptorecommendations.controller.dto.CryptoDTO;
import com.test.cryptorecommendations.controller.dto.RecommendationDTO;
import com.test.cryptorecommendations.service.RecommendationsService;
import com.test.cryptorecommendations.service.model.CryptoModel;
import com.test.cryptorecommendations.service.model.RecommendationModel;
import com.test.cryptorecommendations.utilities.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recommendations")
public class RecommendationsController {

    @Autowired
    private RecommendationsService service;

    @GetMapping
    public List<RecommendationDTO> getAll() {
        List<RecommendationModel> recommendationModels = service.getAll();
        List<RecommendationDTO> response = recommendationModels.stream()
                .map(ModelConverter::recommendationModelToDTO).collect(Collectors.toList());

        return response;
    }

    // Exposes an endpoint that will return a descending sorted list of all the cryptos,
    // comparing the normalized range (i.e. (max-min)/min)
    @GetMapping(value = "/sorted")
    public List<RecommendationDTO> getSortedDesc() {
        List<RecommendationModel> recommendationModels = service.getSortedDesc();
        List<RecommendationDTO> response = recommendationModels.stream().map(ModelConverter::recommendationModelToDTO).collect(Collectors.toList());

        return response;
    }

    // Calculates oldest/newest/min/max for each crypto for the whole month
    // Exposes an endpoint that will return the oldest/newest/min/max values for a requested
    // crypto

    // todo: Consider checking for currency identifier
    // -- is month also requisted or is the last one?
    @GetMapping(value = "/{name}/min")
    public CryptoDTO getMin(@PathVariable("name") String name) {
        CryptoModel cryptoModel = service.recommendMinForCrypto(name);
        CryptoDTO response = ModelConverter.cryptoModelToDTO(cryptoModel);

        return response;
    }

    //todo: Consider checking for currency identifier and month in attributes
    @GetMapping(value = "/{name}/max")
    public CryptoDTO getMax(@PathVariable("name") String name) {
        CryptoModel cryptoModel = service.recommendMaxForCrypto(name);
        CryptoDTO response = ModelConverter.cryptoModelToDTO(cryptoModel);

        return response;
    }

    //todo: Consider checking for currency identifier and month in attributes
    @GetMapping(value = "/{name}/newest")
    public CryptoDTO getNewest(@PathVariable("name") String name) {
        CryptoModel cryptoModel = service.recommendNewestForCrypto(name);
        CryptoDTO response = ModelConverter.cryptoModelToDTO(cryptoModel);

        return response;
    }

    //todo: Consider checking for currency identifier and month in attributes
    @GetMapping(value = "/{name}/oldest")
    public CryptoDTO getOldest(@PathVariable("name") String name) {
        CryptoModel cryptoModel = service.recommendOldestForCrypto(name);
        CryptoDTO response = ModelConverter.cryptoModelToDTO(cryptoModel);

        return response;
    }

    // Exposes an endpoint that will return the crypto with the highest normalized range for a
    // specific day
    @PostMapping("/highestNormalized")
    public RecommendationDTO getHighestNormilizedByDay (@RequestParam("localDate")
                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        RecommendationDTO response = ModelConverter.recommendationModelToDTO(service.getWithHighestNormalizedRangeByDay(date));
        return response;
    }
}
