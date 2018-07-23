package com.github.wiro34.hairspray.example.ds.user;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext(unitName = "hairspray.example")
    private EntityManager entityManager;

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            return Optional.of(
                    entityManager.createQuery("select u from User u where u.email = :email", User.class)
                            .setParameter("email", email)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return entityManager
                .createQuery("select u from User u order by u.id", User.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }
}
