package io.github.arch2be.genderly.gender;

import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmFactory;
import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmResponse;
import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmVariant;
import io.github.arch2be.genderly.gender.exceptions.GenderAlgorithmVariantNotFound;
import io.github.arch2be.genderly.gender.exceptions.GenderTokenNotFound;
import io.github.arch2be.genderly.gender.mocked_db_rules.AllNameAlgorithmMockedDbRules;
import io.github.arch2be.genderly.gender.mocked_db_rules.FirstNameAlgorithmMockedDbRules;
import io.github.arch2be.genderly.gender.mocked_db_rules.GetAllTokensMockedDbRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GenderServiceTest implements FirstNameAlgorithmMockedDbRules, AllNameAlgorithmMockedDbRules, GetAllTokensMockedDbRules {

    private GenderService genderService;

    @BeforeEach
    void setUp() {
        GenderRepository genderMockRepository = Mockito.mock(GenderRepository.class);

        getMockedDbRulesForAllNameAlgorithm(genderMockRepository);
        getMockedDbRulesForFirstNameAlgorithm(genderMockRepository);
        getMockedDbRulesForGetAllTokens(genderMockRepository);

        genderService = new GenderService(new GenderAlgorithmFactory(genderMockRepository), genderMockRepository);
    }

    @Test
    void guessGenderForFirstAlgorithmAndCorrectFemaleToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderService.guessGender(GenderAlgorithmVariant.FIRST, "Monika");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.FEMALE);
    }

    @Test
    void guessGenderForFirstAlgorithmAndCorrectMaleToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderService.guessGender(GenderAlgorithmVariant.FIRST, "Karol");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.MALE);
    }

    @Test
    void guessGenderForAllAlgorithmAndTwoCorrectMaleTokens() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderService.guessGender(GenderAlgorithmVariant.ALL, "Karol Adam");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.MALE);
    }

    @Test
    void guessGenderForAllAlgorithmAndTwoCorrectFemaleTokens() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderService.guessGender(GenderAlgorithmVariant.ALL, "Monika Rokita");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.FEMALE);
    }

    @Test
    void notGuessGenderForAllAlgorithmAndOneCorrectMaleTokenAndOneCorrectFemaleToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderService.guessGender(GenderAlgorithmVariant.ALL, "Karol Monika");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.INCONCLUSIVE);
    }

    @Test
    void notGuessGenderForAllAlgorithmAndNullToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderService.guessGender(GenderAlgorithmVariant.ALL, null);
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.INCONCLUSIVE);
    }

    @Test
    void throwGenderTokenNotFoundForFirstAlgorithmAndInCorrectMaleToken() {
        assertThrows(GenderTokenNotFound.class, () -> genderService.guessGender(GenderAlgorithmVariant.FIRST, null));
    }

    @Test
    void throwGenderAlgorithmVariantNotFoundForNullValues() {
        assertThrows(GenderAlgorithmVariantNotFound.class, () -> genderService.guessGender(null, null));
    }

    @Test
    void getEmptyListForGetAllTokensWhenEmptyRequestParams() {
        assertEquals(Collections.emptyList(), genderService.getPageOfEachAvailableNames(null, null));
    }

    @Test
    void getCorrectListForGetAllTokensWhenFirstPageAndTwoElements() {
        List<GenderTokenDto> pageOfEachAvailableNames = genderService.getPageOfEachAvailableNames(2, 1);

        assertEquals(pageOfEachAvailableNames.size(), 4);

        List<GenderTokenDto> maleNames = getAllNamesByGenderType(GenderType.MALE, pageOfEachAvailableNames);
        List<GenderTokenDto> femaleNames = getAllNamesByGenderType(GenderType.FEMALE, pageOfEachAvailableNames);

        assertEquals(maleNames.size(), 2);
        assertEquals(femaleNames.size(), 2);
    }

    @Test
    void getCorrectListForGetAllTokensWhenLastPageAndTwoElements() {
        List<GenderTokenDto> pageOfEachAvailableNames = genderService.getPageOfEachAvailableNames(2, 3);

        assertEquals(pageOfEachAvailableNames.size(), 3);

        List<GenderTokenDto> maleNames = getAllNamesByGenderType(GenderType.MALE, pageOfEachAvailableNames);
        List<GenderTokenDto> femaleNames = getAllNamesByGenderType(GenderType.FEMALE, pageOfEachAvailableNames);

        assertEquals(maleNames.size(), 1);
        assertEquals(femaleNames.size(), 2);
    }

    private List<GenderTokenDto> getAllNamesByGenderType(GenderType genderType, List<GenderTokenDto> listOfNames) {
        return listOfNames.stream()
                .filter(name -> name.getGenderType().equals(genderType))
                .collect(Collectors.toList());
    }
}