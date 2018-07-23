package com.github.wiro34.hairspray.example.ds;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProducer {
    @Produces
    @PersistenceContext(unitName = "hairspray.example")
    private EntityManager entityManager;
}
