package com.test.cryptorecommendations.data.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CryptoEntity {
    private LocalDateTime timestamp;
    private String name;
    private BigDecimal price;

    public CryptoEntity() {}

    public CryptoEntity(LocalDateTime timestamp, String name, BigDecimal price) {
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
