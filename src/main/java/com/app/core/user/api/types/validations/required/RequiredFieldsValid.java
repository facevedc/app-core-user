package com.app.core.user.api.types.validations.required;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RequiredFieldsValidations.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredFieldsValid {
    String message() default "Required field is missing";
        Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
