package com.androutils.livedata.asynctask;

import android.os.AsyncTask;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 07-07-2020
 */
public interface AsyncTaskInterface<Params, Progress, Result> {

    /**
     * @see AsyncTask#getStatus() ()
     */
    public AsyncTask.Status getStatus();

    /**
     * @see AsyncTask#isCancelled() ()
     */
    public boolean isCancelled();

    /**
     * @see AsyncTask#cancel(boolean) ()
     */
    public boolean cancel(boolean mayInterruptIfRunning);

    /**
     * @see AsyncTask#get() ()
     */
    public Result get() throws ExecutionException, InterruptedException;

    /**
     * @see AsyncTask#get(long, TimeUnit) ()
     */
    public Result get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException;

    /**
     * @see AsyncTask#execute(Object[]) ()
     */
    public AsyncTask<Params, Progress, Result> execute(Params... params);

    /**
     * @see AsyncTask#executeOnExecutor(Executor, Object[]) ()
     */
    public AsyncTask<Params, Progress, Result> executeOnExecutor(Executor exec, Params... params);

    /**
     * @see AsyncTask#execute(Runnable) ()
     */
    public static void execute(Runnable runnable) {
        AsyncTask.execute(runnable);
    }

    /**
     * See the {@link AsyncTask} counterpart
     */
    @MainThread
    void onPreExecute();

    /**
     * See the {@link AsyncTask} counterpart
     */
    @WorkerThread
    abstract Result doInBackground(Params... params);

    /**
     * See the {@link AsyncTask} counterpart
     */
    @SuppressWarnings({"UnusedDeclaration"})
    @MainThread
    void onPostExecute(Result result);

    /**
     * See the {@link AsyncTask} counterpart
     */
    @SuppressWarnings({"UnusedDeclaration"})
    @MainThread
    void onProgressUpdate(Progress... values);

    /**
     * See the {@link AsyncTask} counterpart
     */
    @SuppressWarnings({"UnusedParameters"})
    @MainThread
    void onCancelled(Result result);

    /**
     * See the {@link AsyncTask} counterpart
     */
    @MainThread
    void onCancelled();
}
