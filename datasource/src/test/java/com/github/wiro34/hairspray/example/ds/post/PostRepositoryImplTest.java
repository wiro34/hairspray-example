package com.github.wiro34.hairspray.example.ds.post;

import com.github.wiro34.hairspray.Hairspray;
import com.github.wiro34.hairspray.example.ds.ArquillianTest;
import com.github.wiro34.hairspray.example.ds.user.User;
import com.github.wiro34.hairspray.example.post.Post;
import com.github.wiro34.hairspray.example.post.PostRepository;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.testng.AssertJUnit.assertEquals;

@Dependent
public class PostRepositoryImplTest extends ArquillianTest {

    @Inject
    private Hairspray factory;

    @Inject
    private PostRepository postRepository;

    private List<User> users;

    private List<Post> posts;

    @BeforeMethod
    public void setUp() {
        if (isInContainer()) {
            users = factory.createList(User.class, 2);
            posts = factory.createList(Post.class, 3, (post, i) -> {
                post.setAuthor(users.get(0));
                post.setSubject("Test Post #" + (i + 1));
            });
        }
    }

    @Test
    public void testFind() {
        assertEquals(postRepository.find(posts.get(2).getId()), Optional.of(posts.get(2)));
    }

    @Test
    public void testFindIfNotFound() {
        assertEquals(postRepository.find(posts.get(2).getId() + 1), Optional.empty());
    }

    @DataProvider
    public Object[][] provideDataForFindBy() {
        return new Object[][]{
                {0, 3},
                {1, 0}
        };
    }

    @Test(dataProvider = "provideDataForFindBy")
    public void testFindBy(int userIndex, int size) {
        User user = users.get(userIndex);
        List<Post> founds = postRepository.findBy(user);
        assertEquals(founds.size(), size);
        founds.forEach((p) -> assertEquals(p.getAuthor(), user));
    }

    @Test
    public void testSave() {
        User user = users.get(1);
        Post post = factory.build(Post.class, p -> {
            p.setAuthor(user);
            p.setSubject("A New Post");
        });
        postRepository.save(post);
        Post saved = postRepository.find(post.getId()).get();
        assertEquals(saved.getSubject(), "A New Post");
        assertEquals(saved.getAuthor(), user);
        assertEquals(postRepository.findBy(user).size(), 1);
    }

}
