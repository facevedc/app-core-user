package com.app.core.user.infraestructure.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "user_status")
@Data
public class UserStatusEntity {
    private Long id;
    private String name;
}
