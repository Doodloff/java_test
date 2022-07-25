package com.test.cryptorecommendations.data;

import com.test.cryptorecommendations.data.entity.CryptoEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

// A hybrid Unit + Integration test
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CSVCryptoLoaderTest {

    @Autowired
    private CryptoLoader cryptoLoader;

    @Test
    void readCrypto_ok() {
        List<CryptoEntity> cryptoEntities = cryptoLoader.readCrypto("BTC");
        assertThat(cryptoEntities.size()).isEqualTo(3);
        for (CryptoEntity e : cryptoEntities) {
            assertThat(e.getTimestamp()).isNotNull();
            assertThat(e.getName()).isNotNull();
            assertThat(e.getPrice()).isNotNull();
        }
    }

    @Test
    void readCrypto_nok_FileNotFoundException() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> cryptoLoader.readCrypto("Lalala")).withMessage(DataErrorMessages.NO_CRYPTOCURRENCY_FILE);
    }

    @Test
    void listFilesInDir_ok() throws IOException {
        Set<String> files = cryptoLoader.listFilesInDir();
        assertThat(files.size()).isGreaterThan(0);
    }

    @Test
    void listFilesInDir_nok() throws IOException {
        //todo
    }

    @Test
    void getSupportedCryptoCodes_ok() throws IOException {
        Set<String> supportedCodes = cryptoLoader.getSupportedCryptoCodes();
        assertThat(supportedCodes.size()).isGreaterThan(0);
    }

    @Test
    void getSupportedCryptoCodes_nok() throws IOException {
        //todo
    }
}