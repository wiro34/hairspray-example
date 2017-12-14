package com.github.wiro34.hairspray.example;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements RepositoryOperation<User>, Serializable {

    @PersistenceContext(unitName = "hairspray.example")
    private EntityManager entityManager;

    @Override
    public Optional<User> find(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> all() {
        return entityManager.createQuery("select u from User u order by u.id").getResultList();
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }
}
