package com.test.cryptorecommendations.service.exception;

import com.test.cryptorecommendations.service.ServiceErrorMessages;

public class CryptoNotFoundException extends CryptoException{

    private static final String CURRENCY_NOT_FOUND = "Cryptocurrency not found";

    public CryptoNotFoundException() {
        super(CryptoNotFoundException.CURRENCY_NOT_FOUND);
    }

    public CryptoNotFoundException(String message) {
        super(message);
    }

    public CryptoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
