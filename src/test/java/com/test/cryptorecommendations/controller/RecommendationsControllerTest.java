package com.test.cryptorecommendations.controller;

import com.test.cryptorecommendations.service.RecommendationsService;
import com.test.cryptorecommendations.service.model.CryptoModel;
import com.test.cryptorecommendations.service.model.RecommendationModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RecommendationsControllerTest {

    @Autowired
    private MockMvc recommendationController;

    @MockBean // Add this
    private RecommendationsService service;

    @WithMockUser
    @Test
    void getAll() throws Exception {
        CryptoModel cryptoModel = new CryptoModel(
                LocalDateTime.ofInstant(Instant.ofEpochMilli((Double.valueOf(1.64101E+12).longValue())), TimeZone.getDefault().toZoneId()),
                "BTC",
                new BigDecimal(46813.00));

        List<CryptoModel> cryptoModels = new ArrayList<>();
        cryptoModels.add(cryptoModel);

        List<RecommendationModel> recommendationModels = new ArrayList<>();
        RecommendationModel recommendationModel = new RecommendationModel("BTC", cryptoModels);
        recommendationModels.add(recommendationModel);

        when(service.getAll()).thenReturn(recommendationModels);

        recommendationController.perform(MockMvcRequestBuilders.get("/recommendations"))
                .andDo(print()).andExpect(status().isOk())
                // Matching with string is not the best way, better operate with proper types.
                .andExpect(content().json("[{\"cryptoCode\":\"BTC\",\"max\":{\"timestamp\":\"2022-01-01T08:06:40\",\"name\":\"BTC\",\"price\":46813},\"min\":{\"timestamp\":\"2022-01-01T08:06:40\",\"name\":\"BTC\",\"price\":46813},\"newest\":{\"timestamp\":\"2022-01-01T08:06:40\",\"name\":\"BTC\",\"price\":46813},\"oldest\":{\"timestamp\":\"2022-01-01T08:06:40\",\"name\":\"BTC\",\"price\":46813},\"normalizedValue\":0,\"cryptos\":[{\"timestamp\":\"2022-01-01T08:06:40\",\"name\":\"BTC\",\"price\":46813}]}]"));
    }

    @Test
    void getSortedDesc() {

    }

    @Test
    @WithMockUser
    void getMin_ok() throws Exception {

        CryptoModel cryptoModel = new CryptoModel(
                LocalDateTime.ofInstant(Instant.ofEpochMilli((Double.valueOf(1.64101E+12).longValue())), TimeZone.getDefault().toZoneId()),
                "BTC",
                new BigDecimal(46813.00));

        when(service.recommendMinForCrypto("BTC")).thenReturn(cryptoModel);

        recommendationController.perform(MockMvcRequestBuilders.get("/recommendations/BTC/min"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"timestamp\":\"2022-01-01T08:06:40\",\"name\":\"BTC\",\"price\":46813}"));
    }

    @Test
    @WithMockUser
    void getMin_nok_NotFound() throws Exception {

        when(service.recommendMinForCrypto("NOT_FOUND_LALALA")).thenThrow(IllegalArgumentException.class);

        recommendationController.perform(MockMvcRequestBuilders.get("/recommendations/NOT_FOUND_LALALA/min"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void getMax() {
    }

    @Test
    void getNewest() {
    }

    @Test
    void getOldest() {
    }

    @Test
    void getHighestNormilizedByDay() {
    }
}