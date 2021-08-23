package com.androutils.livedata.repository;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;

import timber.log.Timber;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 29-06-2020
 */
public class DelegateLiveData<T> extends LiveData<T> {

    private LiveData<T> delegate;
    private static Field mVersion;

    public DelegateLiveData(LiveData<T> delegate) {
        this.delegate = delegate;
        correctVersioning();
    }

    private void correctVersioning() {
        // FIX:[BEGIN]
        // Make sure that versioning is properly set with this "stubbed" live data so that
        // any observation to this facade gets passed for an event of setting/posting value
        // for delegate
        // Note: Bug raised when MediatorLiveData fails to deliver result
        try {
            if(mVersion == null){
                mVersion = LiveData.class.getDeclaredField("mVersion");
                mVersion.setAccessible(true);
            }
            mVersion.set(this, 1);
        } catch (NoSuchFieldException e) {
            Timber.e(e, "Couldn't correct versioning!");
            return;
        }
        catch (IllegalAccessException e) {
            Timber.e(e, "Couldn't correct versioning!");
            return;
        }
        Timber.d("Versioning corrected for LiveData = %s", DelegateLiveData.this.toString());
        // [END]
    }

    @Override
    @MainThread
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        delegate.observe(owner, observer);
    }

    @Override
    @MainThread
    public void observeForever(@NonNull Observer<? super T> observer) {
        delegate.observeForever(observer);
    }

    @Override
    @MainThread
    public void removeObserver(@NonNull Observer<? super T> observer) {
        delegate.removeObserver(observer);
    }

    @Override
    @MainThread
    public void removeObservers(@NonNull LifecycleOwner owner) {
        delegate.removeObservers(owner);
    }

    @Override
    @Nullable
    public T getValue() {
        return delegate.getValue();
    }

    @Override
    public boolean hasObservers() {
        return delegate.hasObservers();
    }

    @Override
    public boolean hasActiveObservers() {
        return delegate.hasActiveObservers();
    }
}
