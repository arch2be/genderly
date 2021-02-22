package io.github.arch2be.genderly.gender.algorithm;

import io.github.arch2be.genderly.gender.GenderRepository;
import io.github.arch2be.genderly.gender.GenderToken;
import io.github.arch2be.genderly.gender.GenderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AllNameGenderAlgorithmImplTest {

    private GenderRepository genderMockRepository;
    private GenderAlgorithm genderAlgorithm;

    @BeforeEach
    void setUp() {
        genderMockRepository = Mockito.mock(GenderRepository.class);
        genderAlgorithm = new FirstNameGenderAlgorithmImpl(genderMockRepository);

        Mockito
                .when(genderMockRepository.findByNameIn(Collections.singletonList("Monika")))
                .thenReturn(Collections.singletonList(new GenderToken("Monika", GenderType.FEMALE)));

        Mockito
                .when(genderMockRepository.findByNameIn(Collections.singletonList("Karol")))
                .thenReturn(Collections.singletonList(new GenderToken("Karol", GenderType.MALE)));

        Mockito
                .when(genderMockRepository.findByNameIn(Collections.singletonList("Adam")))
                .thenReturn(Collections.emptyList());

        Mockito
                .when(genderMockRepository.findByNameIn(Collections.singletonList("")))
                .thenReturn(Collections.emptyList());

        Mockito
                .when(genderMockRepository.findByNameIn(Collections.singletonList(" ")))
                .thenReturn(Collections.emptyList());

        Mockito
                .when(genderMockRepository.findByNameIn(null))
                .thenReturn(Collections.emptyList());

        Mockito
                .when(genderMockRepository.findByNameIn(Arrays.asList("Karol", "Monika")))
                .thenReturn(Arrays.asList(new GenderToken("Karol", GenderType.MALE), new GenderToken("Monika", GenderType.FEMALE)));

        Mockito
                .when(genderMockRepository.findByNameIn(Arrays.asList("Karol", "Monika")))
                .thenReturn(Arrays.asList(new GenderToken("Karol", GenderType.MALE), new GenderToken("Monika", GenderType.FEMALE)));

        Mockito
                .when(genderMockRepository.findByNameIn(Arrays.asList("Karol", "Adam")))
                .thenReturn(Arrays.asList(new GenderToken("Karol", GenderType.MALE), new GenderToken("Adam", GenderType.MALE)));

        Mockito
                .when(genderMockRepository.findByNameIn(Arrays.asList("Monika", "Rokita")))
                .thenReturn(Arrays.asList(new GenderToken("Monika", GenderType.FEMALE), new GenderToken("Rokita", GenderType.FEMALE)));

        Mockito
                .when(genderMockRepository.findByNameIn(Arrays.asList("Jan", "Maria", "Rokita")))
                .thenReturn(Arrays.asList(new GenderToken("Jan", GenderType.MALE), new GenderToken("Maria", GenderType.FEMALE)));
    }

    @Test
    void test() {

    }
}