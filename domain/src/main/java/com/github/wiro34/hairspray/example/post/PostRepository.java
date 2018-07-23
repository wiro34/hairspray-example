package com.github.wiro34.hairspray.example.post;

import com.github.wiro34.hairspray.example.ds.user.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Optional<Post> find(Long id);

    List<Post> findBy(User user);

    void save(Post post);

}
