package com.example.parser.service.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class Writer {
    public void saveRecordToCSV(Map<String, List<Integer>> map, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath, true);
             CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT
                     .withHeader("url", "tesla", "musk", "gigafactory", "elonMusk", "total"))) {

            for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
                printer.print(entry.getKey());
                printer.print(String.valueOf(entry.getValue().get(0)));
                printer.print(String.valueOf(entry.getValue().get(1)));
                printer.print(String.valueOf(entry.getValue().get(2)));
                printer.print(String.valueOf(entry.getValue().get(3)));
                printer.print(String.valueOf(entry.getValue().get(4)));
                printer.printRecord();
            }
            printer.flush();

        } catch (IOException e) {
            System.err.println("Can't save " + e.getMessage());
        }
    }
}
