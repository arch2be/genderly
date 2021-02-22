package io.github.arch2be.genderly.gender.exceptions;

public class GenderAlgorithmVariantNotFound extends RuntimeException {

    public GenderAlgorithmVariantNotFound(String code) {
        super("Not found particular gender algorithm variant for specified code: " + code);
    }
}
