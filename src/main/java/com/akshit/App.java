package com.akshit;

import com.akshit.db.PersonEntity;
import com.akshit.exceptions.PlaygroundExceptionMapper;
import com.akshit.resources.Resource;
import com.akshit.services.PersonService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.inject.Stage;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.hibernate.SessionFactory;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class App extends Application<AppConfiguration> {
    private final SwaggerBundle<AppConfiguration> swaggerBundle = new SwaggerBundle<AppConfiguration>() {
        @Override
        protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AppConfiguration configuration) {
            return configuration.getSwagger();
        }
    };
    private final HibernateBundle<AppConfiguration> hibernate = new HibernateBundle<AppConfiguration>(
            PersonEntity.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(AppConfiguration configuration) {
            return configuration.getDatabase();
        }
    };


    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        super.initialize(bootstrap);
        final GuiceBundle guiceBundle = GuiceBundle.builder()
                .enableAutoConfig(getClass().getPackage().getName()) // scans your project package
                .modules(new AppModule(hibernate)) // your custom Guice module
                .build();

        bootstrap.addBundle(swaggerBundle);
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(AppConfiguration appConfiguration, Environment environment) throws Exception {
        environment.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        environment.jersey().register(new PlaygroundExceptionMapper());
        TemplateHealthCheck healthCheck = new TemplateHealthCheck("%s");
        environment.healthChecks().register("template", healthCheck);
    }
}
