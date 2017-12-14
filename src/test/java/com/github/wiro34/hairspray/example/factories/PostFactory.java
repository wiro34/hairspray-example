package com.github.wiro34.hairspray.example.factories;

import com.github.wiro34.hairspray.Hairspray;
import com.github.wiro34.hairspray.annotation.Factory;
import com.github.wiro34.hairspray.example.Post;
import com.github.wiro34.hairspray.example.User;
import com.github.wiro34.hairspray.example.UserRepository;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.function.Function;

@Factory(Post.class)
public class PostFactory {

    @Inject
    private Hairspray factory;

    @Inject
    private UserRepository userRepository;

    public Function<Post, User> auther = (post) -> userRepository.find(1L).orElse(factory.create(User.class));

    public String subject = "Example post";

    public String content = "it is content";

    public Function<Post, Timestamp> createdAt = (post) -> {
        Calendar c = Calendar.getInstance();
        c.set(2016, 1, 1, 0, 0, 0);
        return new Timestamp(c.getTimeInMillis());
    };
}
