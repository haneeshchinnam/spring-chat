package com.example.practice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoSpecialCharactersValidator implements ConstraintValidator<NoSpecialCharacters, String> {
    @Override
    public void initialize(NoSpecialCharacters constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("string "+s);
        if (s == null) return true;

        // Allow only letters, numbers and underscores
        return s.matches("[a-zA-Z0-9_]*");
    }
}
