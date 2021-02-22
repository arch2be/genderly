package io.github.arch2be.genderly.gender.exceptions;

public class GenderTokenNotFound extends RuntimeException {

    public GenderTokenNotFound(String name) {
        super("Not found particular gender for specified name: " + name);
    }
}
