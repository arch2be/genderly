package io.github.arch2be.genderly.gender.mocked_db_rules;

import io.github.arch2be.genderly.gender.GenderRepository;
import io.github.arch2be.genderly.gender.GenderToken;
import io.github.arch2be.genderly.gender.GenderType;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;

import java.util.*;

public interface GetAllTokensMockedDbRules {

    default void getMockedDbRulesForGetAllTokens(GenderRepository genderMockRepository) {
        Map<GenderSearchPageable, List<GenderToken>> repositoryMockedRules = new HashMap<>();

        repositoryMockedRules.put(new GenderSearchPageable(GenderType.MALE, 1, 2), Arrays.asList(new GenderToken("Karol", GenderType.MALE), new GenderToken("Adam", GenderType.MALE)));
        repositoryMockedRules.put(new GenderSearchPageable(GenderType.FEMALE, 1, 2), Arrays.asList(new GenderToken("Monika", GenderType.FEMALE), new GenderToken("Rokita", GenderType.FEMALE)));

        repositoryMockedRules.put(new GenderSearchPageable(GenderType.MALE, 3, 2), Collections.singletonList(new GenderToken("Karol", GenderType.MALE)));
        repositoryMockedRules.put(new GenderSearchPageable(GenderType.FEMALE, 3, 2), Arrays.asList(new GenderToken("Monika", GenderType.FEMALE), new GenderToken("Rokita", GenderType.FEMALE)));

        repositoryMockedRules.forEach((k, v) -> Mockito
                .when(genderMockRepository.findAllByGenderType(k.getGenderType(), PageRequest.of(k.getPageNumber(), k.getPageSize())))
                .thenReturn(v));
    }
}
