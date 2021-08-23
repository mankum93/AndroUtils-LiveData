package com.androutils.livedata.asynctask;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.androutils.livedata.repository.OnObserveEmitMutableLiveData;
import com.androutils.livedata.repository.StubRunnable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 06-07-2020
 */
public final class AsyncTaskEnabler<Params, Progress, Result> extends OnObserveEmitMutableLiveData<Result> {

    @SuppressLint("StaticFieldLeak")
    private AsyncTask<Params, Progress, Result> asyncTask = new AsyncTask<Params, Progress, Result>() {
        @SafeVarargs
        @Override
        protected final Result doInBackground(Params... params) {
            return liveAsyncTask.doInBackground(params);
        }

        @Override
        protected void onPreExecute() {
            if(liveAsyncTask != null){
                liveAsyncTask.onPreExecute();
            }
        }

        @Override
        protected void onPostExecute(Result result) {
            if(liveAsyncTask != null){
                liveAsyncTask.onPostExecute(result);
            }
        }

        @Override
        protected void onProgressUpdate(Progress... values) {
            if(liveAsyncTask != null){
                liveAsyncTask.onProgressUpdate(values);
            }
        }

        @Override
        protected void onCancelled(Result result) {
            if(liveAsyncTask != null){
                liveAsyncTask.onCancelled(result);
            }
        }

        @Override
        protected void onCancelled() {
            if(liveAsyncTask != null){
                liveAsyncTask.onCancelled();
            }
        }
    };
    private Params[] params;
    private LiveAsyncTask<Params, Progress, Result> liveAsyncTask;
    private Executor executor;

    public AsyncTaskEnabler() {
        // Runnable task dummied by AsyncTask impl. in requirement
        // LOLZ!
        super(StubRunnable.STUB_RUNNABLE);
    }

    @Override
    public void postValue(Result value) {
        throw new UnsupportedOperationException("DO NOT TOUCH");
    }

    @Override
    public void setValue(Result value) {
        throw new UnsupportedOperationException("DO NOT TOUCH");
    }

    public void execute(@Nullable LifecycleOwner owner, Params[] params, @NonNull LiveAsyncTask<Params, Progress, Result> observer) {
        this.liveAsyncTask = observer;
        this.params = params;
        if(owner == null){
            super.observeForever(observer);
        }
        else {
            super.observe(owner, observer);
        }
    }

    public void executeOnExecutor(@Nullable LifecycleOwner owner, Executor exec, Params[] params, @NonNull LiveAsyncTask<Params, Progress, Result> observer) {
        this.liveAsyncTask = observer;
        this.params = params;
        this.executor = exec;
        if(owner == null){
            super.observeForever(observer);
        }
        else {
            super.observe(owner, observer);
        }
    }

    // Not in use yet.
    /*package-access*/ void execute(Params[] params, @NonNull LiveAsyncTask<Params, Progress, Result> observer) {
        this.liveAsyncTask = observer;
        this.params = params;
        super.observeForever(observer);
    }

    // Not in ues yet.
    /*package-access*/ void executeOnExecutor(Params[] params, Executor exec, @NonNull LiveAsyncTask<Params, Progress, Result> observer) {
        this.liveAsyncTask = observer;
        this.params = params;
        this.executor = exec;
        super.observeForever(observer);
    }

    @Override
    public void removeObserver(@NonNull Observer<? super Result> observer) {
        this.liveAsyncTask = null;
        super.removeObserver(observer);
    }

    @Override
    public void removeObservers(@NonNull LifecycleOwner owner) {
        this.liveAsyncTask = null;
        super.removeObservers(owner);
    }

    // Guaranteed to run post setting of params...
    @Override
    protected void run() {
        // Just for the fun of it
        super.run();
        // Actual impl.!
        if(executor != null){
            asyncTask.executeOnExecutor(executor, params);
        }else {
            asyncTask.execute(params);
        }
    }


    // AsyncTask delegate

    public AsyncTask.Status getStatus() {
        return asyncTask.getStatus();
    }

    public boolean isCancelled() {
        return asyncTask.isCancelled();
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return asyncTask.cancel(mayInterruptIfRunning);
    }

    public Result get() throws ExecutionException, InterruptedException {
        return asyncTask.get();
    }

    public Result get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        return asyncTask.get(timeout, unit);
    }
}
