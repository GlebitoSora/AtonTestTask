package org.example.service;

public interface InMemoryCache<K, V> {
    void addRecord(K key, V value);

    void removeRecord(K key);

    boolean updateRecord(K key, V value);

    V getRecord(K key);

}