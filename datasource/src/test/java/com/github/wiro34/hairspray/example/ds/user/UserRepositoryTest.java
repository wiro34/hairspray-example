package com.github.wiro34.hairspray.example.ds.user;

import com.github.wiro34.hairspray.Hairspray;
import com.github.wiro34.hairspray.example.ds.ArquillianTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class UserRepositoryTest extends ArquillianTest {

    @Inject
    private Hairspray factory;

    @Inject
    private UserRepository userRepository;

    private List<User> users;

    @Inject
    private EntityManager em;


    @BeforeMethod
    public void setUp() {
        if (isInContainer()) {
            final String[] names = new String[]{"John", "Tom", "Daniel"};
            users = factory.createList(User.class, 3, (u, i) -> {
                u.setFirstName(names[i]);
            });
        }
    }

    @DataProvider
    public Object[][] provideUserNames() {
        return new Object[][]{
                {"John", "Doe"},
                {"Tom", "Doe"},
                {"Daniel", "Doe"},
        };
    }

    @Test(dataProvider = "provideUserNames")
    public void testFindByEmail(String firstName, String lastName) {
        User user = userRepository.findByEmail(firstName + "." + lastName + "@example.com").get();
        assertEquals(user.getFirstName(), firstName);
        assertEquals(user.getLastName(), lastName);
    }

    @Test
    public void testFindAll() {
        List<User> all = userRepository.findAll();
        assertEquals(all.size(), 3);
        assertEquals(all, users);
    }

    @Test
    public void testSave() {
        User user = factory.build(User.class, u -> {
            u.setFirstName("Jack");
            u.setLastName("Daniel");
            u.setEmail("Jack.Daniel@foobar.com");
        });
        userRepository.save(user);
        assertTrue(userRepository.findByEmail("Jack.Daniel@foobar.com").isPresent());
    }

}
