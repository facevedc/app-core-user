package com.app.core.user.infraestructure.mapper;

import com.app.core.user.infraestructure.entity.TypesEntity;
import com.app.core.user.setting.enums.TypesEnum;
import com.app.core.user.infraestructure.exceptions.NotFoundException;
import io.r2dbc.spi.Row;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.app.core.user.infraestructure.constants.TypesSqlConstants.*;

@Component
@AllArgsConstructor
public class TypesRepositoryMapper {

    public TypesEntity convertRowInRepository(Row row) {
        return TypesEntity.builder()
                .id(row.get("id", Long.class))
                .name(row.get("name", String.class))
                .build();
    }

    public String queryFormatSelect(String types) {
        return FIND_ALL.formatted(selectedQueryType(types));
    }

    public String queryFormatInsert(String types, TypesEntity typesEntity) {
        return INSERT.formatted(selectedQueryType(types), typesEntity.getName());
    }

    public String queryFormatUpdate(String types, TypesEntity typesEntity, String name) {
        return UPDATE.formatted(selectedQueryType(types), typesEntity.getName(), name);
    }

    public String queryFormatDelete(String types, String name) {
        return DELETE.formatted(selectedQueryType(types), name);
    }

    private String selectedQueryType(String types) {
        for (TypesEnum type : TypesEnum.values()) {
            if (type.getPathValue().equalsIgnoreCase(types)) {
                return type.getTableValue();
            }
        }
        throw new NotFoundException("Entity not exist.");
    }
}
