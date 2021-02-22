package io.github.arch2be.genderly.gender.algorithm;

import io.github.arch2be.genderly.gender.GenderType;

import java.util.Arrays;

public enum GenderAlgorithmResponse {
    FEMALE,
    MALE,
    INCONCLUSIVE;

    public static GenderAlgorithmResponse fromGenderType(GenderType genderType) {
        return Arrays.stream(values())
                .filter(genderAlgorithmResponse -> genderAlgorithmResponse.name().equals(genderType.name()))
                .findFirst()
                .orElse(GenderAlgorithmResponse.INCONCLUSIVE);
    }
}
