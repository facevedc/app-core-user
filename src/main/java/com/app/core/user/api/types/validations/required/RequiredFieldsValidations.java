package com.app.core.user.api.types.validations.required;

import com.app.core.user.api.common.validation.Validations;
import com.app.core.user.api.types.dtos.TypesRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RequiredFieldsValidations extends Validations
        implements ConstraintValidator<RequiredFieldsValid, TypesRequest> {

    @Override
    public void initialize(RequiredFieldsValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(TypesRequest typesRequest, ConstraintValidatorContext constraintValidatorContext) {
        return switch (typesRequest.getHttpMethod().name()) {
            case "POST", "DELETE" -> validateNotNullName(typesRequest);
            case "PUT" -> validateForPut(typesRequest);
            case "GET" -> true;
            default -> false;
        };
    }

    private boolean validateNotNullName(TypesRequest request) {
        return request.getName() != null;
    }

    private boolean validateForPut(TypesRequest request) {
        return validateNotNullName(request) && request.getPreview_name() != null;
    }
}
