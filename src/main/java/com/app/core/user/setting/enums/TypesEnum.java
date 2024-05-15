package com.app.core.user.setting.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypesEnum {
    USER_TYPE("user_type", "user-type"),
    USER_STATUS("user_status", "user-status"),
    DNI_TYPE("dni_type", "dni-type");

    private final String tableValue;
    private final String pathValue;
}
