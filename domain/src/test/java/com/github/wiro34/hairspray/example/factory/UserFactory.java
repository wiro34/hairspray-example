package com.github.wiro34.hairspray.example.factory;

import com.github.wiro34.hairspray.Dynamic;
import com.github.wiro34.hairspray.Lazy;
import com.github.wiro34.hairspray.annotation.Factory;
import com.github.wiro34.hairspray.example.ds.user.User;
import com.github.wiro34.hairspray.example.ds.user.User.Sex;

import java.sql.Timestamp;

@Factory(User.class)
public class UserFactory {

    public String firstName = "John";

    public String lastName = "Doe";

    public Integer age = 18;

    public Lazy<User, String> email = (user) -> user.getFirstName() + "." + user.getLastName() + "@example.com";

    public Dynamic<User, Sex> sex = (user) -> user.getFirstName().equals("Jane") ? Sex.FEMALE : Sex.MALE;

    public Dynamic<User, Timestamp> createdAt = (user) -> new Timestamp(System.currentTimeMillis());
}
