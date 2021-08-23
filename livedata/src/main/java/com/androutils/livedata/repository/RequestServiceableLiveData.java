package com.androutils.livedata.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 29-06-2020
 */
public class RequestServiceableLiveData<T> extends DelegateMutableLiveData<T> {

    // TODO: Can we replace this machinery with that of a MediatorLiveData?

    private final LiveDataRequest<?> liveDataRequest;
    private final boolean shouldUseCache;

    private final OnObserveEmitMutableLiveData<T> delegate;

    private final Runnable requestServiceRunnable;

    public RequestServiceableLiveData(@NonNull OnObserveEmitMutableLiveData<T> delegate,
                                      @NonNull LiveDataRequest<?> liveDataRequest,
                                      boolean shouldUseCache,
                                      Runnable requestServiceRunnable) {
        super(delegate);
        this.liveDataRequest = liveDataRequest;
        this.shouldUseCache = shouldUseCache;
        this.delegate = delegate;
        this.requestServiceRunnable = requestServiceRunnable;
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        setupDelegate();
        super.observe(owner, observer);
    }

    private void setupDelegate() {
        // This would lead to delegate being observed
        // Make sure that there is some task to emit for the delegate
        if(!shouldUseCache){
            // Initiate a fresh observable emission
            Timber.d( "Initiating a fresh emission of observable...");
            delegate.setOnObserve(requestServiceRunnable);
        }
        else {
            // Results in no-op; directly deliver the result from cache
            Timber.d( "No fresh emission for you!");
            delegate.setOnObserve(StubRunnable.STUB_RUNNABLE);
        }
    }

    @Override
    public void observeForever(@NonNull Observer<? super T> observer) {
        setupDelegate();
        super.observeForever(observer);
    }

    @NotNull
    @Override
    public String toString() {
        return "RequestServiceableLiveData{" +
                "instance=" + super.toString() +
                ", liveDataRequest=" + liveDataRequest +
                ", shouldUseCache=" + shouldUseCache +
                '}';
    }
}
