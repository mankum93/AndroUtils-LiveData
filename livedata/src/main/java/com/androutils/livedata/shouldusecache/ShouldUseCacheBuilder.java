package com.androutils.livedata.shouldusecache;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import com.androutils.livedata.cacheadapters.CacheAdapter;
import com.androutils.livedata.repository.LiveDataRequest;

import org.apache.commons.lang3.mutable.MutableLong;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 09-07-2020
 */
public class ShouldUseCacheBuilder<T, Cache extends CacheAdapter<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>>> {

    private ShouldUseCacheComposite useCacheComposite;
    private LiveDataRequest<T> liveDataRequest;
    private boolean isBuilt;
    private Cache cache;

    private ShouldUseCacheBuilder(Cache cache, LiveDataRequest<T> request) {
        this.useCacheComposite = new ShouldUseCacheComposite();
        this.cache = cache;
        this.liveDataRequest = request;
    }

    public ShouldUseCache build() {
        isBuilt = true;
        return useCacheComposite;
    }

    public static <T, Cache extends CacheAdapter<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>>> ShouldUseCacheBuilder<T, Cache> raw(Cache cache, LiveDataRequest<T> request) {
        return new ShouldUseCacheBuilder<>(cache, request);
    }

    public static <T, Cache extends CacheAdapter<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>>> ShouldUseCacheBuilder<T, Cache> defaults(Cache cache, LiveDataRequest<T> request) {
        final ShouldUseCacheBuilder<T, Cache> builder = new ShouldUseCacheBuilder<>(cache, request);
        builder.useCacheComposite.and(new ShouldUseCacheDefault<>(cache, request));
        return builder;
    }

    public ShouldUseCacheBuilder<T, Cache> and(ShouldUseCache shouldUseCache) {
        assertNotBuilt();
        // Append the same to the end
        useCacheComposite.and(shouldUseCache);
        return this;
    }

    private void assertNotBuilt() {
        if (isBuilt) {
            throw new RuntimeException("ShouldUseCache already built!");
        }
    }
}
