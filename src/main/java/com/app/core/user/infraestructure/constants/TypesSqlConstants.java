package com.app.core.user.infraestructure.constants;

public class TypesSqlConstants {
    public final static String FIND_ALL = "SELECT id, name FROM %s";
    public final static String INSERT = "INSERT INTO %s(name) VALUES ('%s')";
    public final static String UPDATE = "UPDATE %s SET name = '%s' WHERE name = '%s'";
    public final static String DELETE = "DELETE FROM %s WHERE name = '%s'";

    public final static String MESSAGE_ERROR_FIND_ALL = "Error finding all data in %s, e: %s";
    public final static String MESSAGE_ERROR_INSERT = "Error insert data in %s, e: %s";
    public final static String MESSAGE_ERROR_UPDATE = "Error update data in %s, e: %s";
    public final static String MESSAGE_ERROR_DELETE = "Error delete data in %s, e: %s";
}
