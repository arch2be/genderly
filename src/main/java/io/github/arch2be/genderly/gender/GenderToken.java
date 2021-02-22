package io.github.arch2be.genderly.gender;

import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;

@Entity
public class GenderToken {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    @PersistenceConstructor
    public GenderToken() {
    }

    public GenderToken(String name, GenderType genderType) {
        this.name = name;
        this.genderType = genderType;
    }

    public String getName() {
        return name;
    }

    public GenderType getGenderType() {
        return genderType;
    }

    @Override
    public String toString() {
        return "GenderToken{" +
                "name='" + name + '\'' +
                ", genderType=" + genderType +
                '}';
    }
}
