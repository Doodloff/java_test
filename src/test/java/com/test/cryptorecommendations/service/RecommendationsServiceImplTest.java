package com.test.cryptorecommendations.service;

import com.test.cryptorecommendations.data.CryptoLoader;
import com.test.cryptorecommendations.data.entity.CryptoEntity;
import com.test.cryptorecommendations.service.exception.CryptoNotFoundException;
import com.test.cryptorecommendations.service.model.RecommendationModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecommendationsServiceImplTest {

    @InjectMocks
    private RecommendationsServiceImpl service;
    @Mock
    private CryptoLoader cryptoLoader;

    @Test
    void getRecommendation_ok() throws IOException {
        Set<String> supportedCryptoCodes = new HashSet<>(Arrays.asList("BTC"));

        CryptoEntity cryptoEntity1 = new CryptoEntity(
                LocalDateTime.ofInstant(Instant.ofEpochMilli((Double.valueOf(1.64101E+12).longValue())), TimeZone.getDefault().toZoneId()),
                "BTC",
                new BigDecimal(46813.00));
        CryptoEntity cryptoEntity2 = new CryptoEntity(
                LocalDateTime.ofInstant(Instant.ofEpochMilli((Double.valueOf(1.64101E+12).longValue())), TimeZone.getDefault().toZoneId()),
                "BTC",
                new BigDecimal(46813.21));
        CryptoEntity cryptoEntity3 = new CryptoEntity(
                LocalDateTime.ofInstant(Instant.ofEpochMilli((Double.valueOf(1.64101E+12).longValue())), TimeZone.getDefault().toZoneId()),
                "BTC",
                new BigDecimal(46813.41));

        List<CryptoEntity> cryptoEntities = new ArrayList<>();
        cryptoEntities.add(cryptoEntity1);
        cryptoEntities.add(cryptoEntity2);
        cryptoEntities.add(cryptoEntity3);

        when(cryptoLoader.readCrypto("BTC")).thenReturn(cryptoEntities);
        when(cryptoLoader.getSupportedCryptoCodes()).thenReturn(supportedCryptoCodes);

        RecommendationModel responseModel = service.getRecommendation("BTC");
        assertThat(responseModel.getCryptoCode()).isEqualTo("BTC");
        assertThat(responseModel.getCryptos().size()).isEqualTo(3);
        assertThat(responseModel.getMin().getPrice()).isEqualTo(new BigDecimal(46813.00));
        assertThat(responseModel.getMax().getPrice()).isEqualTo(new BigDecimal(46813.41));
    }

    @Test
    void getRecommendation_nok_IllegalArgument() throws IOException {
        Set<String> supportedCryptoCodes = new HashSet<>(Arrays.asList("BTC", "ABC"));
        when(cryptoLoader.getSupportedCryptoCodes()).thenReturn(supportedCryptoCodes);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> service.getRecommendation("DEF"));
    }

    @Test
    void getRecommendation_nok_CryptoNotFound() throws IOException {
        Set<String> supportedCryptoCodes = new HashSet<>(Arrays.asList("BTC", "ABC"));

        List<CryptoEntity> cryptoEntities = new ArrayList<>();

        when(cryptoLoader.readCrypto("ABC")).thenReturn(cryptoEntities);
        when(cryptoLoader.getSupportedCryptoCodes()).thenReturn(supportedCryptoCodes);

        assertThatExceptionOfType(CryptoNotFoundException.class)
                .isThrownBy(() -> service.getRecommendation("ABC"));
    }

    @Test
    void getAll() {
        //todo
    }

    @Test
    void getSupportedCryptoCodes() {
        //todo
    }

    @Test
    void getSortedDesc() {
        //todo
    }

    @Test
    void getHighestNormalizedRange() {
        //todo
    }

    @Test
    void getWithHighestNormalizedRangeByDay() {
        //todo
    }

    @Test
    void recommendMinForCrypto() {
        //todo
    }

    @Test
    void recommendMaxForCrypto() {
        //todo
    }

    @Test
    void recommendNewestForCrypto() {
        //todo
    }

    @Test
    void recommendOldestForCrypto() {
        //todo
    }
}