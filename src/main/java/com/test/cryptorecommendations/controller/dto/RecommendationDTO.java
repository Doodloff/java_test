package com.test.cryptorecommendations.controller.dto;

import com.test.cryptorecommendations.service.model.CryptoModel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class RecommendationDTO {
    String cryptoCode;
    private List<CryptoDTO> cryptos;
    BigDecimal max;
    BigDecimal min;
    BigDecimal normalizedValue;

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

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getNormalizedValue() {
        return normalizedValue;
    }

    public void setNormalizedValue(BigDecimal normalizedValue) {
        this.normalizedValue = normalizedValue;
    }
}
