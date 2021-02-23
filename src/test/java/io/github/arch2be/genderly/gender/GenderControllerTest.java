package io.github.arch2be.genderly.gender;

import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmResponse;
import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmVariant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GenderControllerTest {

    @LocalServerPort
    private int port;
    private final String HOST = "http://localhost:";
    private final String GUESS_GENDER_URL = "api/gender/";
    private final String GET_ALL_TOKENS_URL = "api/gender/names";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void guessMaleGenderForCorrectFirstMaleTokensFirstAlgorithm() {
        assertEquals(restTemplate.getForObject(getFirstAlgorithmUrl("Zechariah Miah"), GenderAlgorithmResponse.class), GenderAlgorithmResponse.MALE);
    }

    @Test
    void guessFemaleGenderForCorrectFirstFemaleTokensFirstAlgorithm() {
        assertEquals(restTemplate.getForObject(getFirstAlgorithmUrl("Miah Zechariah"), GenderAlgorithmResponse.class), GenderAlgorithmResponse.FEMALE);
    }

    @Test
    void guessMaleGenderForCorrectTwoMaleTokensAndOneCorrectFemaleTokenAllAlgorithm() {
        assertEquals(restTemplate.getForObject(getAllAlgorithmUrl("Zechariah Jamir Miah"), GenderAlgorithmResponse.class), GenderAlgorithmResponse.MALE);
    }

    @Test
    void guessFemaleGenderForCorrectTwoFemaleTokensAndOneCorrectMaleTokenAllAlgorithm() {
        assertEquals(restTemplate.getForObject(getAllAlgorithmUrl("Mayra Jamir Miah"), GenderAlgorithmResponse.class), GenderAlgorithmResponse.FEMALE);
    }

    @Test
    void notGuessGenderForCorrectOneFemaleTokensAndOneCorrectMaleTokenAllAlgorithm() {
        assertEquals(restTemplate.getForObject(getAllAlgorithmUrl("Zechariah Miah"), GenderAlgorithmResponse.class), GenderAlgorithmResponse.INCONCLUSIVE);
    }

    @Test
    void getAllAvailableNamesGroupedByGendersForFirstPageAndTwoElements() {
        GenderTokenDto[] forObject = restTemplate.getForObject(getAllNamesUrl(10, 1), GenderTokenDto[].class);
        assertEquals(forObject.length, 20);
    }

    private String getFirstAlgorithmUrl(String name) {
        return getUrlForGuessAlgorithm(GenderAlgorithmVariant.FIRST, name);
    }

    private String getAllAlgorithmUrl(String names) {
        return getUrlForGuessAlgorithm(GenderAlgorithmVariant.ALL, names);
    }

    private String getUrlForGuessAlgorithm(GenderAlgorithmVariant genderAlgorithmVariant, String name) {
        return HOST + port + "/" + GUESS_GENDER_URL + "?algorithmVariant=" + genderAlgorithmVariant + "&namesToCheck=" + name;
    }

    private String getAllNamesUrl(Integer pageSize, Integer pageNumber) {
        return HOST + port + "/" + GET_ALL_TOKENS_URL + "?pageSize=" + pageSize + "&pageNumber=" + pageNumber;
    }
}