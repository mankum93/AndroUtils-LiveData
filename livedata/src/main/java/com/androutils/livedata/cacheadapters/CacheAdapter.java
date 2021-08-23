
/*
 *  Copyright (C) 2020 Manish Kumar Sharma@bit.ly/2HjxA0C
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.androutils.livedata.cacheadapters;

/**
 * A very base set of cache characteristics assumed to be present with any cache universelle.
 *
 * </p>Implement this interface to adapt to your particular cache implementation
 *
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 22-07-2020
 */
public interface CacheAdapter<K, V> {

    /**
     * Returns the value for {@code key} if it exists. It is up to the Adaptee to decide on the nullability
     * enforcements of the returned value or any others deemed apt.
     */
    V get(K key);

    /**
     * Caches {@code value} for {@code key}.
     *
     * </p>
     * It is up to the Adaptee to decide on the nullability
     * enforcements of the value cached or any others deemed apt.
     *
     * @return the previous value mapped by {@code key}.
     */
    V put(K key, V value);

    /**
     * Removes the entry for {@code key} if it exists.
     *
     * @return the previous value mapped by {@code key}.
     */
    V remove(K key);

    /**
     * Clear the cache, removing value entries for each of the keys present
     */
    void evictAll();

    /**
     * A vague method serving only as a handle to "some" "size" impl. Adaptee is free to
     * define their custom meaning of what this "size" constitutes as well as the respective
     * calculation. For ex,
     *
     * </p>
     * <ul>
     *     <li>- You may return the no of entries this cache holds</li>
     *     <li>- You may return the sum of size of all the entries in cache</li>
     *     <li>- You may return the maximum size this cache is capped to!</li>
     *     <li>- Or any other custom meaning...</li>
     * </ul>
     */
    long size();
}
