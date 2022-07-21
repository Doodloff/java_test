package com.test.cryptorecommendations.data;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.test.cryptorecommendations.controller.dto.RecommendationDTO;
import com.test.cryptorecommendations.data.entity.CryptoEntity;
import javassist.ClassPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CSVCryptoLoader implements CryptorLoader{
    @Value(value = "src/main/resources/cryptos/")
    private String cryptoDataPath;


    @Override
//    @Cacheable(value = "crypto_data", key = "#cryptoCode")
    public List<CryptoEntity> readCrypto(String cryptoCode) {
        List<CryptoEntity> records = new ArrayList<>();
        String filePath = cryptoDataPath + cryptoCode + "_values.csv";

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(filePath))
                .withSkipLines(1)
                .build();) {
            String[] values;
            csvReader.getSkipLines();
            while ((values = csvReader.readNext()) != null) {
                CryptoEntity entity = new CryptoEntity();
                entity.setTimestamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(values[0])),
                        TimeZone.getDefault().toZoneId()));
                entity.setName(values[1]);
                entity.setPrice(new BigDecimal(values[2]));
                records.add(entity);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }

//    @Override
//    public Map<String, List<CryptoEntity>> getAllCryptos() throws IOException {
//        Set<String> cryptoFileNames = listFilesInDir("");
//
//        Map<String, List<CryptoEntity>> cryptosMap = new HashMap<>();
//
//        for (String name : cryptoFileNames) {
//            List<CryptoEntity> entities = readCrypto(name);
//            cryptosMap.put(name, entities);
//        }
//
//        return cryptosMap;
//    }

    @Override
    public Set listFilesInDir(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

    @Override
    @Cacheable("crypto_codes")
    public Set<String> getSupportedCryptoCodes() throws IOException {
        Set<String> supportedCodes = listFilesInDir(cryptoDataPath);

        return supportedCodes.stream()
                .map(str -> str.split("_"))
                .filter(arr -> arr.length > 0)
                .map(arr -> arr[0])
                .collect(Collectors.toSet());
    }
}