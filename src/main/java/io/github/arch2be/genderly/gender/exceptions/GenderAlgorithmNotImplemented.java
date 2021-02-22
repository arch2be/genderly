package io.github.arch2be.genderly.gender.exceptions;

import io.github.arch2be.genderly.gender.algorithm.GenderAlgorithmVariant;

public class GenderAlgorithmNotImplemented extends RuntimeException {

    public GenderAlgorithmNotImplemented(GenderAlgorithmVariant algorithmVariant) {
        super("Not implemented gender algorithm for specified variant: " + algorithmVariant);
    }
}