package io.github.arch2be.genderly.gender.algorithm;


import io.github.arch2be.genderly.gender.exceptions.GenderTokenNotFound;

public interface GenderAlgorithm {
    GenderAlgorithmResponse guessGender(String name) throws GenderTokenNotFound;

    GenderAlgorithmVariant getGenderAlgorithmVariant();
}
