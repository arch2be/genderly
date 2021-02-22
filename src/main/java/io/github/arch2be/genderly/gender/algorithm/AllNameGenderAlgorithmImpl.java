package io.github.arch2be.genderly.gender.algorithm;

import io.github.arch2be.genderly.gender.GenderRepository;
import io.github.arch2be.genderly.gender.GenderToken;
import io.github.arch2be.genderly.gender.GenderType;
import io.github.arch2be.genderly.gender.exceptions.GenderTokenNotFound;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.*;

@Service
class AllNameGenderAlgorithmImpl implements GenderAlgorithm {

    private final GenderRepository genderRepository;

    public AllNameGenderAlgorithmImpl(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    @Override
    public GenderAlgorithmResponse guessGender(String name) throws GenderTokenNotFound {
        Map<GenderType, Long> groupedGenderTokens = groupByGenderTokensWithCountingOccurrence(genderRepository.findByNameIn(mapStringToTokenArray(name)));
        List<Map.Entry<GenderType, Long>> reduceGenderTokensWithOccurrenceForMaxValues = reduceGenderTokensWithOccurrenceForMaxValues(groupedGenderTokens);

        return reduceGenderTokensWithOccurrenceForMaxValues.size() != 1
                ? GenderAlgorithmResponse.INCONCLUSIVE
                : GenderAlgorithmResponse.fromGenderType(reduceGenderTokensWithOccurrenceForMaxValues.get(0).getKey());
    }

    @Override
    public GenderAlgorithmVariant getGenderAlgorithmVariant() {
        return GenderAlgorithmVariant.ALL;
    }

    private List<Map.Entry<GenderType, Long>> reduceGenderTokensWithOccurrenceForMaxValues(Map<GenderType, Long> groupedGenderTokens) {
        return Objects.nonNull(groupedGenderTokens) && groupedGenderTokens.size() > 0
                ? groupedGenderTokens.entrySet().stream()
                        .collect(groupingBy(Map.Entry::getValue, TreeMap::new, toList()))
                        .lastEntry()
                        .getValue()
                : Collections.emptyList();
    }

    private Map<GenderType, Long> groupByGenderTokensWithCountingOccurrence(List<GenderToken> genderTokens) {
        return genderTokens.stream()
                .collect(groupingBy(GenderToken::getGenderType, counting()));
    }

    private List<String> mapStringToTokenArray(String name) {
        return Objects.nonNull(name) && !name.trim().isEmpty()
                ? Arrays.asList(name.trim().split(TOKEN_SEPARATOR))
                : Collections.emptyList();
    }
}
