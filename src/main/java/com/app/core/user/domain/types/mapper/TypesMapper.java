package com.app.core.user.domain.types.mapper;

import com.app.core.user.domain.types.model.Types;
import com.app.core.user.infraestructure.entity.TypesEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TypesMapper {

    public Types convertRepositoryInDomain(TypesEntity typesEntity) {
        return Types.builder()
                .id(typesEntity.getId())
                .name(typesEntity.getName())
                .build();
    }

    public List<Types> convertListRepositoryInDomain(List<TypesEntity> typesEntityList) {
        return typesEntityList.stream()
                .map(this::convertRepositoryInDomain)
                .collect(Collectors.toList());
    }

    public TypesEntity convertDomainInRepository(Types types) {
        return TypesEntity.builder()
                .name(types.getName())
                .build();
    }
}
