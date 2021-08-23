package com.androutils.livedata.cacheadapters;

import com.google.common.collect.ForwardingObject;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 22-07-2020
 */
public abstract class AbsCacheAdapter<K, V, Cache> extends ForwardingObject implements CacheAdapter<K, V>{

    protected Cache cache;

    public AbsCacheAdapter(Cache cache) {
        this.cache = cache;
    }

    @Override
    public Cache delegate() {
        return cache;
    }
}
