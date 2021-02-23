package io.github.arch2be.genderly.gender;

public class GenderTokenDto {
    private String name;
    private GenderType genderType;

    public GenderTokenDto(String name, GenderType genderType) {
        this.name = name;
        this.genderType = genderType;
    }

    public String getName() {
        return name;
    }

    public GenderType getGenderType() {
        return genderType;
    }
}
