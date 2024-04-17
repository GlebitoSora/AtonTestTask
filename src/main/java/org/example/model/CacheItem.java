package org.example.model;

public class CacheItem<T> {
    private final T obj;
    private final long timeToLive;

    public CacheItem(T obj, long timeToLive) {
        this.obj = obj;
        this.timeToLive = System.currentTimeMillis() + timeToLive;
    }

    public T getObj() {
        return obj;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > timeToLive;
    }
}
