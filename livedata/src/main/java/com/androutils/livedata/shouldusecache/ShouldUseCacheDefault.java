package com.androutils.livedata.shouldusecache;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import com.androutils.livedata.cacheadapters.CacheAdapter;
import com.androutils.livedata.repository.LiveDataRequest;

import org.apache.commons.lang3.mutable.MutableLong;

import timber.log.Timber;

/**
 * Default behavior: we check if the element is found in cache
 *
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 28-06-2020
 */
public class ShouldUseCacheDefault<T, Cache extends CacheAdapter<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>>> extends ShouldUseCacheBase<T, Cache> {

    public ShouldUseCacheDefault(Cache cache, ShouldUseCache shouldUseCache, LiveDataRequest<T> liveDataRequest) {
        super(cache, shouldUseCache, liveDataRequest);
    }

    public ShouldUseCacheDefault(Cache cache, LiveDataRequest<T> liveDataRequest) {
        super(cache, ShouldUseCacheCollections.TRUE, liveDataRequest);
    }

    /**
     * @return true, if the element is found in cache; false otherwise
     */
    @Override
    public boolean shouldUseCache() {
        boolean shouldUseCache = super.shouldUseCache();
        if(shouldUseCache){
            final Pair<LiveData<?>, MutableLong> value = cache.get(liveDataRequest);
            if(value == null || value.first == null){
                shouldUseCache = false;
            }
        }
        Timber.d("shoulduseCache = %s", shouldUseCache);
        return shouldUseCache;
    }
}
