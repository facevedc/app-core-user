package com.app.core.user.api.types;

import com.app.core.user.api.common.handler.CommonHandler;
import com.app.core.user.api.types.dtos.TypesRequest;
import com.app.core.user.api.types.mapper.TypesApiMapper;
import com.app.core.user.api.types.validations.TypeValidator;
import com.app.core.user.domain.types.TypesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class TypesHandler extends CommonHandler {

    private final TypesApiMapper typesApiMapper;
    private final TypesService typesService;
    private final TypeValidator typeValidator;

    public Mono<ServerResponse> find(ServerRequest serverRequest) {
        String types = serverRequest.pathVariable("types");
        String name = serverRequest.queryParam("name").orElse(null);
        return name == null ? findAll(types) : findOne(types, name);
    }

    private Mono<ServerResponse> findOne(String types, String name) {
        return executeHandler(this.typeValidator.validate(types, name, HttpMethod.GET)
                .map(this.typesApiMapper::convertApiInDomain)
                .flatMap(this.typesService::findOne)
                .map(this.typesApiMapper::convertListDomainInApi)
        );
    }

    private Mono<ServerResponse> findAll(String types) {
        return executeHandler(this.typeValidator.validate(types, HttpMethod.GET)
                .map(this.typesApiMapper::convertApiInDomain)
                .flatMap(this.typesService::find)
                .map(this.typesApiMapper::convertListDomainInApi)
        );
    }

    public Mono<ServerResponse> insert(ServerRequest serverRequest) {
        String types = serverRequest.pathVariable("types");
        return executeHandler(serverRequest.bodyToMono(TypesRequest.class)
                .map(typesRequest -> addTypeBody(types, HttpMethod.POST, typesRequest))
                .flatMap(this.typeValidator::validate)
                .map(this.typesApiMapper::convertApiInDomain)
                .flatMap(this.typesService::insert)
                .map(this.typesApiMapper::convertDomainInApi)
        );
    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        String types = serverRequest.pathVariable("types");
        return executeHandler(serverRequest.bodyToMono(TypesRequest.class)
                .map(typesRequest -> addTypeBody(types, HttpMethod.PUT, typesRequest))
                .flatMap(this.typeValidator::validate)
                .map(this.typesApiMapper::convertApiInDomain)
                .flatMap(this.typesService::update)
                .map(this.typesApiMapper::convertDomainInApi)
        );
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String types = serverRequest.pathVariable("types");
        String name = serverRequest.queryParam("name").orElse(null);
        return executeHandler(
                this.typeValidator.validate(types, name, HttpMethod.DELETE)
                    .map(this.typesApiMapper::convertApiInDomain)
                    .flatMap(this.typesService::delete)
                    .map(this.typesApiMapper::convertDomainInApi)
        );
    }

    private TypesRequest addTypeBody(String types, HttpMethod httpMethod, TypesRequest typesRequest) {
        typesRequest.setType(types);
        typesRequest.setHttpMethod(httpMethod);
        return typesRequest;
    }
}
