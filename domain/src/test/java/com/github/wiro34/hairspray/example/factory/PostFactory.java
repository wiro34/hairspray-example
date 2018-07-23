package com.github.wiro34.hairspray.example.factory;

import com.github.wiro34.hairspray.Dynamic;
import com.github.wiro34.hairspray.Hairspray;
import com.github.wiro34.hairspray.annotation.Factory;
import com.github.wiro34.hairspray.example.ds.user.User;
import com.github.wiro34.hairspray.example.ds.user.UserRepository;
import com.github.wiro34.hairspray.example.post.Post;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Objects;

@Factory(Post.class)
public class PostFactory {

    @Inject
    private Hairspray factory = new Hairspray();

    @Inject
    private UserRepository userRepository;

    public Dynamic<Post, User> author = this::associateUser;

    public String subject = "Example post";

    public String content = "it is content";

    public Dynamic<Post, Timestamp> createdAt = (post) -> {
        Calendar c = Calendar.getInstance();
        c.set(2016, 1, 1, 0, 0, 0);
        return new Timestamp(c.getTimeInMillis());
    };

    private User associateUser(Post post) {
        if (Objects.nonNull(userRepository)) {
            return userRepository.findAll().stream().findFirst().orElse(factory.build(User.class));
        } else {
            return factory.build(User.class);
        }
    }
}
