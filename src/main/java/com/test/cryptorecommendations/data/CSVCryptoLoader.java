package com.test.cryptorecommendations.data;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.test.cryptorecommendations.data.entity.CryptoEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

// <summary>
// This class is responsible for reading cryptocurrency data from files
// For now it reads all files from specified directory
// Logic could be easily extended to read more files with gradation by months (for example - a subdirectory for each month or other way)
// </summary>
@Component
public class CSVCryptoLoader implements CryptoLoader {

    private static Logger logger = LogManager.getLogger(CSVCryptoLoader.class);

    @Value("${crypto.dir}")
    private String cryptoDataPath;


    // Reads CSV file with Crypto entries. Columns: timestamp, symbol, price.
    @Override
    @Cacheable(value = "crypto_data", key = "#cryptoCode")
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
            logger.error(DataErrorMessages.NO_CRYPTOCURRENCY_FILE);
            throw new RuntimeException(DataErrorMessages.NO_CRYPTOCURRENCY_FILE, e);
        } catch (IOException e) {
            logger.error(DataErrorMessages.CRYPTOCURRENCY_FILE_READ_ERROR);
            throw new RuntimeException(DataErrorMessages.CRYPTOCURRENCY_FILE_READ_ERROR, e);
        }

        return records;
    }

    // Listing files in specified directory with crypto currency data
    @Override
    public Set listFilesInDir() throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(cryptoDataPath))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

    // Get supported codes based on the file names in directory
    @Override
    @Cacheable("crypto_codes")
    public Set<String> getSupportedCryptoCodes() throws IOException {
        Set<String> supportedCodes = listFilesInDir();

        return supportedCodes.stream()
                .map(str -> str.split("_"))
                .filter(arr -> arr.length > 0)
                .map(arr -> arr[0])
                .collect(Collectors.toSet());
    }
}