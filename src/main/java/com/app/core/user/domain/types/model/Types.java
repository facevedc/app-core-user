package com.app.core.user.domain.types.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Types {
    private Long id;
    private String type;
    private String prevName;
    private String name;
}
