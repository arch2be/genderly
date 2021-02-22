package io.github.arch2be.genderly.gender.algorithm;

import io.github.arch2be.genderly.gender.GenderRepository;
import io.github.arch2be.genderly.gender.exceptions.GenderTokenNotFound;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
class FirstNameGenderAlgorithmImpl implements GenderAlgorithm {

    private final GenderRepository genderRepository;

    public FirstNameGenderAlgorithmImpl(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    @Override
    public GenderAlgorithmResponse guessGender(String name) throws GenderTokenNotFound {
        return genderRepository.findByName(findFirstToken(name))
                .map(genderToken -> GenderAlgorithmResponse.fromGenderType(genderToken.getGenderType()))
                .orElseThrow(() -> new GenderTokenNotFound(name));
    }

    @Override
    public GenderAlgorithmVariant getGenderAlgorithmVariant() {
        return GenderAlgorithmVariant.FIRST;
    }

    private String findFirstToken(String name) {
        return Objects.nonNull(name) && !name.trim().isEmpty()
                ? name.trim().split(TOKEN_SEPARATOR)[0]
                : null;
    }
}
