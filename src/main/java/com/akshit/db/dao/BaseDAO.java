package com.akshit.db.dao;

import io.dropwizard.hibernate.AbstractDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.SessionFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public abstract class BaseDAO<T> extends AbstractDAO<T> {

    private final Class<T> entityClass;

    public BaseDAO(SessionFactory sessionFactory, Class<T> entityClass) {
        super(sessionFactory);
        this.entityClass = entityClass;
    }

    public T createEntity(T entity) {
        return persist(entity);
    }

    public Optional<T> findEntityById(Long id) {
        return Optional.of(get(id));
    }

    public List<T> findAllEntities() {
        CriteriaBuilder cb = currentSession().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root);
        return list(cq);
    }

    public T updateEntity(Long id, T entity) {
        try {
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

    public T deleteEntity(Long id) {
        T entity = get(id);
        if (entity == null) {
            throw new RuntimeException("Entity not found with ID: " + id);
        }
        currentSession().remove(entity);
        return entity;
    }
}
