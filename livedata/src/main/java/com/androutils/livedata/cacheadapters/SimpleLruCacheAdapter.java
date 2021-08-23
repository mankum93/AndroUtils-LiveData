package com.androutils.livedata.cacheadapters;

import androidx.collection.LruCache;
import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import com.androutils.livedata.repository.LiveDataRequest;

import org.apache.commons.lang3.mutable.MutableLong;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 22-07-2020
 */
public class SimpleLruCacheAdapter extends AbsLruCacheAdapter<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>, LruCache<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>>> {

    public SimpleLruCacheAdapter(LruCache<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>> cache) {
        super(cache);
    }
}
