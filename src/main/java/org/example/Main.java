package org.example;

import org.example.model.User;
import org.example.service.InMemoryCacheImpl;

public class Main {
    public static void main(String[] args) {
        User user = new User(234678L, "Stolyarov Gleb", 2035.34);

        InMemoryCacheImpl<Long, User> inMemoryCache = new InMemoryCacheImpl<>();
        inMemoryCache.addRecord(user.getAccount(), user);
        System.out.println(inMemoryCache.getRecord(user.getAccount()));
        inMemoryCache.removeRecord(user.getAccount());
        System.out.println(inMemoryCache.getCache());

        User user1 = new User(234645L, "Shaliko Salimov", 2023.34);

        InMemoryCacheImpl<String, User> inMemoryCache1 = new InMemoryCacheImpl<>();
        inMemoryCache1.addRecord(user1.getName(), user1);
        System.out.println(inMemoryCache1.getRecord(user1.getName()));
        inMemoryCache1.removeRecord(user1.getName());
        System.out.println(inMemoryCache1.getCache());

        User user2 = new User(234334L, "Semenov Anton", 201.34);

        InMemoryCacheImpl<Double, User> inMemoryCache2 = new InMemoryCacheImpl<>();
        inMemoryCache2.addRecord(user2.getValue(), user2);
        System.out.println(inMemoryCache2.getRecord(user2.getValue()));
        inMemoryCache2.removeRecord(user2.getValue());
        System.out.println(inMemoryCache2.getCache());
    }
}
