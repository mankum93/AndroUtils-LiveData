package com.androutils.livedata.shouldusecache;

import java.util.ArrayDeque;

/**
 * @author Manish@bit.ly/2HjxA0C
 * Created on: 08-07-2020
 */
public class ShouldUseCacheComposite implements ShouldUseCache {

    private ArrayDeque<ShouldUseCache> cachesComposition;

    public ShouldUseCacheComposite() {
        cachesComposition = new ArrayDeque<>();
    }

    public void and(ShouldUseCache shouldUseCache){
        cachesComposition.addLast(shouldUseCache);
    }

    @Override
    public boolean shouldUseCache() {
        boolean useCache = true;
        for(ShouldUseCache cache : cachesComposition){
            useCache = cache.shouldUseCache();
            if(!useCache){
                break;
            }
        }
        return useCache;
    }
}
