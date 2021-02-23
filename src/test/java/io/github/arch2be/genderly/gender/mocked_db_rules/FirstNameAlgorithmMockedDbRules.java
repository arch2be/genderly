package io.github.arch2be.genderly.gender.mocked_db_rules;

import io.github.arch2be.genderly.gender.GenderRepository;
import io.github.arch2be.genderly.gender.GenderToken;
import io.github.arch2be.genderly.gender.GenderType;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface FirstNameAlgorithmMockedDbRules {

    default void getMockedDbRulesForFirstNameAlgorithm(GenderRepository genderMockRepository) {
        Map<String, Optional<GenderToken>> repositoryMockedRules = new HashMap<>();

        repositoryMockedRules.put("Monika", Optional.of(new GenderToken("Monika", GenderType.FEMALE)));
        repositoryMockedRules.put("Karol", Optional.of(new GenderToken("Karol", GenderType.MALE)));
        repositoryMockedRules.put("Adam", Optional.empty());
        repositoryMockedRules.put(" ", Optional.empty());
        repositoryMockedRules.put("" , Optional.empty());
        repositoryMockedRules.put(null, Optional.empty());

        repositoryMockedRules.forEach((k, v) -> Mockito
                .when(genderMockRepository.findByName(k))
                .thenReturn(v));
    }
}
