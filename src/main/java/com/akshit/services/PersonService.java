package com.akshit.services;

import com.akshit.db.dao.PersonDAO;
import com.akshit.db.entities.PersonEntity;
import com.akshit.models.Person;
import com.akshit.utils.Utils;
import com.akshit.utils.exceptions.PlaygroundException;
import com.google.inject.Inject;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PersonService {
    private final PersonDAO personDAO;

    private <T> T executeWithExceptionHandling(Supplier<T> action, String errorMessage) {
        try {
            return action.get();
        } catch (Exception e) {
            throw new PlaygroundException(Response.Status.INTERNAL_SERVER_ERROR, errorMessage + ": " + e.getMessage());
        }
    }

    private PersonEntity toPersonEntity(Person person) {
        return Utils.map(person, PersonEntity.class);
    }

    private Person toPerson(PersonEntity personEntity) {
        return Utils.map(personEntity, Person.class);
    }


    public Person createPerson(Person person) {
        PersonEntity personEntity = executeWithExceptionHandling(() -> personDAO.createEntity(toPersonEntity(person)), "Error creating person");
        return toPerson(personEntity);
    }

    public Optional<PersonEntity> getPersonById(long id) {
        return executeWithExceptionHandling(() -> personDAO.findEntityById(id), "Error finding person by ID");
    }

    public List<Person> getAllPersons() {
        return executeWithExceptionHandling(() -> personDAO.findAllEntities().stream().map(this::toPerson).toList(), "Error retrieving all persons");
    }

    public Person updatePerson(long id, Person updatedPerson) {
        return toPerson(executeWithExceptionHandling(() -> personDAO.updateEntity(id, toPersonEntity(updatedPerson)), "Error updating person"));
    }

    public Person deletePerson(long id) {
        return toPerson(executeWithExceptionHandling(() -> personDAO.deleteEntity(id), "Error deleting person"));
    }

}
