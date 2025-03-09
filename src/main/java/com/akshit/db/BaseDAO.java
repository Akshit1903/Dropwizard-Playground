package com.akshit.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import java.lang.reflect.Field;

public abstract class BaseDAO<T> extends AbstractDAO<T> {

    private final Class<T> entityClass;

    public BaseDAO(SessionFactory sessionFactory, Class<T> entityClass) {
        super(sessionFactory);
        this.entityClass = entityClass;
    }

    public T updateEntity(T entity, Long id) {
        try {
            // Fetch the existing entity from DB
            T existingEntity = get(id);
            if (existingEntity == null) {
                throw new RuntimeException("Entity not found with ID: " + id);
            }

            // Use reflection to update only non-null fields
            for (Field field : entityClass.getDeclaredFields()) {
                field.setAccessible(true);
                Object newValue = field.get(entity);
                if (newValue != null) { // ✅ Only update if new value is present
                    field.set(existingEntity, newValue);
                }
            }

            return persist(existingEntity); // ✅ Save the updated entity
        } catch (Exception e) {
            throw new RuntimeException("Error updating entity: " + e.getMessage(), e);
        }
    }
}
