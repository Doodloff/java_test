package com.test.cryptorecommendations.data;

import com.opencsv.CSVReader;
import com.test.cryptorecommendations.controller.dto.RecommendationDTO;
import com.test.cryptorecommendations.data.entity.CryptoEntity;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVCryptoLoader implements CryptorLoader{
    @Override
    public List<CryptoEntity> readCrypto(String cryptoCode) {
        List<CryptoEntity> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(cryptoCode));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                CryptoEntity entity = new CryptoEntity();
                entity.setTimestamp(Timestamp.valueOf(values[0]));
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

    @Override
    public Map<String, List<CryptoEntity>> getAllCryptos() throws IOException {
        Set<String> cryptoFileNames = listFilesInDir("");

        Map<String, List<CryptoEntity>> cryptosMap = new HashMap<>();

        for (String name : cryptoFileNames) {
            List<CryptoEntity> entities = readCrypto(name);
            cryptosMap.put(name, entities);
        }

        return cryptosMap;
    }

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
}