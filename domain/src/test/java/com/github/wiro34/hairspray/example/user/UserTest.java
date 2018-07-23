package com.github.wiro34.hairspray.example.user;

import com.github.wiro34.hairspray.Hairspray;
import com.github.wiro34.hairspray.example.ds.user.User;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class UserTest {

    private Hairspray factory = new Hairspray();

    @Test
    public void testGetFullName() throws Exception {
        User user = factory.build(User.class, (u) -> {
            u.setFirstName("Foo");
            u.setLastName("Bar");
        });
        assertEquals(user.getFullName(), "Foo Bar");
    }

}
