package io.github.arch2be.genderly.gender;

import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmResponse;
import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmVariant;
import io.github.arch2be.genderly.gender.exceptions.GenderAlgorithmVariantNotFound;
import io.github.arch2be.genderly.gender.exceptions.GenderTokenNotFound;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GenderServiceTest {

    @Autowired
    private GenderService genderService;

    @Test
    void guessGenderForFirstAlgorithmAndCorrectFemaleToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderService.guessGender(GenderAlgorithmVariant.FIRST, "Miah");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.FEMALE);
    }

    @Test
    void guessGenderForFirstAlgorithmAndCorrectMaleToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderService.guessGender(GenderAlgorithmVariant.FIRST, "Zechariah");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.MALE);
    }

    @Test
    void guessGenderForAllAlgorithmAndTwoCorrectMaleTokens() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderService.guessGender(GenderAlgorithmVariant.ALL, "Zechariah Jamir");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.MALE);
    }

    @Test
    void guessGenderForAllAlgorithmAndTwoCorrectFemaleTokens() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderService.guessGender(GenderAlgorithmVariant.ALL, "Miah Mayra");
        assertEquals(genderAlgorithmResponse, GenderAlgorithmResponse.FEMALE);
    }

    @Test
    void notGuessGenderForAllAlgorithmAndOneCorrectMaleTokenAndOneCorrectFemaleToken() {
        GenderAlgorithmResponse genderAlgorithmResponse = genderService.guessGender(GenderAlgorithmVariant.ALL, "Zechariah Mayra");
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
}