package org.example.service;

import org.example.model.CacheItem;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCacheImpl<K, V> implements InMemoryCache<K, V> {
    private final long TIME_TO_LIVE = 50000L;
    private final Map<K, CacheItem<V>> cache = new HashMap<>();

    @Override
    public void addRecord(K key, V value) {
        cache.put(key, new CacheItem<>(value, TIME_TO_LIVE));
    }

    @Override
    public void removeRecord(K key) {
        cache.remove(key);
    }

    @Override
    public boolean updateRecord(K key, V value) {
        if (cache.containsKey(key)) {
            cache.put(key, new CacheItem<>(value, TIME_TO_LIVE));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public V getRecord(K key) {
        CacheItem<V> cacheItem = cache.get(key);
        if (cacheItem != null && !cacheItem.isExpired()) {
            return cacheItem.getObj();
        }
        cache.remove(key);
        return null;
    }

    public Map<K, CacheItem<V>> getCache() {
        return cache;
    }


}