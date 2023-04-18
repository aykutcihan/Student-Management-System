package com.project.schoolmanagment.entity.enums.enumValidate;


import com.project.schoolmanagment.entity.enums.Gender;
import com.project.schoolmanagment.entity.enums.enumValidate.constraits.GenderTypeSubset;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;



public class GenderTypeSubSetValidator implements ConstraintValidator<GenderTypeSubset, Gender> {
    private Gender[] subset;

    @Override
    public void initialize(GenderTypeSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(Gender value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset)
                .contains(value);
    }
}
