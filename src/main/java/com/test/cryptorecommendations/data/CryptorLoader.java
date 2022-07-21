package com.test.cryptorecommendations.data;

import com.test.cryptorecommendations.data.entity.CryptoEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface CryptorLoader {

    List<CryptoEntity> readCrypto(String cryptoCode);

//    Map<String, List<CryptoEntity>> getAllCryptos() throws IOException;

    Set listFilesInDir(String dir) throws IOException;

    Set<String> getSupportedCryptoCodes() throws IOException;
}
