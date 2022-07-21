package com.test.cryptorecommendations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication() //scanBasePackages = {"com.test.cryptorecommendations"}
public class CryptoRecommendationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoRecommendationsApplication.class, args);
    }

}
