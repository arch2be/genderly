package io.github.arch2be.genderly.gender.algorithm;

import io.github.arch2be.genderly.gender.GenderRepository;
import io.github.arch2be.genderly.gender.GenderToken;
import io.github.arch2be.genderly.gender.GenderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AllNameGenderAlgorithmImplTest {

    private GenderRepository genderMockRepository;
    private GenderAlgorithm genderAlgorithm;

    @BeforeEach
    void setUp() {
        genderMockRepository = Mockito.mock(GenderRepository.class);
        genderAlgorithm = new AllNameGenderAlgorithmImpl(genderMockRepository);

        Map<List<String>, List<GenderToken>> repositoryMockedRules = new HashMap<>();

        repositoryMockedRules.put(Collections.singletonList("Monika"), Collections.singletonList(new GenderToken("Monika", GenderType.FEMALE)));
        repositoryMockedRules.put(Collections.singletonList("Karol"), Collections.singletonList(new GenderToken("Karol", GenderType.MALE)));
        repositoryMockedRules.put(Collections.singletonList("Adam"), Collections.emptyList());
        repositoryMockedRules.put(Collections.singletonList(""), Collections.emptyList());
        repositoryMockedRules.put(Collections.singletonList("" ), Collections.emptyList());
        repositoryMockedRules.put(Collections.singletonList(null), Collections.emptyList());
        repositoryMockedRules.put(Arrays.asList("Karol", "Monika"), Arrays.asList(new GenderToken("Karol", GenderType.MALE), new GenderToken("Monika", GenderType.FEMALE)));
        repositoryMockedRules.put(Arrays.asList("Karol", "Adam"), Arrays.asList(new GenderToken("Karol", GenderType.MALE), new GenderToken("Adam", GenderType.MALE)));
        repositoryMockedRules.put(Arrays.asList("Monika", "Rokita"), Arrays.asList(new GenderToken("Monika", GenderType.FEMALE), new GenderToken("Rokita", GenderType.FEMALE)));
        repositoryMockedRules.put(Arrays.asList("Jan", "Maria", "Rokita"), Arrays.asList(new GenderToken("Jan", GenderType.MALE), new GenderToken("Maria", GenderType.FEMALE)));
        repositoryMockedRules.put(Arrays.asList("Jan", "Adam", "Rokita"), Arrays.asList(new GenderToken("Jan", GenderType.MALE), new GenderToken("Adam", GenderType.MALE), new GenderToken("Rokita", GenderType.FEMALE)));
        repositoryMockedRules.put(Arrays.asList("Jan", "Monika", "Rokita"), Arrays.asList(new GenderToken("Jan", GenderType.MALE), new GenderToken("Monika", GenderType.FEMALE), new GenderToken("Rokita", GenderType.FEMALE)));

        repositoryMockedRules.forEach((k, v) -> Mockito
                .when(genderMockRepository.findByNameIn(k))
                .thenReturn(v));
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