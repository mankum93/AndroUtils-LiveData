package com.androutils.livedata.shouldusecache;

import android.os.SystemClock;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import com.androutils.livedata.cacheadapters.CacheAdapter;
import com.androutils.livedata.repository.LiveDataRequest;

import org.apache.commons.lang3.mutable.MutableLong;

import java.util.concurrent.TimeUnit;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 22-06-2020
 */
public class ShouldUseCacheTimeBased<T, Cache extends CacheAdapter<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>>> extends ShouldUseCacheBase<T, Cache> {

    private long timeDifference;

    public ShouldUseCacheTimeBased(Cache cache,
                                   LiveDataRequest<T> liveDataRequest,
                                   long timeDifference,
                                   TimeUnit timeUnit,
                                   ShouldUseCache shouldUseCache) {
        super(cache, shouldUseCache, liveDataRequest);
        this.timeDifference = timeDifference;
    }

    public ShouldUseCacheTimeBased(Cache cache,
                                   LiveDataRequest<T> liveDataRequest,
                                   long timeDifference,
                                   ShouldUseCache shouldUseCache) {
        this(cache, liveDataRequest, timeDifference, TimeUnit.MILLISECONDS, shouldUseCache);
    }

    public ShouldUseCacheTimeBased(Cache cache,
                                   LiveDataRequest<T> liveDataRequest, long timeDifference) {
        this(cache, liveDataRequest, timeDifference, TimeUnit.MILLISECONDS, ShouldUseCacheCollections.TRUE);
    }

    public ShouldUseCacheTimeBased(Cache cache,
                                   LiveDataRequest<T> liveDataRequest, long timeDifference, TimeUnit timeUnit) {
        this(cache, liveDataRequest, timeDifference, timeUnit, ShouldUseCacheCollections.TRUE);
    }

    @Override
    public boolean shouldUseCache() {
        boolean shouldUseCache = super.shouldUseCache();
        if(shouldUseCache){
            final long lastRequestTime = cache.get(liveDataRequest).second.longValue();
            if (SystemClock.elapsedRealtime() - lastRequestTime > timeDifference) {
                shouldUseCache = false;
            }
        }
        return shouldUseCache;
    }

}
