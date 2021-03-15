package com.eight.silent.gender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenderServiceImplTest {

    @Mock
    private NameLoader nameLoader;

    private GenderService genderService;

    private final String fileNameMale = "male-names.txt";
    private final String fileNameFemale = "female-names.txt";

    List<String> maleNames = Arrays.asList("Jacek", "Jakub", "Jan");
    List<String> femaleNames = Arrays.asList("Ewa", "Kasia", "Maria");

    @BeforeEach
    void setUp() {
        genderService = new GenderServiceImpl(nameLoader);
    }

    @Test
    public void getAllNames_CorrectListSize_ValidData() {
        int expectedSize = maleNames.size() + femaleNames.size();

        when(nameLoader.loadNamesFromFile(fileNameMale)).thenReturn(maleNames);
        when(nameLoader.loadNamesFromFile(fileNameFemale)).thenReturn(femaleNames);
        List<String> allNames = genderService.getAllNames();

        assertEquals(expectedSize, allNames.size());
    }

    @Test
    public void getAllNames_EmptyList_EmptyData() {
        List<String> names = new ArrayList<>();

        when(nameLoader.loadNamesFromFile(fileNameMale)).thenReturn(names);
        when(nameLoader.loadNamesFromFile(fileNameFemale)).thenReturn(names);
        List<String> allNames = genderService.getAllNames();

        assertEquals(names.isEmpty(), allNames.isEmpty());
    }

    @Test
    public void getAllNames_listElements() {
        when(nameLoader.loadNamesFromFile(fileNameMale)).thenReturn(maleNames);
        when(nameLoader.loadNamesFromFile(fileNameFemale)).thenReturn(femaleNames);
        List<String> allNames = genderService.getAllNames();

        assertEquals(maleNames.get(0), allNames.get(0));
        assertEquals(maleNames.get(1), allNames.get(1));
        assertEquals(maleNames.get(2), allNames.get(2));
        assertEquals(femaleNames.get(0), allNames.get(3));
        assertEquals(femaleNames.get(1), allNames.get(4));
        assertEquals(femaleNames.get(2), allNames.get(5));
    }

    @Test
    public void getGender_Male_MaleName() {
        String name = "Jan Ewa Rokita";
        String firstSubName = "Jan";
        String genderExpected = "MALE";

        when(nameLoader.hasOccurred(fileNameMale, firstSubName)).thenReturn(true);
        String genderActual = genderService.getGender(name, false);

        assertEquals(genderExpected, genderActual);
    }

    @Test
    public void getGender_Female_FemaleName() {
        String name = "Maria Jan Rokita";
        String firstSubName = "Maria";
        String genderExpected = "FEMALE";

        when(nameLoader.hasOccurred(fileNameMale, firstSubName)).thenReturn(false);
        when(nameLoader.hasOccurred(fileNameFemale, firstSubName)).thenReturn(true);
        String genderActual = genderService.getGender(name, false);

        assertEquals(genderExpected, genderActual);
    }

    @Test
    public void getGender_Inconclusive_UnknownName() {
        String name = "Robin Jan Ewa Rokita";
        String firstSubName = "Robin";
        String genderExpected = "INCONCLUSIVE";

        when(nameLoader.hasOccurred(fileNameMale, firstSubName)).thenReturn(false);
        when(nameLoader.hasOccurred(fileNameFemale, firstSubName)).thenReturn(false);
        String genderActual = genderService.getGender(name, false);

        assertEquals(genderExpected, genderActual);
    }

    @Test
    public void getGender_Male_NameWithWhiteSpaceAtTheBeginning() {
        String name = " Jacek Jan Ewa Rokita";
        String firstSubName = "Jacek";
        String genderExpected = "MALE";

        when(nameLoader.hasOccurred(fileNameMale, firstSubName)).thenReturn(true);
        String genderActual = genderService.getGender(name, false);

        assertEquals(genderExpected, genderActual);
    }

    @Test
    public void getGenderFull_Male_MaleName() {
        String name = "Jan Maria Jacek Rokita";
        List<String> subNames = Arrays.asList("Jan", "Maria", "Jacek", "Rokita");
        String genderExpected = "MALE";

        when(nameLoader.getOccurrences(fileNameMale, subNames)).thenReturn(2);
        when(nameLoader.getOccurrences(fileNameFemale, subNames)).thenReturn(1);
        String genderActual = genderService.getGender(name, true);

        assertEquals(genderExpected, genderActual);
    }

    @Test
    public void getGenderFull_Female_FemaleName() {
        String name = "Jan Maria Ewa Rokita";
        List<String> subNames = Arrays.asList("Jan", "Maria", "Ewa", "Rokita");
        String genderExpected = "FEMALE";

        when(nameLoader.getOccurrences(fileNameMale, subNames)).thenReturn(1);
        when(nameLoader.getOccurrences(fileNameFemale, subNames)).thenReturn(2);
        String genderActual = genderService.getGender(name, true);

        assertEquals(genderExpected, genderActual);
    }

    @Test
    public void getGenderFull_Female_FemaleNameWithWhiteSpaceAtTheBegining() {
        String name = " Jan Maria Ewa Rokita";
        List<String> subNames = Arrays.asList("Jan", "Maria", "Ewa", "Rokita");
        String genderExpected = "FEMALE";

        when(nameLoader.getOccurrences(fileNameMale, subNames)).thenReturn(1);
        when(nameLoader.getOccurrences(fileNameFemale, subNames)).thenReturn(2);
        String genderActual = genderService.getGender(name, true);

        assertEquals(genderExpected, genderActual);
    }

    @Test
    public void getGenderFull_Inconclusive_InconclusiveName() {
        String name = "Jan Maria Rokita";
        List<String> subNames = Arrays.asList("Jan", "Maria", "Rokita");
        String genderExpected = "INCONCLUSIVE";

        when(nameLoader.getOccurrences(fileNameMale, subNames)).thenReturn(1);
        when(nameLoader.getOccurrences(fileNameFemale, subNames)).thenReturn(1);
        String genderActual = genderService.getGender(name, true);

        assertEquals(genderExpected, genderActual);
    }

    @Test
    public void getGenderFull_Inconclusive_EmptyName() {
        String name = "";
        List<String> subNames = Collections.singletonList("");
        String genderExpected = "INCONCLUSIVE";

        when(nameLoader.getOccurrences(fileNameMale, subNames)).thenReturn(0);
        when(nameLoader.getOccurrences(fileNameFemale, subNames)).thenReturn(0);
        String genderActual = genderService.getGender(name, true);

        assertEquals(genderExpected, genderActual);
    }

    @Test
    public void getGenderFull_Male_DoubleName() {
        String name = "Jan Jan Maria Rokita";
        List<String> subNames = Arrays.asList("Jan", "Jan", "Maria", "Rokita");
        String genderExpected = "MALE";

        when(nameLoader.getOccurrences(fileNameMale, subNames)).thenReturn(2);
        when(nameLoader.getOccurrences(fileNameFemale, subNames)).thenReturn(1);
        String genderActual = genderService.getGender(name, true);

        assertEquals(genderExpected, genderActual);
    }
}
