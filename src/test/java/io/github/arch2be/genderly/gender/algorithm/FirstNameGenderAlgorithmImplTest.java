package io.github.arch2be.genderly.gender.algorithm;

import io.github.arch2be.genderly.gender.GenderRepository;
import io.github.arch2be.genderly.gender.exceptions.GenderTokenNotFound;
import io.github.arch2be.genderly.gender.mocked_db_rules.FirstNameAlgorithmMockedDbRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FirstNameGenderAlgorithmImplTest implements FirstNameAlgorithmMockedDbRules {

    private GenderAlgorithm genderAlgorithm;

    @BeforeEach
    void setUp() {
        GenderRepository genderMockRepository = Mockito.mock(GenderRepository.class);

        getMockedDbRulesForFirstNameAlgorithm(genderMockRepository);

        genderAlgorithm = new FirstNameGenderAlgorithmImpl(genderMockRepository);
    }

    @Test
    void guessMaleGenderForOneCorrectMaleToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderAlgorithm.guessGender("Karol");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.MALE);
    }

    @Test
    void guessMaleGenderForOneCorrectMaleTokenWithSpaces() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderAlgorithm.guessGender("         Karol     ");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.MALE);
    }

    @Test
    void guessFemaleGenderForOneCorrectFemaleToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderAlgorithm.guessGender("Monika");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.FEMALE);
    }

    @Test
    void throwGenderTokenNotFoundWhenNullValue() {
        assertThrows(GenderTokenNotFound.class, () -> genderAlgorithm.guessGender(null));
    }

    @Test
    void guessMaleGenderForFirstCorrectMaleAndSecondCorrectFemaleToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderAlgorithm.guessGender("Karol Monika");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.MALE);
    }

    @Test
    void guessFemaleGenderForFirstCorrectFemaleAndSecondCorrectMaleToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderAlgorithm.guessGender("Monika Karol");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.FEMALE);
    }

    @Test
    void guessFemaleGenderForFirstCorrectFemaleAndSecondCorrectMaleTokenWithSpaces() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderAlgorithm.guessGender("    Monika Karol    ");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.FEMALE);
    }

    @Test
    void throwGenderTokenNotFoundWhenIncorrectToken() {
        assertThrows(GenderTokenNotFound.class, () -> genderAlgorithm.guessGender("Adam"));
        assertThrows(GenderTokenNotFound.class, () -> genderAlgorithm.guessGender(""));
        assertThrows(GenderTokenNotFound.class, () -> genderAlgorithm.guessGender(" "));
    }

    @Test
    void getGenderAlgorithmVariant() {
        assertEquals(genderAlgorithm.getGenderAlgorithmVariant(), GenderAlgorithmVariant.FIRST);
    }
}