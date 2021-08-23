package com.androutils.livedata.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 19-06-2020
 */
public class OnObserveEmitMutableLiveData<T> extends MutableLiveData<T> {

    /**
     * To be run as soon as an observer starts observing this {@link androidx.lifecycle.LiveData}
     */
    private Runnable onObserve;

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, observer);
        run();
    }

    @Override
    public void observeForever(@NonNull Observer<? super T> observer) {
        super.observeForever(observer);
        run();
    }

    public OnObserveEmitMutableLiveData(Runnable onObserve) {
        this.onObserve = onObserve;
    }

    public void setOnObserve(Runnable onObserve) {
        this.onObserve = onObserve;
    }

    protected void run(){
        onObserve.run();
    }
}
