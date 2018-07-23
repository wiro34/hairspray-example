package com.github.wiro34.hairspray.example.post;

import com.github.wiro34.hairspray.Hairspray;
import com.github.wiro34.hairspray.example.ds.user.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

public class PostServiceTest {
    @InjectMocks
    private PostService service;

    @Mock
    private PostRepository postRepository;

    private Hairspray factory = new Hairspray();

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateNewPost() {
        User user = factory.build(User.class);
        Post expected = factory.build(Post.class, post->{
            post.setAuthor(user);
            post.setSubject("My Post");
            post.setContent("it is content");
            post.setCreatedAt(null);
        });
        Post result = service.createNewPost(user, "My Post", "it is content");
        verify(postRepository, only()).save(expected);
        assertEquals(result, expected);
    }
}
