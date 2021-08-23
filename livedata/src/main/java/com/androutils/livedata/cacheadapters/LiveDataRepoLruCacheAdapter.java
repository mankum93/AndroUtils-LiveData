package com.androutils.livedata.cacheadapters;

import androidx.collection.LruCache;
import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import com.androutils.livedata.repository.LiveDataRequest;

import org.apache.commons.lang3.mutable.MutableLong;

import timber.log.Timber;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 22-07-2020
 */
public class LiveDataRepoLruCacheAdapter extends SimpleLruCacheAdapter{

    public LiveDataRepoLruCacheAdapter(LruCache<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>> cache) {
        super(cache);
    }

    /**
     * This method provides time when a "fresh" request was made that didn't allow a cached version to be employed
     *
     * @param key The <code>LiveDataRequest</code>
     * @return The last request time; -1 if the request didn't exist or if a valid request time wasn't
     * registered
     */
    public synchronized long getLastRequestTime(LiveDataRequest<?> key){
        final Pair<LiveData<?>, MutableLong> value = super.get(key);
        if(value != null && value.second != null){
            return value.second.longValue();
        }
        return -1;
    }

    public synchronized LiveData<?> getLiveData(LiveDataRequest<?> request){
        final Pair<LiveData<?>, MutableLong> liveDataFromCache = super.get(request);
        if(liveDataFromCache != null){
            Timber.d( "Cached version found = true");
            return liveDataFromCache.first;
        }
        else {
            Timber.d( "Cached version found = false");
        }
        return null;
    }

    @Override
    public Pair<LiveData<?>, MutableLong> remove(LiveDataRequest<?> key) {
        final Pair<LiveData<?>, MutableLong> liveDataFromCache = super.remove(key);
        if(liveDataFromCache != null){
            Timber.d( "Invalidated Request previously existed");
        }
        else {
            Timber.d( "Invalidated Request previously didn't exist!");
        }
        return liveDataFromCache;
    }

    @Override
    public void evictAll() {
        Timber.d( "Evicting all...");
        super.evictAll();
    }
}
