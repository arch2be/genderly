package io.github.arch2be.genderly.gender;

class GenderAlgorithmVariantNotFound extends Throwable {
    public GenderAlgorithmVariantNotFound(String code) {
        super("Not found particular gender algorithm variant for specify code: " + code);
    }
}
