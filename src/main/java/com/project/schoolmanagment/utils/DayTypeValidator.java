package com.project.schoolmanagment.utils;

import com.project.schoolmanagment.entity.enums.Day;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class DayTypeValidator implements ConstraintValidator<DayType,Enum<?>> {
    @Override
    public void initialize(DayType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Enum anEnum, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(Day.values()).anyMatch(e->e.name().equalsIgnoreCase(anEnum.name()));
    }
}
