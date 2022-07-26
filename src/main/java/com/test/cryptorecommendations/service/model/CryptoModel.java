package com.test.cryptorecommendations.service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CryptoModel{
    private LocalDateTime timestamp;
    private String name;
    private BigDecimal price;

    public CryptoModel() {}

    public CryptoModel(LocalDateTime timestamp, String name, BigDecimal price) {
        this.timestamp = timestamp;
        this.name = name;
        this.price = price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
