package io.github.arch2be.genderly.gender.algorithm;

import io.github.arch2be.genderly.gender.exceptions.GenderAlgorithmVariantNotFound;

import java.util.Arrays;

public enum GenderAlgorithmVariant {
    FIRST("FIRST", "Only first provided token to check"),
    ALL("ALL", "All provided tokens to check");

    private final String code;
    private final String description;

    GenderAlgorithmVariant(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public GenderAlgorithmVariant findByCode(String code) throws GenderAlgorithmVariantNotFound {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new GenderAlgorithmVariantNotFound(code));
    }
}
