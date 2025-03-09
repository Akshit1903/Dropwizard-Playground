package com.akshit.db;

import com.akshit.exceptions.PlaygroundException;
import io.dropwizard.hibernate.AbstractDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.ws.rs.core.Response;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.function.Supplier;

public class PersonDAO extends BaseDAO<PersonEntity> {
    public PersonDAO(SessionFactory sessionFactory) {
        super(sessionFactory,PersonEntity.class);
    }

    private <T> T executeWithExceptionHandling(Supplier<T> action, String errorMessage) {
        try {
            return action.get();
        } catch (Exception e) {
            throw new PlaygroundException(Response.Status.INTERNAL_SERVER_ERROR, errorMessage + ": " + e.getMessage());
        }
    }

    public PersonEntity findPersonById(Long id) {
        return executeWithExceptionHandling(() -> get(id), "Error finding person by ID");
    }

    public List<PersonEntity> findAllPersons() {
        return executeWithExceptionHandling(() -> {
            CriteriaBuilder cb = currentSession().getCriteriaBuilder();
            CriteriaQuery<PersonEntity> cq = cb.createQuery(PersonEntity.class);
            Root<PersonEntity> root = cq.from(PersonEntity.class);
            cq.select(root);
            return list(cq);
        }, "Error retrieving all persons");
    }

    public PersonEntity createPerson(PersonEntity entity) {
        return executeWithExceptionHandling(() -> persist(entity), "Error creating person");
    }

    public PersonEntity deletePerson(PersonEntity entity) {
        return executeWithExceptionHandling(() -> {
            currentSession().remove(entity);
            return entity;
        }, "Error deleting person");
    }

    public PersonEntity deletePerson(Long id) {
        return executeWithExceptionHandling(() -> {
            PersonEntity entity = get(id);
            if (entity == null) {
                throw new PlaygroundException(Response.Status.NOT_FOUND, "Person not found with ID: " + id);
            }
            currentSession().remove(entity);
            return entity;
        }, "Error deleting person");
    }

}
