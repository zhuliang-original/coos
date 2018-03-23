package top.coos.cache.factory;

import top.coos.cache.CacheContext;

public class CacheClassCacheContext extends CacheContext<String, CacheContext<?, ?>> {

    @Override
    public boolean isSingleton() {
        return true;
    }

}
