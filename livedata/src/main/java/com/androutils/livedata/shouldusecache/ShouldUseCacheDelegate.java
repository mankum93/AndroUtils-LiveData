package com.androutils.livedata.shouldusecache;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import com.androutils.livedata.cacheadapters.CacheAdapter;
import com.androutils.livedata.repository.LiveDataRequest;

import org.apache.commons.lang3.mutable.MutableLong;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 28-06-2020
 */
public class ShouldUseCacheDelegate<Cache extends CacheAdapter<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>>> extends AbsShouldUseCache<Cache> {

    private ShouldUseCache shouldUseCache;

    public ShouldUseCacheDelegate(Cache cache, ShouldUseCache shouldUseCache) {
        super(cache);
        this.shouldUseCache = shouldUseCache;
    }

    @Override
    public boolean shouldUseCache() {
        return shouldUseCache.shouldUseCache();
    }
}
