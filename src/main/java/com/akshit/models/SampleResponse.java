package com.akshit.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SampleResponse {
    private String name;
    private String message;
}
