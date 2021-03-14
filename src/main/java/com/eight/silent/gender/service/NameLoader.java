package com.eight.silent.gender.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class NameLoader {

    public List<String> loadNamesFromFile(String fileName) {
        List<String> names = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream("/" + fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = br.readLine()) != null) {
                names.addAll(Arrays.asList(line.split(",")));
            }
            return names;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return List.of();
    }

    public boolean hasOccurred(String fileName, String name) {
        try (InputStream inputStream = getClass().getResourceAsStream("/" + fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = br.readLine()) != null) {
                List<String> tokens = getTokes(line);
                if (tokens.contains(name)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getOccurrences(String fileName, List<String> names) {
        int counter = 0;
        try (InputStream inputStream = getClass().getResourceAsStream("/" + fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = br.readLine()) != null) {
                List<String> tokens = getTokes(line);
                for (String name : names) {
                    if (tokens.contains(name)) {
                        counter++;

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }

    private List<String> getTokes(String line) {
        return Arrays.asList(line.split(","));
    }
}
