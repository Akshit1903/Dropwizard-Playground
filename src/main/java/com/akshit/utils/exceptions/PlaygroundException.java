package com.akshit.utils.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from response
public class PlaygroundException extends RuntimeException {
    private final String message;
    private final Response.Status status;

    public PlaygroundException(Response.Status status, String message) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
