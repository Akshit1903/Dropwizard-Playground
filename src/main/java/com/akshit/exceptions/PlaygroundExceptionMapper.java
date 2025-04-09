package com.akshit.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class PlaygroundExceptionMapper implements ExceptionMapper<PlaygroundException> {
    @Override
    public Response toResponse(PlaygroundException exception) {
        return Response.status(exception.getStatus())
                .entity(exception)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
