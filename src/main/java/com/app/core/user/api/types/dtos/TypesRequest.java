package com.app.core.user.api.types.dtos;

import com.app.core.user.api.types.validations.required.httpMethods.DeleteValidations;
import com.app.core.user.api.types.validations.required.httpMethods.PostValidations;
import com.app.core.user.api.types.validations.required.httpMethods.PutValidations;
import com.app.core.user.api.types.validations.required.RequiredFieldsValid;
import com.app.core.user.api.types.validations.types.TypesValid;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpMethod;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredFieldsValid
public class TypesRequest {
    private Long id;

    @TypesValid
    private String type;

    @NotBlank(message = "name must not be blank.",
            groups = { PostValidations.class, PutValidations.class, DeleteValidations.class })
    @NotNull(message = "name must not be null.",
            groups = { PostValidations.class, PutValidations.class, DeleteValidations.class })
    private String name;

    @NotBlank(message = "previous_name must not be blank.", groups = PutValidations.class)
    @NotNull(message = "previous_name must not be null.", groups = PutValidations.class)
    private String preview_name;

    private HttpMethod httpMethod;

}
