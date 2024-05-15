package com.app.core.user.api.types.validations.types;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TypesValidations.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypesValid {
    String message() default "Invalid type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
