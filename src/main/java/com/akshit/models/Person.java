package com.akshit.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Person {
    private Long id;
    private String name;
    private String age;
}
