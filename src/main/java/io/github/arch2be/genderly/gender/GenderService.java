package io.github.arch2be.genderly.gender;

import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithm;
import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmResponse;
import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmVariant;
import io.github.arch2be.genderly.gender.exceptions.GenderAlgorithmNotImplemented;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class GenderService {

    private final List<GenderAlgorithm> genderAlgorithms;

    GenderService(List<GenderAlgorithm> genderAlgorithms) {
        this.genderAlgorithms = genderAlgorithms;
    }

    public GenderAlgorithmResponse guessGender(GenderAlgorithmVariant genderAlgorithmVariant,
                                               String namesToCheck) {
        return getGenderAlgorithmByAlgorithmVariant(genderAlgorithmVariant)
                .guessGender(namesToCheck);
    }

    private GenderAlgorithm getGenderAlgorithmByAlgorithmVariant(GenderAlgorithmVariant genderAlgorithmVariant) {
        return genderAlgorithms.stream()
                .filter(genderAlgorithm -> genderAlgorithm.getGenderAlgorithmVariant().equals(genderAlgorithmVariant))
                .findFirst()
                .orElseThrow(() -> new GenderAlgorithmNotImplemented(genderAlgorithmVariant));
    }
}
