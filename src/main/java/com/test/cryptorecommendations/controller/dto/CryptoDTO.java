package com.test.cryptorecommendations.controller.dto;

import com.test.cryptorecommendations.service.model.CryptoModel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class CryptoDTO {
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
