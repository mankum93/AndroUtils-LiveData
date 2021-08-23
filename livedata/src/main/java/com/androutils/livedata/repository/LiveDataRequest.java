package com.androutils.livedata.repository;

import java.util.Objects;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 23-06-2020
 */
public class LiveDataRequest<T> {

    private final T request;

    public LiveDataRequest(T request) {
        this.request = request;
    }

    public T getRequest() {
        return request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveDataRequest<?> that = (LiveDataRequest<?>) o;
        return Objects.equals(request, that.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request);
    }

    @Override
    public String toString() {
        return "LiveDataRequest{" +
                "request=" + request +
                '}';
    }
}
