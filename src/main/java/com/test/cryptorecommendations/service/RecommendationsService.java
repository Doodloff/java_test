package com.test.cryptorecommendations.service;

import com.test.cryptorecommendations.model.RecommendationDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class RecommendationsService implements IRecommendationsService {


    @Override
    public List<RecommendationDTO> readExcel() {
        try
        {
            String fileLocation = "";

            FileInputStream file = new FileInputStream(new File(fileLocation));
            Workbook workbook = new XSSFWorkbook(file);

            Sheet sheet = workbook.getSheetAt(0);

            Map<Integer, List<String>> data = new HashMap<>();
            int i = 0;
            for (Row row : sheet) {
                data.put(i, new ArrayList<String>());
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING: /*...*/ break;
                        case NUMERIC: /*...*/ break;
                        case BOOLEAN: /*...*/ break;
                        case FORMULA: /*...*/ break;
                        default: data.get(new Integer(i)).add(" ");
                    }
                }
                i++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<RecommendationDTO> findAll() {
        return null;
    }
}
