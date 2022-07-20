package com.test.cryptorecommendations.data.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CryptoEntity {
    private Timestamp timestamp;
    private String name;
    private BigDecimal price;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
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
