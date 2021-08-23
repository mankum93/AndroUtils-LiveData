package com.androutils.livedata.repository;

import android.os.Looper;
import android.os.SystemClock;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androutils.livedata.cacheadapters.CacheAdapter;
import com.androutils.livedata.shouldusecache.ShouldUseCache;

import org.apache.commons.lang3.mutable.MutableLong;

import timber.log.Timber;

/**
 * The base repository for all cached concept repositories. Don't forget to insert your custom
 * cache implementation;)
 *
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 07-07-2020
 */
public abstract class LiveDataRepository<Cache extends CacheAdapter<LiveDataRequest<?>, Pair<LiveData<?>, MutableLong>>> {

    /**
     * Use it to construct a String {@link LiveDataRequest<String>}, i.e,
     *
     * </p>
     * Suppose, we want to retrieve the cached GCM token from our repository. We could construct
     * a request as follows:
     * <code>
     * String REQUEST_KEY_GCM_TOKEN = LiveDataRepository.REQUEST_KEY_PREFIX_LIVE_DATA + ".gcm_helper";
     * LiveDataRequest<String> GCM_TOKEN_REQUEST = new LiveDataRequest<>(REQUEST_KEY_GCM_TOKEN);
     * </code>
     */
    public static final String REQUEST_KEY_PREFIX_LIVE_DATA = "request_key";

    /**
     * Workhorse of the cached concept LiveData - this Cache maps specifically the
     * {@link LiveData} observables w.r.t a request object.
     * <p>
     * By default, the Cache provisions for timing the {@link LiveData} requests
     * <p>
     * CACHE USE REQUIREMENT
     * ---------------------
     * For the Cache to work,
     * <ul>
     *     <li>- the request object must provide a proper implementation of {@link Object#equals(Object)} and
     *     {@link Object#hashCode()} methods</li>
     *     <li>- the request time stored must be in the format of a elapsed real time, such as, {@link SystemClock#elapsedRealtime()}</li>
     * </ul>
     */
    protected Cache cache;

    protected LiveDataRepository(Cache cache) {
        this.cache = cache;
    }

    protected Cache getCache() {
        return cache;
    }

    protected static abstract class MyLiveDataRunnable<T> implements Runnable {

        private MutableLiveData<T> liveData;

        public MutableLiveData<T> getPreparedLiveDataInstance() {
            return liveData;
        }

        public void setLiveData(MutableLiveData<T> liveData) {
            this.liveData = liveData;
        }
    }

    public <T, U> MutableLiveData<T> prepareLiveData(LiveDataRequest<U> request,
                                                     ShouldUseCache shouldUseCache,
                                                     MyLiveDataRunnable<T> liveDataRunnable) {
        // TODO(Manish): Assertion
        //  Comment out for release env.
        assertOverridesEqualsAndHashCodeMethods(request);

        synchronized (this) {
            // Determine ShouldUseCache once and only once
            boolean shouldUseCacheDetermined = shouldUseCache.shouldUseCache();
            MutableLiveData<T> data;
            if (shouldUseCacheDetermined) {
                Timber.d("LiveData requested from cache...");
                data = (MutableLiveData<T>) cache.get(request).first;
                if (data == null) {
                    throw new RuntimeException("No default behavior of checking cache found.");
                }

                data = new RequestServiceableLiveData<T>((OnObserveEmitMutableLiveData<T>) data, request, shouldUseCacheDetermined, liveDataRunnable);
            } else {
                Timber.d("LiveData requested afresh!");
                final OnObserveEmitMutableLiveData<T> onObserve = new OnObserveEmitMutableLiveData<>(StubRunnable.STUB_RUNNABLE);
                data = new RequestServiceableLiveData<>(onObserve, request, shouldUseCacheDetermined, liveDataRunnable);
                cache.put(request, new Pair<>(onObserve, new MutableLong(SystemClock.elapsedRealtime())));
                liveDataRunnable.setLiveData(onObserve);
            }
            Timber.d("LiveData being returned = %s", data.toString());
            return data;
        }
    }

    public <T, U> LiveData<T> prepareLiveData(LiveDataRequest<U> request,
                                              ShouldUseCache shouldUseCache,
                                              LiveData<T> source) {
        // TODO(Manish): Assertion
        //  Comment out for release env.
        assertOverridesEqualsAndHashCodeMethods(request);

        synchronized (this) {
            // Determine ShouldUseCache once and only once
            boolean shouldUseCacheDetermined = shouldUseCache.shouldUseCache();
            LiveData<T> data;
            if (shouldUseCacheDetermined) {
                Timber.d("LiveData requested from cache...");
                data = (DelegateLiveData<T>) cache.get(request).first;
                if (data == null) {
                    throw new RuntimeException("No default behavior of checking cache found.");
                }
            } else {
                Timber.d("LiveData requested afresh!");
                data = new DelegateLiveData<>(source);
                cache.put(request, new Pair<>(data, new MutableLong(SystemClock.elapsedRealtime())));
            }
            // TODO: Introduce a proper toString() for Delegate LiveData as well as see if we need to
            //   have a similar one for RequestServiceableLiveData
            Timber.d("LiveData being returned = " + "{" + request.toString() + ", " + data.toString() + "}");
            return data;
        }
    }

    public <T, U> MutableLiveData<T> prepareLiveDataOneShot(LiveDataRequest<U> request,
                                                            MyLiveDataRunnable<T> liveDataRunnable) {
        MutableLiveData<T> data = new OnObserveEmitMutableLiveData<>(liveDataRunnable);
        Timber.d("LiveData requested afresh!");
        liveDataRunnable.setLiveData(data);
        Timber.d("LiveData being returned = %s", data.toString());
        return data;
    }

    protected abstract boolean isDebugEnvironment();

    private <U> void assertOverridesEqualsAndHashCodeMethods(LiveDataRequest<U> request) {
        if (isDebugEnvironment()) {
            // Check if request overrides hashCode() and equals() method
            final Class<?> clazz = request.getRequest().getClass();
            boolean assertion = false;
            try {
                assertion = clazz.getMethod("hashCode").getDeclaringClass().equals(clazz) &&
                        clazz.getMethod("equals", Object.class).getDeclaringClass().equals(clazz);
            } catch (NoSuchMethodException e) {
            }
            if (!assertion) {
                throw new RuntimeException("The request class = " + clazz.getSimpleName() + " must implement hashCode() and equals() methods");
            }
        }
    }

    // Borrowed from androidx.arch.core.executor.DefaultTaskExecutor
    private boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public void assertMainThread() {
        // No method name supplied for now...
        final String methodName = "this method";
        if (!isMainThread()) {
            throw new IllegalStateException("Cannot invoke " + methodName + " on a background"
                    + " thread");
        }
    }


}
