package com.akshit;

import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppConfiguration extends Configuration {
    @NotEmpty
    private String name;

    @NotNull
    private SwaggerBundleConfiguration swagger;

    @Valid
    @NotNull
    private DataSourceFactory database;

}
