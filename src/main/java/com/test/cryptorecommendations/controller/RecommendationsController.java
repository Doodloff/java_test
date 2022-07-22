package com.test.cryptorecommendations.controller;

import com.test.cryptorecommendations.controller.dto.CryptoDTO;
import com.test.cryptorecommendations.controller.dto.RecommendationDTO;
import com.test.cryptorecommendations.service.RecommendationsService;
import com.test.cryptorecommendations.service.model.CryptoModel;
import com.test.cryptorecommendations.service.model.RecommendationModel;
import com.test.cryptorecommendations.utilities.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recommendations")
public class RecommendationsController {

    @Autowired
    private RecommendationsService service;

    // All cryptos
    @GetMapping
    public List<RecommendationDTO> getAll() {
        List<RecommendationModel> recommendationModels = service.getAll();
        List<RecommendationDTO> response = recommendationModels.stream()
                .map(ModelConverter::recommendationModelToDTO).collect(Collectors.toList());

        return response;
    }

    // Descending sorted list of all the cryptos, comparing the normalized range (i.e. (max-min)/min)
    @GetMapping(value = "/sorted")
    public List<RecommendationDTO> getSortedDesc() {
        List<RecommendationModel> recommendationModels = service.getSortedDesc();
        List<RecommendationDTO> response = recommendationModels.stream().map(ModelConverter::recommendationModelToDTO).collect(Collectors.toList());

        return response;
    }

    // Minimum value for a crypto
    @GetMapping(value = "/{name}/min")
    public CryptoDTO getMin(@PathVariable("name") String name) {
        try {
            CryptoModel cryptoModel = service.recommendMinForCrypto(name);
            CryptoDTO response = ModelConverter.cryptoModelToDTO(cryptoModel);

            return response;
        } catch (IllegalArgumentException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, EndpointErrorMessages.NOT_SUPPORTED_CRYPTO_CODE, exc);
        }
    }

    // Maximum value for a crypto
    @GetMapping(value = "/{name}/max")
    public CryptoDTO getMax(@PathVariable("name") String name) {
        try {
            CryptoModel cryptoModel = service.recommendMaxForCrypto(name);
            CryptoDTO response = ModelConverter.cryptoModelToDTO(cryptoModel);

            return response;
        } catch (IllegalArgumentException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, EndpointErrorMessages.NOT_SUPPORTED_CRYPTO_CODE, exc);
        }
    }

    // Newest value for a crypto
    @GetMapping(value = "/{name}/newest")
    public CryptoDTO getNewest(@PathVariable("name") String name) {
        try {
            CryptoModel cryptoModel = service.recommendNewestForCrypto(name);
            CryptoDTO response = ModelConverter.cryptoModelToDTO(cryptoModel);

            return response;
        } catch (IllegalArgumentException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, EndpointErrorMessages.NOT_SUPPORTED_CRYPTO_CODE, exc);
        }
    }

    // Oldest value for a crypto
    @GetMapping(value = "/{name}/oldest")
    public CryptoDTO getOldest(@PathVariable("name") String name) {
        try {
            CryptoModel cryptoModel = service.recommendOldestForCrypto(name);
            CryptoDTO response = ModelConverter.cryptoModelToDTO(cryptoModel);

            return response;
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, EndpointErrorMessages.NOT_SUPPORTED_CRYPTO_CODE, ex);
        }
    }

    // Crypto with the highest normalized range for a specific day
    @GetMapping("/highestNormalized")
    public RecommendationDTO getHighestNormilizedByDay (@RequestParam("localDate")
                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        RecommendationDTO response = ModelConverter.recommendationModelToDTO(service.getWithHighestNormalizedRangeByDay(date));
        return response;
    }
}
