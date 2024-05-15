package com.app.core.user.infraestructure.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TypesEntity {
    private Long id;
    private String name;
}
