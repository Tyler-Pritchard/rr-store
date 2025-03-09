package com.rr.store.util;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A utility service for reading files as strings.
 */
@Service
public class FileReaderService {

    /**
     * Reads the content of a file and returns it as a string.
     *
     * @param filePath the path of the file to read
     * @return the content of the file as a string
     * @throws IOException if an error occurs while reading the file
     */
    public String readJsonFile(String filePath) throws IOException {
        return Files.readString(Path.of(filePath));
    }
}
