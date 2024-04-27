package com.clearsolutions.validation.anotation;

import com.clearsolutions.validation.AdultValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {AdultValidator.class})
public @interface Adult {
    String message() default "Age restriction";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}