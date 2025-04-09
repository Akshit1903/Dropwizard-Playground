package com.akshit.resources;

import com.akshit.db.PersonDAO;
import com.akshit.db.PersonEntity;
import com.akshit.models.Person;
import com.akshit.models.SampleResponse;
import com.akshit.services.PersonService;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jdk.jfr.Description;
import org.hibernate.SessionFactory;

import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Housekeeping APIs", description = "does housekeeping")
public class Resource {
    PersonService personService;

    public Resource(SessionFactory sessionFactory) {
        personService = new PersonService(sessionFactory);
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
    public List<Person> getPersons() {
        return personService.getAllPersons();
    }

    @GET
    @Description("Get person by id")
    @Operation(summary = "Get person by id")
    @Path("/person/{id}")
    @UnitOfWork
    public PersonEntity getPerson(@PathParam("id") Long id) {
        return personService.getPersonById(id).orElse(null);
    }

    @POST
    @Description("Create person")
    @Operation(summary = "Create person")
    @Path("/person")
    @UnitOfWork
    public Person createPerson(Person person) {
        return personService.createPerson(person);
    }

    @PUT
    @Description("Update person")
    @Operation(summary = "Update person")
    @Path("/person/{id}")
    @UnitOfWork
    public Person updatePerson(@PathParam("id") Long id, Person person) {
        return personService.updatePerson(id, person);
    }

    @DELETE
    @Description("Delete person")
    @Operation(summary = "Delete person by id")
    @Path("/person/{id}")
    @UnitOfWork
    public Person deletePerson(@PathParam("id") Long id) {
        return personService.deletePerson(id);
    }
}
