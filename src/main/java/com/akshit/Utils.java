package com.akshit;

import com.akshit.exceptions.PlaygroundException;
import jakarta.ws.rs.core.Response;

import java.lang.reflect.Field;

public class Utils {
    public static <S, T> T map(S source, Class<T> targetClass) {
        if (source == null) return null;

        try {
            T target = targetClass.getDeclaredConstructor().newInstance();

            Field[] sourceFields = source.getClass().getDeclaredFields();
            Field[] targetFields = targetClass.getDeclaredFields();

            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                for (Field targetField : targetFields) {
                    if (targetField.getName().equals(sourceField.getName()) &&
                            targetField.getType().equals(sourceField.getType())) {

                        targetField.setAccessible(true);
                        targetField.set(target, sourceField.get(source));
                    }
                }
            }
            return target;
        } catch (Exception e) {
            throw new PlaygroundException(Response.Status.INTERNAL_SERVER_ERROR, String.format("Failed to map objects from %s to %s: %s", source.getClass(), targetClass, e.getMessage()));
        }
    }
}
