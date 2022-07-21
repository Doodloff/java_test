package com.test.cryptorecommendations.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CryptoDTO {
    private LocalDateTime timestamp;
    private String name;
    private BigDecimal price;

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
