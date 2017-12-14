package com.github.wiro34.hairspray.example;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PostRepository implements RepositoryOperation<Post>, Serializable {

    @PersistenceContext(unitName = "hairspray.example")
    private EntityManager entityManager;

    @Override
    public Optional<Post> find(Long id) {
        return Optional.ofNullable(entityManager.find(Post.class, id));
    }

    public List<Post> findBy(User user) {
        return entityManager
                .createQuery("select p from Post p where p.auther.id = :userId order by p.id", Post.class)
                .setParameter("userId", user.getId())
                .getResultList();
    }

    @Override
    public List<Post> all() {
        return entityManager
                .createQuery("select p from Post p order by p.id", Post.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void save(Post post) {
        if (entityManager.contains(post) || post.getId() > 0) {
            entityManager.merge(post);
        } else {
            entityManager.persist(post);
        }
    }
}
