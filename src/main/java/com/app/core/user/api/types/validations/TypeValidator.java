package com.app.core.user.api.types.validations;

import com.app.core.user.api.common.exception.BadRequestException;
import com.app.core.user.api.common.validation.Validations;
import com.app.core.user.api.types.dtos.TypesRequest;
import com.app.core.user.api.types.validations.required.httpMethods.PostValidations;
import com.app.core.user.api.types.validations.required.httpMethods.PutValidations;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Component
public class TypeValidator extends Validations {

    public Mono<TypesRequest> validate(TypesRequest request) {
        Set<ConstraintViolation<TypesRequest>> violations =
                switch (request.getHttpMethod().name()) {
                    case "POST", "DELETE" -> validator.validate(request, PostValidations.class);
                    case "PUT" -> validator.validate(request, PutValidations.class);
                    default -> validator.validate(request);
                };
        List<String> errorMessages = violations.stream().map(ConstraintViolation::getMessage).toList();
        if (!violations.isEmpty()) {
            return Mono.error(new BadRequestException("Validation error", errorMessages));
        }
        return Mono.just(request);
    }

    public Mono<TypesRequest> validate(String type, String name, HttpMethod httpMethod) {
        TypesRequest typesRequest = TypesRequest.builder()
                .type(type)
                .name(name)
                .httpMethod(httpMethod)
                .build();
        return validate(typesRequest);
    }

    public Mono<TypesRequest> validate(String type, HttpMethod httpMethod) {
        TypesRequest typesRequest = TypesRequest.builder()
                .type(type)
                .httpMethod(httpMethod)
                .build();
        return validate(typesRequest);
    }
}
