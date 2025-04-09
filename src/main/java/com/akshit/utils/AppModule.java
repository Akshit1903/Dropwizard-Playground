package com.akshit.utils;

import com.akshit.AppConfiguration;
import com.akshit.db.dao.PersonDAO;
import com.akshit.services.PersonService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.dropwizard.hibernate.HibernateBundle;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AppModule extends AbstractModule {
    private final HibernateBundle<AppConfiguration> hibernate;

    @Provides
    @Singleton
    public PersonService getPersonService(PersonDAO personDAO) {
        return new PersonService(personDAO);
    }

    @Provides
    @Singleton
    public PersonDAO getPersonDAO() {
        return new PersonDAO(hibernate.getSessionFactory());
    }

}
