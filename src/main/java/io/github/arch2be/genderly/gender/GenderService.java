package io.github.arch2be.genderly.gender;

import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithm;
import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmResponse;
import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmVariant;
import io.github.arch2be.genderly.gender.exceptions.GenderAlgorithmNotImplemented;
import io.github.arch2be.genderly.gender.exceptions.GenderAlgorithmVariantNotFound;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
class GenderService {

    private final List<GenderAlgorithm> genderAlgorithms;

    GenderService(List<GenderAlgorithm> genderAlgorithms) {
        this.genderAlgorithms = genderAlgorithms;
    }

    public GenderAlgorithmResponse guessGender(GenderAlgorithmVariant genderAlgorithmVariant,
                                               String namesToCheck) {

        throwExceptionForWrongWrongGenderAlgorithmVariantValue(genderAlgorithmVariant);

        return getGenderAlgorithmByAlgorithmVariant(genderAlgorithmVariant)
                .guessGender(namesToCheck);
    }

    private void throwExceptionForWrongWrongGenderAlgorithmVariantValue(GenderAlgorithmVariant genderAlgorithmVariant) {
        if (Objects.isNull(genderAlgorithmVariant))
            throw new GenderAlgorithmVariantNotFound(null);
    }

    private GenderAlgorithm getGenderAlgorithmByAlgorithmVariant(GenderAlgorithmVariant genderAlgorithmVariant) {
        return genderAlgorithms.stream()
                .filter(genderAlgorithm -> genderAlgorithm.getGenderAlgorithmVariant().equals(genderAlgorithmVariant))
                .findFirst()
                .orElseThrow(() -> new GenderAlgorithmNotImplemented(genderAlgorithmVariant));
    }
}
