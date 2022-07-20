package com.test.cryptorecommendations.service.model;

import com.test.cryptorecommendations.data.entity.CryptoEntity;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class RecommendationModel implements Comparator<RecommendationModel> {
    String cryptoCode;
    List<CryptoModel> cryptos;
    BigDecimal max;
    BigDecimal min;
    BigDecimal normalizedValue;

    public RecommendationModel(List<CryptoModel> cryptos) {
        this.cryptos = cryptos;
    }

    public RecommendationModel(String cryptoCode, List<CryptoModel> cryptos){
        this.cryptoCode = cryptoCode;
        this.cryptos = cryptos.stream()
                .collect(Collectors.toList());
        this.max = calculateMax();
        this.min = calculateMin();
        this.normalizedValue = calculateNormalizedValue();
    }

    private BigDecimal calculateMax() {
        CryptoModel max = cryptos.stream()
                .max(Comparator.comparing(CryptoModel::getPrice))
                .orElseThrow(NoSuchElementException::new);

        return max.getPrice();
    }

    private BigDecimal calculateMin() {
        CryptoModel min = cryptos.stream()
                .min(Comparator.comparing(CryptoModel::getPrice))
                .orElseThrow(NoSuchElementException::new);

        return min.getPrice();
    }

    private BigDecimal calculateNormalizedValue() {
        return calculateMax().subtract(calculateMin()).divide(calculateMin());
    }

    public String getCryptoCode() {
        return cryptoCode;
    }

    public void setCryptoCode(String cryptoCode) {
        this.cryptoCode = cryptoCode;
    }

    public List<CryptoModel> getCryptos() {
        return cryptos;
    }

    public void setCryptos(List<CryptoModel> cryptos) {
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

    @Override
    public int compare(RecommendationModel first, RecommendationModel second) {
        return first.getNormalizedValue().compareTo(second.getNormalizedValue());
    }
}
