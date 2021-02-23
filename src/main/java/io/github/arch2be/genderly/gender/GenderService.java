package io.github.arch2be.genderly.gender;

import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmFactory;
import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmResponse;
import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmVariant;
import io.github.arch2be.genderly.gender.exceptions.GenderAlgorithmVariantNotFound;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
class GenderService {

    private final GenderAlgorithmFactory genderAlgorithmFactory;
    private final GenderRepository genderRepository;

    GenderService(GenderAlgorithmFactory genderAlgorithmFactory, GenderRepository genderRepository) {
        this.genderAlgorithmFactory = genderAlgorithmFactory;
        this.genderRepository = genderRepository;
    }

    GenderAlgorithmResponse guessGender(GenderAlgorithmVariant genderAlgorithmVariant,
                                               String namesToCheck) {

        throwExceptionForWrongGenderAlgorithmVariantValue(genderAlgorithmVariant);

        return genderAlgorithmFactory.getGenderAlgorithm(genderAlgorithmVariant)
                .guessGender(namesToCheck);
    }

    List<GenderTokenDto> getPageOfEachAvailableNames(Integer pageSize, Integer pageNumber) {

        if (Objects.isNull(pageSize) || Objects.isNull(pageNumber)) {
            return Collections.emptyList();
        }

        return Arrays.stream(GenderType.values())
                .map(genderType -> genderRepository.findAllByGenderType(genderType, PageRequest.of(pageNumber, pageSize)))
                .map(genderTokens -> genderTokens.stream()
                        .map(GenderToken::toDto)
                        .collect(toList()))
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private void throwExceptionForWrongGenderAlgorithmVariantValue(GenderAlgorithmVariant genderAlgorithmVariant) {
        if (Objects.isNull(genderAlgorithmVariant))
            throw new GenderAlgorithmVariantNotFound(null);
    }
}
