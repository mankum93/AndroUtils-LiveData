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

import androidx.collection.LruCache;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 22-07-2020
 */
public abstract class AbsLruCacheAdapter<K, V, Cache extends LruCache<K, V>> extends AbsCacheAdapter<K, V, Cache> {

    public AbsLruCacheAdapter(Cache cache) {
        super(cache);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public V put(K key, V value) {
        return cache.put(key, value);
    }

    @Override
    public V remove(K key) {
        return cache.remove(key);
    }

    @Override
    public void evictAll() {
        cache.evictAll();
    }

    @Override
    public long size() {
        return cache.size();
    }
}
