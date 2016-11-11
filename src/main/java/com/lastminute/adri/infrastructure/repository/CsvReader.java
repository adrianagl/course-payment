package com.lastminute.adri.infrastructure.repository;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class CsvReader {
    private final CSVReader reader;

    public CsvReader(String fileName) {
        this.reader = createReader(fileName);
    }

    public Optional<String[]> getNextLine() {
        try {
            return Optional.ofNullable(reader.readNext());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private CSVReader createReader(String fileName) {
        String file = getClass().getClassLoader().getResource(fileName).getFile();

        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
            return new CSVReader(fileReader);
        } catch (FileNotFoundException e) {
            throw new RepositoryException(String.format("CSV file not found: %s", file), e);
        }
    }

}
