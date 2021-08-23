package com.androutils.livedata.cacheadapters;

import java.util.Map;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 23-07-2020
 */
public class MapCacheAdapter<K, V, Cache extends Map<K, V>> extends AbsCacheAdapter<K, V, Cache>{

    public MapCacheAdapter(Cache cache) {
        super(cache);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public V put(K key, V value) {
        return cache.put(key, value);
    }

    @Override
    public V remove(K key) {
        return cache.remove(key);
    }

    @Override
    public void evictAll() {
        cache.clear();
    }

    @Override
    public long size() {
        return cache.size();
    }
}
