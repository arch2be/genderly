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
        Objects.requireNonNull(name);

        String[] names = name.split(" ");

        Map<GenderType, Long> groupedGenderTokens = genderRepository.findByNameIn(Arrays.asList(names.clone()))
                .stream()
                .collect(groupingBy(GenderToken::getGenderType, counting()));

        List<Map.Entry<GenderType, Long>> value = groupedGenderTokens.entrySet().stream()
                .collect(groupingBy(Map.Entry::getValue, TreeMap::new, toList()))
                .lastEntry()
                .getValue();

        return value.size() > 1
                ? GenderAlgorithmResponse.INCONCLUSIVE
                : GenderAlgorithmResponse.fromGenderType(value.get(0).getKey());
    }

    @Override
    public GenderAlgorithmVariant getGenderAlgorithmVariant() {
        return GenderAlgorithmVariant.ALL;
    }
}
