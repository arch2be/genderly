package io.github.arch2be.genderly.gender.algorithm;

import io.github.arch2be.genderly.gender.GenderRepository;
import io.github.arch2be.genderly.gender.mocked_db_rules.AllNameAlgorithmMockedDbRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AllNameGenderAlgorithmImplTest implements AllNameAlgorithmMockedDbRules {

    private GenderAlgorithm genderAlgorithm;

    @BeforeEach
    void setUp() {
        GenderRepository genderMockRepository = Mockito.mock(GenderRepository.class);

        getMockedDbRulesForAllNameAlgorithm(genderMockRepository);

        genderAlgorithm = new AllNameGenderAlgorithmImpl(genderMockRepository);
    }

    @Test
    void guessFemaleForOneCorrectFemaleToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderAlgorithm.guessGender("Monika");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.FEMALE);
    }

    @Test
    void guessMaleForOneCorrectMaleToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderAlgorithm.guessGender("Karol");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.MALE);
    }

    @Test
    void notGuessGenderForWrongToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderAlgorithm.guessGender("Adam");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.INCONCLUSIVE);

        GenderAlgorithmResponse genderAlgorithmResponseEmptyString = genderAlgorithm.guessGender("");
        assertEquals(genderAlgorithmResponseEmptyString, GenderAlgorithmResponse.INCONCLUSIVE);

        GenderAlgorithmResponse genderAlgorithmResponseNotTrimmedString = genderAlgorithm.guessGender(" ");
        assertEquals(genderAlgorithmResponseNotTrimmedString, GenderAlgorithmResponse.INCONCLUSIVE);
    }

    @Test
    void notGuessGenderForNullValue() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderAlgorithm.guessGender(null);
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.INCONCLUSIVE);
    }

    @Test
    void notGuessGenderForOneMaleTokenAndOneFemaleToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderAlgorithm.guessGender("   Karol Monika");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.INCONCLUSIVE);
    }

    @Test
    void guessMaleGenderForTwoMaleTokens() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderAlgorithm.guessGender("Karol Adam   ");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.MALE);
    }

    @Test
    void guessFemaleGenderForTwoFemaleTokens() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderAlgorithm.guessGender("    Monika Rokita    ");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.FEMALE);
    }

    @Test
    void notGuessGenderForOneMaleTokenAndOneFemaleTokenAndForOneNotFoundToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderAlgorithm.guessGender("   Jan Maria Rokita    ");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.INCONCLUSIVE);
    }

    @Test
    void notGuessMaleForTwoMaleTokenAndOneFemaleToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderAlgorithm.guessGender("   Jan Adam Rokita    ");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.MALE);
    }

    @Test
    void notGuessFemaleForOneMaleTokenAndTwoFemaleToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderAlgorithm.guessGender("   Jan Monika Rokita    ");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.FEMALE);
    }

    @Test
    void getGenderAlgorithmVariant() {
        assertEquals(genderAlgorithm.getGenderAlgorithmVariant(), GenderAlgorithmVariant.ALL);
    }
}