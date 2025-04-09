package com.akshit.db.dao;

import com.akshit.db.entities.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

@Slf4j
public class PersonDAO extends BaseDAO<PersonEntity> {
    public PersonDAO(SessionFactory sessionFactory) {
        super(sessionFactory, PersonEntity.class);
    }

}
