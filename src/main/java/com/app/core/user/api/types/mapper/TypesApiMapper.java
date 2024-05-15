package com.app.core.user.api.types.mapper;

import com.app.core.user.api.types.dtos.TypesListResponse;
import com.app.core.user.api.types.dtos.TypesRequest;
import com.app.core.user.api.types.dtos.TypesResponse;
import com.app.core.user.domain.types.model.Types;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TypesApiMapper {

    public TypesResponse convertDomainInApi(Boolean result) {
        return TypesResponse.builder()
                .result(result)
                .build();
    }

    public TypesListResponse convertListDomainInApi(List<Types> typesList) {
        return TypesListResponse.builder()
                .result(convertListRequestApi(typesList))
                .build();
    }

    public Types convertApiInDomain(TypesRequest typesRequest) {
        return Types.builder()
                .type(typesRequest.getType())
                .name(typesRequest.getName())
                .prevName(typesRequest.getPreview_name())
                .build();
    }

    private List<TypesRequest> convertListRequestApi(List<Types> typesList) {
        return typesList.stream().map(this::convertRequestInApi).toList();
    }

    private TypesRequest convertRequestInApi(Types types) {
        return TypesRequest.builder()
                .id(types.getId())
                .name(types.getName())
                .build();
    }
}
