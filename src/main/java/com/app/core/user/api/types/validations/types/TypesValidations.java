package com.app.core.user.api.types.validations.types;

import com.app.core.user.setting.enums.TypesEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class TypesValidations implements ConstraintValidator<TypesValid, String> {
    private final List<String> validTypes = Arrays.stream(TypesEnum.values()).map(TypesEnum::getPathValue).toList();

    @Override
    public void initialize(TypesValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
            return value != null && validTypes.contains(value);
    }
}
