package com.project.schoolmanagment.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = DayTypeValidator.class)
public @interface DayType {

    String message() default "Invalid day";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
