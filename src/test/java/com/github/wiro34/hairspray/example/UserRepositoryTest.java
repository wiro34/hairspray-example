package com.github.wiro34.hairspray.example;

import com.github.wiro34.hairspray.Hairspray;
import com.github.wiro34.hairspray.example.arquillian.ArquillianTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

public class UserRepositoryTest extends ArquillianTest {

    @Inject
    private Hairspray factory;

    @Inject
    private UserRepository userRepository;

    private List<User> users;

    @BeforeMethod
    public void setUp() {
        if (isInContainer()) {
            users = factory.createList(User.class, 3);
            users.get(0).setFirstName("John");
            users.get(1).setFirstName("Tom");
            users.get(2).setFirstName("Daniel");
        }
    }

    @Test
    public void testFind() {
        assertEquals(userRepository.find(users.get(1).getId()).get().getFirstName(), "Tom");
        assertFalse(userRepository.find(users.get(2).getId() + 1).isPresent());
    }

    @Test
    public void testAll() {
        assertEquals(userRepository.all(), users);
    }

    @Test
    public void testSave() {
        User user = users.get(2);
        assertEquals(userRepository.find(user.getId()).get().getFirstName(), "Daniel");
        user.setFirstName("Mickel");
        userRepository.save(user);
        assertEquals(userRepository.find(user.getId()).get().getFirstName(), "Mickel");
    }

    @Test
    public void testFirst() {
        assertEquals(userRepository.first().get(), users.get(0));
    }

    @Test
    public void testLast() {
        assertEquals(userRepository.last().get(), users.get(2));
    }
}
