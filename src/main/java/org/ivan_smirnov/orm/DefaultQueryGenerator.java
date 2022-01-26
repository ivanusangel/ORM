package org.ivan_smirnov.orm;

import org.ivan_smirnov.orm.annotation.Column;
import org.ivan_smirnov.orm.annotation.Id;
import org.ivan_smirnov.orm.annotation.Table;
import org.ivan_smirnov.orm.exception.WrongAnnotationException;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.StringJoiner;

public class DefaultQueryGenerator implements QueryGenerator {
    @Override
    public String findAll(Class<?> clazz) {
        String tableName = getTableName(clazz);

        StringBuilder result = new StringBuilder("SELECT ");
        StringJoiner parameters = new StringJoiner(", ");

        for (Field declaredField : clazz.getDeclaredFields()) {
            Column columnAnnotation = declaredField.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                String columnName = columnAnnotation.name();
                String fieldName = !columnName.isEmpty()
                        ? columnName
                        : declaredField.getName();
                parameters.add(fieldName);
            }
        }
        result.append(parameters)
                .append(" FROM ")
                .append(tableName)
                .append(";");
        return result.toString();
    }

    @Override
    public String findById(Serializable id, Class<?> clazz) {
        StringBuilder result = new StringBuilder(findAll(clazz));
        result.setLength(result.length() - 1);
        String fieldName = "";
        for (Field declaredField : clazz.getDeclaredFields()) {
            if (declaredField.getAnnotation(Id.class) != null) {
                Column columnAnnotation = declaredField.getAnnotation(Column.class);
                if (columnAnnotation == null) {
                    throw new WrongAnnotationException("Annotation @Id without @Column");
                }
                String columnName = columnAnnotation.name();
                fieldName = !columnName.isEmpty()
                        ? columnName
                        : declaredField.getName();
                break;
            }
        }
        result.append(" WHERE ")
                .append(fieldName)
                .append(" = ")
                .append(id)
                .append(";");
        return result.toString();
    }

    @Override
    public String insert(Object value) throws IllegalAccessException {
        Class<?> clazz = value.getClass();
        String tableName = getTableName(clazz);
        StringJoiner fieldJoiner = new StringJoiner(", ", "(",")");
        StringJoiner valueJoiner = new StringJoiner("', '", "('", "')");

        StringBuilder result = new StringBuilder("INSERT INTO ");
        result.append(tableName);
        for (Field declaredField : clazz.getDeclaredFields()) {
            Column columnAnnotation = declaredField.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                String columnName = columnAnnotation.name();
                String fieldName = !columnName.isEmpty()
                        ? columnName
                        : declaredField.getName();
                fieldJoiner.add(fieldName);
                declaredField.setAccessible(true);
                valueJoiner.add(String.valueOf(declaredField.get(value)));
            }
        }
        result.append(fieldJoiner)
                .append(" VALUES ")
                .append(valueJoiner)
                .append(";");
        return result.toString();
    }

    @Override
    public String delete(Object value) {
        return null;
    }

    private String getTableName(Class<?> clazz) {
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        if (tableAnnotation == null) {
            throw new IllegalArgumentException(clazz.getSimpleName() + " is not entity");
        }
        String tableName = tableAnnotation.name();
        return !tableName.isEmpty() ? tableName : clazz.getSimpleName();
    }
}