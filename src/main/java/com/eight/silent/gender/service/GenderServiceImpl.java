package com.eight.silent.gender.service;

import com.eight.silent.gender.enums.Gender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenderServiceImpl implements GenderService{

    private final List<Gender> genders;
    private final NameLoader nameLoader;

    public GenderServiceImpl(NameLoader nameLoader) {
        genders = Gender.getGenders();
        this.nameLoader = nameLoader;
    }

    @Override
    public List<String> getAllNames() {
        return genders.stream()
                .map(gender -> nameLoader.loadNamesFromFile(gender.getFileName()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public String getGender(String name, Boolean full) {
        if (full != null && full) {
            return getGenderFromArray(name);
        } else {
            return checkFirstSubName(name);
        }
    }

    private String getGenderFromArray(String name) {
        List<String> subNames = getListOfSubNames(name);
        return getGenderFullName(subNames);
    }

    private String checkFirstSubName(String name) {
        String first = getFirstSubName(name);
        return getGender(first);
    }

    private List<String> getListOfSubNames(String name) {
        name = removeWhiteSpaceAtStart(name);
        return Arrays.asList(name.split(" "));
    }

    private String getFirstSubName(String name) {
        name = removeWhiteSpaceAtStart(name);
        String[] s = name.split(" ", 2);
        return s.length != 0 ? s[0] : "";
    }

    private String removeWhiteSpaceAtStart(String name) {
        return name.replaceFirst("^\\s*", "");
    }

    private String getGender(String name) {
        return genders.stream()
                .filter(gender -> nameLoader.hasOccurred(gender.getFileName(), name))
                .findFirst()
                .map(Gender::name)
                .orElse("INCONCLUSIVE");
    }

    private String getGenderFullName(List<String> names) {
        Map<String, Integer> genderMap = genders.stream()
                .collect(Collectors.toMap(Enum::name,
                        gender -> nameLoader.getOccurrences(gender.getFileName(), names)));
        return getGenderWithMostOccurrences(genderMap);
    }

    private String getGenderWithMostOccurrences(Map<String, Integer> genderMap) {
        int score = 0;
        String gender = "INCONCLUSIVE";

        for (Map.Entry<String, Integer> entry : genderMap.entrySet()) {
            if (entry.getValue() > score) {
                gender = entry.getKey();
                score = entry.getValue();
            } else if (entry.getValue() == score) {
                gender = "INCONCLUSIVE";
            }
        }
        return gender;
    }
}

