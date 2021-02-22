package io.github.arch2be.genderly.gender.algorithm;

import io.github.arch2be.genderly.gender.GenderRepository;
import io.github.arch2be.genderly.gender.GenderToken;
import io.github.arch2be.genderly.gender.GenderType;
import io.github.arch2be.genderly.gender.exceptions.GenderTokenNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FirstNameGenderAlgorithmImplTest {

    private GenderRepository genderMockRepository;
    private GenderAlgorithm genderAlgorithm;

    @BeforeEach
    void setUp() {
        genderMockRepository = Mockito.mock(GenderRepository.class);
        genderAlgorithm = new FirstNameGenderAlgorithmImpl(genderMockRepository);

        Mockito
                .when(genderMockRepository.findByName("Monika"))
                .thenReturn(Optional.of(new GenderToken("Monika", GenderType.FEMALE)));

        Mockito
                .when(genderMockRepository.findByName("Karol"))
                .thenReturn(Optional.of(new GenderToken("Karol", GenderType.MALE)));

        Mockito
                .when(genderMockRepository.findByName("Adam"))
                .thenReturn(Optional.empty());

        Mockito
                .when(genderMockRepository.findByName(""))
                .thenReturn(Optional.empty());

        Mockito
                .when(genderMockRepository.findByName(" "))
                .thenReturn(Optional.empty());

        Mockito
                .when(genderMockRepository.findByName(null))
                .thenReturn(Optional.empty());
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