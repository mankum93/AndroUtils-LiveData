package com.androutils.livedata.repository;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 30-06-2020
 */
public final class StubRunnable implements Runnable {

    public static final StubRunnable STUB_RUNNABLE = new StubRunnable();

    private StubRunnable() {
    }

    @Override
    public void run() {
        // Do nothing
    }
}
