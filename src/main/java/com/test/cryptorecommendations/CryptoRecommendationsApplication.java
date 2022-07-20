package com.test.cryptorecommendations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.test.cryptorecommendations.service"})
public class CryptoRecommendationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoRecommendationsApplication.class, args);
    }

}
