package com.github.wiro34.hairspray.example;

import com.github.wiro34.hairspray.Hairspray;
import com.github.wiro34.hairspray.example.arquillian.ArquillianTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

@Dependent
public class PostRepositoryTest extends ArquillianTest {

    @Inject
    private Hairspray factory;

    @Inject
    private PostRepository postRepository;

    private List<Post> posts;

    @BeforeMethod
    public void setUp() {
        if (isInContainer()) {
            posts = factory.createList(Post.class, 3);
            posts.get(0).setSubject("Test Post #1");
            posts.get(1).setSubject("Test Post #2");
            posts.get(2).setSubject("Test Post #3");
            posts.get(2).setAuther(posts.get(0).getAuther());
            postRepository.save(posts.get(2));
        }
    }

    @Test
    public void testFind() {
        assertEquals(postRepository.find(posts.get(2).getId()).get().getSubject(), "Test Post #3");
        assertFalse(postRepository.find(posts.get(2).getId() + 1).isPresent());
    }

    @Test
    public void testFindBy() {
        User user = posts.get(0).getAuther();
        List<Post> founds = postRepository.findBy(user);
        assertEquals(founds.size(), 2);
        founds.stream().forEach((p) -> {
            assertEquals(p.getAuther(), user);
        });
    }

    @Test
    public void testAll() {
        assertEquals(3, postRepository.all().size());
        assertEquals(posts.get(2), postRepository.all().get(2));
    }

    @Test
    public void testSave() {
        Post post = posts.get(2);
        assertEquals(postRepository.find(post.getId()).get().getSubject(), "Test Post #3");
        post.setSubject("Foo");
        postRepository.save(post);
        assertEquals(postRepository.find(post.getId()).get().getSubject(), "Foo");
    }

    @Test
    public void testFirst() {
        assertEquals(postRepository.first().get(), posts.get(0));
    }

    @Test
    public void testLast() {
        assertEquals(postRepository.last().get(), posts.get(2));
    }
}
