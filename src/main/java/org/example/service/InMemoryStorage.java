package org.example.service;

import org.example.annotation.DatabaseField;
import org.example.annotation.FieldType;
import org.example.utils.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class InMemoryStorage<T> {
    private final Map<String, Map<String, T>> uniqueFieldMap = new HashMap<>();

    private final Map<String, Map<String, List<T>>> nonUniqueFieldMap = new HashMap<>();

    private final Set<String> uniqueFieldsNames = new HashSet<>();

    private final Map<Field, Method> fieldMethodMap = new HashMap<>();

    private final Class<T> type;

    public InMemoryStorage(Class<T> type) {
        this.type = type;
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation a : annotations) {
                // проверям наличие аннтоации над полем
                if (a instanceof DatabaseField) {
                    // получаем геттер для конкретного поля класса
                    fieldMethodMap.put(field, ReflectionUtils.getterForField(field, type));
                    // проверяем уникальность поля
                    if (((DatabaseField) a).type() == FieldType.UNIQUE) {
                        uniqueFieldsNames.add(field.getName());
                        uniqueFieldMap.put(field.getName(), new HashMap<>());
                    } else if (((DatabaseField) a).type() == FieldType.NON_UNIQUE) {
                        nonUniqueFieldMap.put(field.getName(), new HashMap<>());
                    }
                }
            }
        }
    }

    public void add(T value) throws InvocationTargetException, IllegalAccessException {
        Objects.requireNonNull(value);
        for (Field field : type.getDeclaredFields()) {
            // достаем значение поля
            String fieldValue = String.valueOf(fieldMethodMap.get(field).invoke(value));
            // кладем его в хранилище
            if (uniqueFieldsNames.contains(field.getName())) {
                uniqueFieldMap.get(field.getName()).put(fieldValue, value);
            } else {
                if (!nonUniqueFieldMap.get(field.getName()).containsKey(fieldValue)) {
                    nonUniqueFieldMap.get(field.getName()).put(fieldValue, new ArrayList<>());
                }
                nonUniqueFieldMap.get(field.getName()).get(fieldValue).add(value);
            }
        }
    }

    public T getByUniqueField(String field, String value) {
        Objects.requireNonNull(field);
        Objects.requireNonNull(value);
        // здесь можно добавить копирование обьекта, чтобы его не могли изменить после получения
        if (!uniqueFieldMap.containsKey(field)) {
            throw new RuntimeException("Field not found");
        }
        return uniqueFieldMap.get(field).get(value);
    }

    public List<T> getByNonUniqueField(String field, String value) {
        Objects.requireNonNull(field);
        Objects.requireNonNull(value);
        // здесь тоже можно добавить копирование всех обьектов, чтобы их не могли изменять после получения
        if (!nonUniqueFieldMap.containsKey(field)) {
            throw new RuntimeException("Field not found");
        }
        if (nonUniqueFieldMap.get(field).get(value) != null) {
            return new ArrayList<>(nonUniqueFieldMap.get(field).get(value));
        }
        return Collections.emptyList();
    }

    public void removeByField(String field, String value) {
        Objects.requireNonNull(field);
        Objects.requireNonNull(value);
        if (!uniqueFieldMap.containsKey(field) && !nonUniqueFieldMap.containsKey(field)) {
            throw new RuntimeException("Field not found");
        }
        if (uniqueFieldMap.containsKey(field)) {
            uniqueFieldMap.get(field).remove(value);
        } else {
            nonUniqueFieldMap.get(field).remove(value);
        }
    }

    public void remove(T value) throws InvocationTargetException, IllegalAccessException {
        Objects.requireNonNull(value);
        for (Field field : type.getDeclaredFields()) {
            String fieldValue = String.valueOf(fieldMethodMap.get(field).invoke(value));
            // сюда бы прикрутить поиск по хешкоду и удаление по нему конкретного инстанса, а не снос всего списка
            if (uniqueFieldsNames.contains(field.getName())) {
                uniqueFieldMap.get(field.getName()).remove(fieldValue);
            } else {
                nonUniqueFieldMap.get(field.getName()).remove(fieldValue);
            }
        }
    }
}