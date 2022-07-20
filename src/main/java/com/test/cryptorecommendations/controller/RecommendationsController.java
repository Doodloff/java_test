package com.test.cryptorecommendations.controller;

import com.test.cryptorecommendations.controller.dto.RecommendationDTO;
import com.test.cryptorecommendations.service.RecommendationsService;
import com.test.cryptorecommendations.service.model.CryptoModel;
import com.test.cryptorecommendations.service.model.RecommendationModel;
import com.test.cryptorecommendations.utilities.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationsController {

    @Autowired
    private RecommendationsService service;

    // todo: Rename RecommendationDTO and RecommendationModel. They are objects, containing info about concrete Currency, not something recommendational.

    @GetMapping
    public List<RecommendationDTO> getAll() throws IOException {
        List<RecommendationDTO> response = new ArrayList<>();
        List<RecommendationModel> recommendationModels = service.getAll();

        for (RecommendationModel model : recommendationModels) {
            RecommendationDTO dto = ModelConverter.recommendationModelToDTO(model);
            response.add(dto);
        }

        return response;
    }

    // Exposes an endpoint that will return a descending sorted list of all the cryptos,
    // comparing the normalized range (i.e. (max-min)/min)
    @GetMapping
    public List<RecommendationDTO> getSortedDesc() {
        return null;
    }

    // Calculates oldest/newest/min/max for each crypto for the whole month
    // Exposes an endpoint that will return the oldest/newest/min/max values for a requested
    // crypto

    // todo: Consider checking for currency identifier
    // -- is month also requisted or is the last one?
    @GetMapping(value = "/{name}/min")
    public RecommendationDTO getMin(@PathVariable("name") String name) {
        return null;
    }

    //todo: Consider checking for currency identifier and month in attributes
    @GetMapping(value = "/{name}/max")
    public RecommendationDTO getMax(@PathVariable("name") String name) {
        return null;
    }

    //todo: Consider checking for currency identifier and month in attributes
    @GetMapping(value = "/{name}/newest")
    public RecommendationDTO getNewest(@PathVariable("name") String name) {
        return null;
    }

    //todo: Consider checking for currency identifier and month in attributes
    @GetMapping(value = "/{name}/oldest")
    public RecommendationDTO getOldest(@PathVariable("name") String name) {
        return null;
    }

    // Exposes an endpoint that will return the crypto with the highest normalized range for a
    // specific day
    @GetMapping("/highestNormalized")
    public String getHighestNormilized () {
        return null;
    }
}
