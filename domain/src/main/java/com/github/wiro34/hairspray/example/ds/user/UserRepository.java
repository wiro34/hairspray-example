package com.github.wiro34.hairspray.example.ds.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    List<User> findAll();

    void save(User user);

}
