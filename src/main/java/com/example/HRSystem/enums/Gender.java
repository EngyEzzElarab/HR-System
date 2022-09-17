package com.example.HRSystem.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MALE ("MALE"), // 0
    FEMALE ("FEMALE");  // 1
    private String genderCode;

    public String getGenderCode() {

        return this.genderCode;
    }

    @JsonCreator
    public static Gender getGenderFromCode(String value) {

        for (Gender gender : Gender.values()) {

            if (gender.getGenderCode().equals(value)) {

                return gender;
            }
        }

        return null;
    }

    Gender(String genderCode) {
        this.genderCode = genderCode;
    }
}
