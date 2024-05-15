package com.app.core.user.api.common.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class Validations {

    protected Validator validator;

    public Validations() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    public <T> Set<ConstraintViolation<T>> validateWithGroups(T object, Class<?>... groups) {
        return validator.validate(object, groups);
    }
}
