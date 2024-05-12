package org.example;

import org.example.model.User;
import org.example.service.InMemoryStorage;

import java.lang.reflect.InvocationTargetException;

public class Main{
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("Start");
        User user = new User(1L, "Gleb", 43456.63);
        User user1 = new User(2L, "Shaliko", 4354.7);
        User user2 = new User(3L, "Anton", 2193.7);
        InMemoryStorage<User> storage = new InMemoryStorage<>(User.class);
        storage.add(user);
        storage.add(user1);
        storage.add(user2);
        System.out.println("Get by account = 1: " + storage.getByUniqueField("account", "1"));
        System.out.println("Get by name = Gleb: " + storage.getByNonUniqueField("name", "Gleb"));
        System.out.println("Get by value =2193.7: " + storage.getByNonUniqueField("value", "2193.7"));
        System.out.println();
    }
}