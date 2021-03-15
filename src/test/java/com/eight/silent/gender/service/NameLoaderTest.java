package com.eight.silent.gender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NameLoaderTest {

    private NameLoader nameLoader;

    private final String fileNameTest = "test-names.txt";

    @BeforeEach
    void setUp() {
        nameLoader = new NameLoader();
    }

    @Test
    public void loadNamesFromFile_5_TestFileName() {
        int numberOfNames = 7;

        List<String> names = nameLoader.loadNamesFromFile(fileNameTest);

        assertEquals(numberOfNames, names.size());
    }

    @Test
    public void hasOccurred_True_NameThatOccurs() {
        String testNameOne = "Jacek";
        String testNameTwo = "Krzysztof";
        String testNameThree = "Marian";

        boolean hasOccurredOne = nameLoader.hasOccurred(fileNameTest, testNameOne);
        boolean hasOccurredTwo = nameLoader.hasOccurred(fileNameTest, testNameTwo);
        boolean hasOccurredThree = nameLoader.hasOccurred(fileNameTest, testNameThree);

        assertTrue(hasOccurredOne);
        assertTrue(hasOccurredTwo);
        assertTrue(hasOccurredThree);
    }

    @Test
    public void hasOccurred_True_NameThatOccursLowerCase() {
        String testNameOne = "jacek";
        String testNameTwo = "krzysztof";
        String testNameThree = "marian";

        boolean hasOccurredOne = nameLoader.hasOccurred(fileNameTest, testNameOne);
        boolean hasOccurredTwo = nameLoader.hasOccurred(fileNameTest, testNameTwo);
        boolean hasOccurredThree = nameLoader.hasOccurred(fileNameTest, testNameThree);

        assertTrue(hasOccurredOne);
        assertTrue(hasOccurredTwo);
        assertTrue(hasOccurredThree);
    }

    @Test
    public void hasOccurred_True_NameThatOccursLowerCaseToken() {
        String testNameOne = "Piotr";
        String testNameTwo = "Marek";

        boolean hasOccurredOne = nameLoader.hasOccurred(fileNameTest, testNameOne);
        boolean hasOccurredTwo = nameLoader.hasOccurred(fileNameTest, testNameTwo);

        assertTrue(hasOccurredOne);
        assertTrue(hasOccurredTwo);
    }

    @Test
    public void hasOccurred_False_NameThatDoesntOccur() {
        String testName = "Marek";

        boolean hasOccurred = nameLoader.hasOccurred(fileNameTest, testName);

        assertFalse(hasOccurred);
    }

    @Test
    public void hasOccurred_False_InvalidName() {
        String invalidName = "MarFBPA)&guvbop922901ek";
        String emptyName = "";
        String numberName = "3841y08341";

        boolean hasOccurredInvalid = nameLoader.hasOccurred(fileNameTest, invalidName);
        boolean hasOccurredEmpty = nameLoader.hasOccurred(fileNameTest, emptyName);
        boolean hasOccurredNumber = nameLoader.hasOccurred(fileNameTest, numberName);

        assertFalse(hasOccurredInvalid);
        assertFalse(hasOccurredEmpty);
        assertFalse(hasOccurredNumber);
    }

    @Test
    public void getOccurrences_0_NamesThatDontOccur() {
        List<String> names = Arrays.asList("Jan", "Maria", "Rokita");

        int occurrences = nameLoader.getOccurrences(fileNameTest, names);

        assertEquals(0, occurrences);
    }

    @Test
    public void getOccurrences_3_NamesThatAllOccur() {
        List<String> names = Arrays.asList("Jacek", "Jakub", "Krzysztof");

        int occurrences = nameLoader.getOccurrences(fileNameTest, names);

        assertEquals(names.size(), occurrences);
    }

    @Test
    public void getOccurrences_3_NamesThatAllOccurLowerCase() {
        List<String> names = Arrays.asList("Jacek", "jakub", "Krzysztof", "Piotr");

        int occurrences = nameLoader.getOccurrences(fileNameTest, names);

        assertEquals(names.size(), occurrences);
    }

    @Test
    public void getOccurrences_0_EmptyListOfNames() {
        List<String> names = new ArrayList<>();

        int occurrences = nameLoader.getOccurrences(fileNameTest, names);

        assertEquals(names.size(), occurrences);
    }

}
