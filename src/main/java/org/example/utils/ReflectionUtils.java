package org.example.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {
    public ReflectionUtils() {
        throw new IllegalStateException("Is utility class. No need to create instance");
    }

    public static Method[] gettersForFields(Field[] fields, Class<?> type) throws NoSuchMethodException {
        Method[] methods = new Method[fields.length];
        for (int i = 0; i < fields.length; i++) {
            methods[i] = type.getMethod(resolveGetterMethodName(fields[i]), fields[i].getType());
        }
        return methods;
    }

    public static Method getterForField(Field field, Class<?> type) {
        try {
            return type.getMethod(resolveGetterMethodName(field));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static String resolveGetterMethodName(Field field) {
        char firstChar = field.getName().charAt(0);
        return "get" + field.getName().replaceFirst(Character.toString(firstChar), Character.toString(firstChar).toUpperCase());
    }
}
