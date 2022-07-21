package com.test.cryptorecommendations.utilities;

import com.test.cryptorecommendations.controller.dto.CryptoDTO;
import com.test.cryptorecommendations.controller.dto.RecommendationDTO;
import com.test.cryptorecommendations.data.entity.CryptoEntity;
import com.test.cryptorecommendations.service.model.CryptoModel;
import com.test.cryptorecommendations.service.model.RecommendationModel;
import java.util.ArrayList;
import java.util.List;

// <summary>
// A utility class for conversions between entries, models, DTOs
// </summary>
public class ModelConverter {
    public static CryptoModel cryptoEntityToModel(CryptoEntity entity) {
        CryptoModel model = new CryptoModel();
        model.setTimestamp(entity.getTimestamp());
        model.setName(entity.getName());
        model.setPrice(entity.getPrice());

        return model;
    }

    public static CryptoDTO cryptoModelToDTO(CryptoModel modeel) {
        CryptoDTO model = new CryptoDTO();
        model.setTimestamp(modeel.getTimestamp());
        model.setName(modeel.getName());
        model.setPrice(modeel.getPrice());

        return model;
    }

    public static RecommendationDTO recommendationModelToDTO(RecommendationModel recommendationModel) {
        RecommendationDTO recommendationDTO = new RecommendationDTO();
        List<CryptoDTO> cryptoDTOS = new ArrayList<>();
        for (CryptoModel cryptoModel : recommendationModel.getCryptos()) {
            CryptoDTO cryptoDTO = cryptoModelToDTO(cryptoModel);
            cryptoDTOS.add(cryptoDTO);
        }
        recommendationDTO.setCryptos(cryptoDTOS);
        recommendationDTO.setCryptoCode(recommendationModel.getCryptoCode());
        recommendationDTO.setMin(cryptoModelToDTO(recommendationModel.getMin()));
        recommendationDTO.setMax(cryptoModelToDTO(recommendationModel.getMax()));
        recommendationDTO.setNewest(cryptoModelToDTO(recommendationModel.getNewest()));
        recommendationDTO.setOldest(cryptoModelToDTO(recommendationModel.getOldest()));
        recommendationDTO.setNormalizedValue(recommendationModel.getNormalizedValue());

        return recommendationDTO;
    }
}
