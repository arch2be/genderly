package io.github.arch2be.genderly.gender.mocked_db_rules;

import io.github.arch2be.genderly.gender.GenderRepository;
import io.github.arch2be.genderly.gender.GenderToken;
import io.github.arch2be.genderly.gender.GenderType;
import org.mockito.Mockito;

import java.util.*;

public interface AllNameAlgorithmMockedDbRules {

    default void getMockedDbRulesForAllNameAlgorithm(GenderRepository genderMockRepository) {
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
}
