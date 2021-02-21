package io.github.arch2be.genderly.gender.algorithm;

interface GenderAlgorithm {

    GenderAlgorithmResponse guessGenderForFirstProvidedName(String name);

    GenderAlgorithmResponse guessGenderForAllProvidedNames(String name);
}
