package com.test.cryptorecommendations.controller.dto;

import com.test.cryptorecommendations.service.model.CryptoModel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class RecommendationDTO {
    private String cryptoCode;
    private List<CryptoDTO> cryptos;
    private CryptoDTO max;
    private CryptoDTO min;
    private CryptoDTO newest;
    private CryptoDTO oldest;
    private BigDecimal normalizedValue;

    public String getCryptoCode() {
        return cryptoCode;
    }

    public void setCryptoCode(String cryptoCode) {
        this.cryptoCode = cryptoCode;
    }

    public List<CryptoDTO> getCryptos() {
        return cryptos;
    }

    public void setCryptos(List<CryptoDTO> cryptos) {
        this.cryptos = cryptos;
    }

    public CryptoDTO getMax() {
        return max;
    }

    public void setMax(CryptoDTO max) {
        this.max = max;
    }

    public CryptoDTO getMin() {
        return min;
    }

    public void setMin(CryptoDTO min) {
        this.min = min;
    }

    public BigDecimal getNormalizedValue() {
        return normalizedValue;
    }

    public CryptoDTO getNewest() {
        return newest;
    }

    public void setNewest(CryptoDTO newest) {
        this.newest = newest;
    }

    public CryptoDTO getOldest() {
        return oldest;
    }

    public void setOldest(CryptoDTO oldest) {
        this.oldest = oldest;
    }

    public void setNormalizedValue(BigDecimal normalizedValue) {
        this.normalizedValue = normalizedValue;
    }
}
