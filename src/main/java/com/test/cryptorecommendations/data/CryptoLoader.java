package com.test.cryptorecommendations.data;

import com.test.cryptorecommendations.data.entity.CryptoEntity;
import java.io.IOException;
import java.util.List;
import java.util.Set;


public interface CryptoLoader {
    // load crypto data
    List<CryptoEntity> readCrypto(String cryptoCode);
    // Listing files in specified directory with crypto currency data
    Set listFilesInDir() throws IOException;
    // Get supported codes based on the file names in directory
    Set<String> getSupportedCryptoCodes() throws IOException;
}
