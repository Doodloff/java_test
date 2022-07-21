package com.test.cryptorecommendations.service.model;

import com.test.cryptorecommendations.data.entity.CryptoEntity;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class RecommendationModel implements Comparator<RecommendationModel> {
    private String cryptoCode;
    private List<CryptoModel> cryptos;
    private CryptoModel max;
    private CryptoModel min;
    private CryptoModel newest;
    private CryptoModel oldest;
    private BigDecimal normalizedValue;

    public RecommendationModel(String cryptoCode, List<CryptoModel> cryptos){
        this.cryptoCode = cryptoCode;
        this.cryptos = cryptos.stream()
                .collect(Collectors.toList());
        calculateAnalytics();
    }

    private void calculateAnalytics() {
        this.max = calculateMax();
        this.min = calculateMin();
        this.newest = calculateNewest();
        this.oldest = calculateOldest();
        this.normalizedValue = calculateNormalizedValue();
    }

    private CryptoModel calculateMax() {
        return cryptos.stream()
                .max(Comparator.comparing(CryptoModel::getPrice))
                .orElseThrow(NoSuchElementException::new);
    }

    private CryptoModel calculateMin() {
        return cryptos.stream()
                .min(Comparator.comparing(CryptoModel::getPrice))
                .orElseThrow(NoSuchElementException::new);
    }

    private BigDecimal calculateNormalizedValue() {
        Assert.notNull(getMax(), "max value should not be null");
        Assert.notNull(getMin(), "min value should not be null");

        return getMax().getPrice().subtract(getMin().getPrice()).divide(getMin().getPrice(), 3);
    }

    private CryptoModel calculateNewest() {
        return cryptos.stream()
                .max(Comparator.comparing(CryptoModel::getTimestamp))
                .orElseThrow(NoSuchElementException::new);
    }

    private CryptoModel calculateOldest() {
        return cryptos.stream()
                .min(Comparator.comparing(CryptoModel::getTimestamp))
                .orElseThrow(NoSuchElementException::new);
    }

    public CryptoModel getMaxByDay(LocalDate date) {
        return cryptos.stream()
                .filter(cryptoModel -> cryptoModel.getTimestamp() == date.atStartOfDay())
                .max(Comparator.comparing(CryptoModel::getPrice))
                .orElseThrow(NoSuchElementException::new);
    }

    public CryptoModel getMinByDay(LocalDate date) {
        return cryptos.stream()
                .filter(cryptoModel -> cryptoModel.getTimestamp() == date.atStartOfDay())
                .min(Comparator.comparing(CryptoModel::getPrice))
                .orElseThrow(NoSuchElementException::new);
    }

    public BigDecimal getNormalizedValueBuyDay(LocalDate date) {
        Assert.notNull(getMax(), "max value should not be null");
        Assert.notNull(getMin(), "min value should not be null");

        return getMaxByDay(date).getPrice().subtract(getMinByDay(date).getPrice()).divide(getMinByDay(date).getPrice(), 3);
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

    public CryptoModel getMax() {
        return max;
    }

    public void setMax(CryptoModel max) {
        this.max = max;
    }

    public CryptoModel getMin() {
        return min;
    }

    public void setMin(CryptoModel min) {
        this.min = min;
    }

    public BigDecimal getNormalizedValue() {
        return normalizedValue;
    }

    public CryptoModel getNewest() {
        return newest;
    }

    public void setNewest(CryptoModel newest) {
        this.newest = newest;
    }

    public CryptoModel getOldest() {
        return oldest;
    }

    public void setOldest(CryptoModel oldest) {
        this.oldest = oldest;
    }

    public void setNormalizedValue(BigDecimal normalizedValue) {
        this.normalizedValue = normalizedValue;
    }

    @Override
    public int compare(RecommendationModel first, RecommendationModel second) {
        return first.getNormalizedValue().compareTo(second.getNormalizedValue());
    }
}
