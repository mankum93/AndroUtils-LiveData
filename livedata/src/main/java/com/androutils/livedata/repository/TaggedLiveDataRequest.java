package com.androutils.livedata.repository;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * A {@link LiveDataRequest} having tagging so that requests can maintain an identity
 *
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 23-06-2020
 */
public class TaggedLiveDataRequest<T> extends LiveDataRequest<T> {

    /**
     * Anything to identify the request. Could be an API Name, a URI or any other tag name
     * depending on the context
     *
     * This is required to uniquely identify the requests. For ex, one could make an
     * API call with exact same request to 2 different APIs with different responses.
     * In this case, an identifier would be required to differentiate the requests.
     *
     * Cannot be left blank or null
     */
    @NonNull
    private String requestTag;

    public TaggedLiveDataRequest(T request, @NonNull String requestTag) {
        super(request);
        if(TextUtils.isEmpty(requestTag)){
            throw new NullPointerException("Request Tag cannot be left empty");
        }
        this.requestTag = requestTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TaggedLiveDataRequest<?> that = (TaggedLiveDataRequest<?>) o;
        return requestTag.equalsIgnoreCase(that.requestTag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), requestTag);
    }

    @Override
    public String toString() {
        return "TaggedLiveDataRequest{" +
                "requestTag='" + requestTag + '\'' +
                ", super.toString()=" + super.toString() +
                '}';
    }
}
