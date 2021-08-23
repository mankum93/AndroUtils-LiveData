package com.androutils.livedata.asynctask;

import android.os.AsyncTask;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 04-07-2020
 */
public abstract class LiveAsyncTask<Params, Progress, Result> implements AsyncTaskInterface<Params, Progress, Result>, Observer<Result> {

    private AsyncTaskEnabler<Params, Progress, Result> worker;

    public LiveAsyncTask() {
        this.worker = new AsyncTaskEnabler<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AsyncTask.Status getStatus() {
        return worker.getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCancelled() {
        return worker.isCancelled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return worker.cancel(mayInterruptIfRunning);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Result get() throws ExecutionException, InterruptedException {
        return worker.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Result get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        return worker.get(timeout, unit);
    }

    @Override
    public AsyncTask<Params, Progress, Result> execute(Params... params) {
        throw new UnsupportedOperationException("Use the LifecycleOwner counterpart");
    }

    @Override
    public AsyncTask<Params, Progress, Result> executeOnExecutor(Executor exec, Params... params) {
        throw new UnsupportedOperationException("Use the LifecycleOwner counterpart");
    }

    /**
     * Same as the respective {@link AsyncTask#execute(Object[])} method with some exceptions as
     * described here
     *
     * Exceptions:
     * - The return type although indicates a valid AsyncTask which is never to be exposed in our implementation
     * - The {@link LifecycleOwner} is what {@link AsyncTask} would be bound to...
     *
     * @param owner The LifecycleOwner, i.e, could be an Activity or a Fragment's view lifecycle
     * @param params Params to be used in task execution
     * @return null/void value
     */
    public AsyncTask<Params, Progress, Result> execute(@Nullable LifecycleOwner owner, Params... params) {
        worker.execute(owner, params, this);
        return null;
    }


    public AsyncTask<Params, Progress, Result> executeOnExecutor(@Nullable LifecycleOwner owner, Executor exec, Params... params) {
        worker.executeOnExecutor(owner, exec, params, this);
        return null;
    }

    public AsyncTask<Params, Progress, Result> execute(@Nullable FragmentActivity owner, Params... params) {
        worker.execute(((LifecycleOwner) owner), params, this);
        return null;
    }


    public AsyncTask<Params, Progress, Result> executeOnExecutor(@Nullable FragmentActivity owner, Executor exec, Params... params) {
        worker.executeOnExecutor(((LifecycleOwner) owner), exec, params, this);
        return null;
    }

    public AsyncTask<Params, Progress, Result> execute(@Nullable Fragment owner, Params... params) {
        worker.execute(owner.getViewLifecycleOwner(), params, this);
        return null;
    }


    public AsyncTask<Params, Progress, Result> executeOnExecutor(@Nullable Fragment owner, Executor exec, Params... params) {
        worker.executeOnExecutor(owner.getViewLifecycleOwner(), exec, params, this);
        return null;
    }


    @Override
    public void onChanged(Result result) {
       throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @MainThread
    @Override
    public void onPreExecute() {
    }

    /**
     * {@inheritDoc}
     */
    @WorkerThread
    @Override
    public abstract Result doInBackground(Params... params);

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"UnusedDeclaration"})
    @MainThread
    @Override
    public void onPostExecute(Result result) {
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"UnusedDeclaration"})
    @MainThread
    @Override
    public void onProgressUpdate(Progress... values) {
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"UnusedParameters"})
    @MainThread
    @Override
    public void onCancelled(Result result) {
        onCancelled();
    }

    /**
     * {@inheritDoc}
     */
    @MainThread
    @Override
    public void onCancelled() {
    }


    public static void execute(Runnable runnable) {
        throw new RuntimeException("Not allowed!");
    }
}
