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
public abstract class ShouldUseCacheBase<T, Cache extends CacheAdapter<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>>> extends ShouldUseCacheDelegate<Cache> {

    /**
     * The request to get the observable, i.e, the {@link LiveData}
     */
    protected final LiveDataRequest<T> liveDataRequest;

    public ShouldUseCacheBase(Cache cache, ShouldUseCache shouldUseCache, LiveDataRequest<T> liveDataRequest) {
        super(cache, shouldUseCache);
        this.liveDataRequest = liveDataRequest;
    }
}
