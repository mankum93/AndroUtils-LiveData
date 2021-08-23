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
public class ShouldUseCacheNotNull<T, Cache extends CacheAdapter<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>>> extends ShouldUseCacheBase<T, Cache> {

    public ShouldUseCacheNotNull(Cache cache, ShouldUseCache shouldUseCache, LiveDataRequest<T> liveDataRequest) {
        super(cache, shouldUseCache, liveDataRequest);
    }

    public ShouldUseCacheNotNull(Cache cache, LiveDataRequest<T> liveDataRequest) {
        super(cache, ShouldUseCacheCollections.TRUE, liveDataRequest);
    }

    @Override
    public boolean shouldUseCache() {
        boolean shouldUseCache = super.shouldUseCache();
        if(shouldUseCache){
            final Pair<LiveData<?>, MutableLong> value = cache.get(liveDataRequest);
            if(value.first.getValue() == null){
                shouldUseCache = false;
            }
        }
        return shouldUseCache;
    }
}
