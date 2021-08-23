package com.androutils.livedata.shouldusecache;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import com.androutils.livedata.cacheadapters.CacheAdapter;
import com.androutils.livedata.repository.LiveDataRequest;

import org.apache.commons.lang3.mutable.MutableLong;

import java.util.concurrent.TimeUnit;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 09-07-2020
 */
public final class ShouldUseCacheCollections {

    // Direct use of this not encouraged as at the minimum, cache should be
    // checked(so called default behavior)
    public static final ShouldUseCache TRUE = new ShouldUseCache() {
        @Override
        public boolean shouldUseCache() {
            return true;
        }
    };
    public static final ShouldUseCache FALSE = new ShouldUseCache() {
        @Override
        public boolean shouldUseCache() {
            return false;
        }
    };

    public static <T, Cache extends CacheAdapter<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>>> ShouldUseCacheBuilder<T, Cache> IS_NOT_NULL(Cache cache, LiveDataRequest<T> request){
        return ShouldUseCacheBuilder.defaults(cache, request)
                .and(new ShouldUseCacheNotNull<>(cache, request));
    }

    public static <T, Cache extends CacheAdapter<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>>> ShouldUseCacheBuilder<T, Cache> FIVE_MINUTES_ELAPSED(Cache cache, LiveDataRequest<T> request){
        return ShouldUseCacheBuilder.defaults(cache, request)
                .and(new ShouldUseCacheTimeBased<>(cache, request, 5, TimeUnit.MINUTES));
    }

    public static <T, Cache extends CacheAdapter<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>>> ShouldUseCacheBuilder<T, Cache> FIFTEEN_MINUTES_ELAPSED(Cache cache, LiveDataRequest<T> request){
        return ShouldUseCacheBuilder.defaults(cache, request)
                .and(new ShouldUseCacheTimeBased<>(cache, request, 15, TimeUnit.MINUTES));
    }

    public static <T, Cache extends CacheAdapter<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>>> ShouldUseCacheBuilder<T, Cache> TEN_SECONDS_ELAPSED(Cache cache, LiveDataRequest<T> request){
        return ShouldUseCacheBuilder.defaults(cache, request)
                .and(new ShouldUseCacheTimeBased<>(cache, request, 10, TimeUnit.SECONDS));
    }
}
