package io.github.arch2be.genderly.gender.algorithm;

import io.github.arch2be.genderly.gender.GenderRepository;
import io.github.arch2be.genderly.gender.exceptions.GenderAlgorithmNotImplemented;
import org.springframework.stereotype.Service;

@Service
public class GenderAlgorithmFactory {

    private final GenderRepository genderRepository;

    public GenderAlgorithmFactory(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    public GenderAlgorithm getGenderAlgorithm(GenderAlgorithmVariant genderAlgorithmVariant) {
        switch (genderAlgorithmVariant) {
            case ALL: return new AllNameGenderAlgorithmImpl(genderRepository);
            case FIRST: return new FirstNameGenderAlgorithmImpl(genderRepository);
            default: throw new GenderAlgorithmNotImplemented(genderAlgorithmVariant);
        }
    }
}
