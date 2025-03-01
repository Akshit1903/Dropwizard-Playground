package com.akshit.resources;

import com.akshit.models.SampleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jdk.jfr.Description;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Housekeeping APIs", description = "does housekeeping")
public class Resource {
    @GET
    @Description("Housekeeping API")
    @Operation(summary = "Say Hello")
    @Path("/abc")
    public SampleResponse getSampleResponse() {
        return SampleResponse.builder().name("Akshit").message("Hi").build();
    }
}
