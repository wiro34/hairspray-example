package com.github.wiro34.hairspray.example.ds.post;

import com.github.wiro34.hairspray.example.post.Post;
import com.github.wiro34.hairspray.example.post.PostRepository;
import com.github.wiro34.hairspray.example.ds.user.User;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class PostRepositoryImpl implements PostRepository {
    @PersistenceContext(unitName = "hairspray.example")
    private EntityManager entityManager;

    @Override
    public Optional<Post> find(Long id) {
        return Optional.ofNullable(entityManager.find(Post.class, id));
    }

    @Override
    public List<Post> findBy(User user) {
        return entityManager
                .createQuery("select p from Post p where p.author.id = :userId order by p.id", Post.class)
                .setParameter("userId", user.getId())
                .getResultList();
    }

    @Override
    @Transactional
    public void save(Post post) {
        entityManager.persist(post);
    }
}
