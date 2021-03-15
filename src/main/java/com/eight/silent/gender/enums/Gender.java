package com.eight.silent.gender.enums;

import java.util.Arrays;
import java.util.List;

public enum Gender {
    MALE("male-names.txt"),
    FEMALE("female-names.txt");

    private final String fileName;

    Gender(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public static List<Gender> getGenders() {
        return Arrays.asList(Gender.values());
    }
}
