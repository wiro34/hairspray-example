package com.github.wiro34.hairspray.example;

import com.github.wiro34.hairspray.Hairspray;
import com.github.wiro34.hairspray.example.arquillian.ArquillianTest;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.testng.AssertJUnit.assertEquals;

public class PostTest extends ArquillianTest {

    @Inject
    private Hairspray factory;

    @Test
    public void testGetAuther() throws Exception {
        Post post = factory.create(Post.class);
        assertEquals(post.getAuther().getFirstName(), "John");
    }
}
