package com.androutils.livedata.shouldusecache;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import com.androutils.livedata.cacheadapters.CacheAdapter;
import com.androutils.livedata.repository.LiveDataRequest;

import org.apache.commons.lang3.mutable.MutableLong;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 22-07-2020
 */
public abstract class AbsShouldUseCache<Cache extends CacheAdapter<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>>> implements ShouldUseCache{

    protected Cache cache;

    public AbsShouldUseCache(Cache cache) {
        this.cache = cache;
    }
}
