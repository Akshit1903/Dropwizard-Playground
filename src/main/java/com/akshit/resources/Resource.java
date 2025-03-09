package com.akshit.resources;

import com.akshit.db.PersonDAO;
import com.akshit.db.PersonEntity;
import com.akshit.exceptions.PlaygroundException;
import com.akshit.models.Person;
import com.akshit.models.SampleResponse;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jdk.jfr.Description;

import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Housekeeping APIs", description = "does housekeeping")
public class Resource {
    PersonDAO personDAO;
    public Resource(PersonDAO personDAO){
        this.personDAO=personDAO;
    }
    @GET
    @Description("Housekeeping API")
    @Operation(summary = "Say Hello")
    @Path("/abc")
    public SampleResponse getSampleResponse() {
        return SampleResponse.builder().name("Akshit5").message("Hi").build();
    }

    @GET
    @Description("Get all persons")
    @Operation(summary = "Get all persons")
    @Path("/persons")
    @UnitOfWork
    public List<PersonEntity> getPersons() {
        return personDAO.findAllPersons();
    }

    @GET
    @Description("Get person by id")
    @Operation(summary = "Get person by id")
    @Path("/person/{id}")
    @UnitOfWork
    public PersonEntity getPerson(@PathParam("id") Long id) {
        return personDAO.findPersonById(id);
    }

    @POST
    @Description("Create person")
    @Operation(summary = "Create person")
    @Path("/person")
    @UnitOfWork
    public PersonEntity createPerson(Person person) {
        PersonEntity personEntity=PersonEntity.builder().name(person.getName()).age(person.getAge()).build();
        return personDAO.createPerson(personEntity);
    }

    @PUT
    @Description("Update person")
    @Operation(summary = "Update person")
    @Path("/person")
    @UnitOfWork
    public PersonEntity updatePerson(Person person) {
        PersonEntity personEntity=PersonEntity.builder().id(person.getId()).name(person.getName()).age(person.getAge()).build();
        return personDAO.updateEntity(personEntity,personEntity.getId());
    }

    @DELETE
    @Description("Delete person")
    @Operation(summary = "Delete person by id")
    @Path("/person/{id}")
    @UnitOfWork
    public PersonEntity deletePerson(@PathParam("id") Long id) {
        return personDAO.deletePerson(id);
    }
}
