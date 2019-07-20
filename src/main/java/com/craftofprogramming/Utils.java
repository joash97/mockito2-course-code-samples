package com.craftofprogramming;

import java.io.*;
import java.nio.file.*;
import java.time.Year;
import java.util.*;

public class Utils {
    private static final int INVALID_CSV_FILE_TOKEN_ERROR_CODE = 1;
    public static final int MISSING_CSV_FILE = 2;

    public List<Book> parseLibraryFrom(Path path) {
        List<Book> library = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                library.add(getBook(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return library;
    }

    public Book getBook(String line) {
        final String[] tokens = line.split(",");
        return new Book(getToken(tokens, 0), Integer.parseInt(getToken(tokens, 1)), Topic.valueOf(getToken(tokens, 2)),
                Year.parse(getToken(tokens, 3)), getToken(tokens, 4));
    }

    public String getToken(String[] tokens, int idx) {
        final String token = tokens[idx];
        if (token == null) {
            throw new IllegalArgumentException(String.format("%d | Found invalid token at index i:%d", INVALID_CSV_FILE_TOKEN_ERROR_CODE, idx));
        }
        return token.trim();
    }
}