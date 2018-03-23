package top.coos.web.cache;

import top.coos.cache.CacheContext;
import top.coos.cache.factory.CacheContextFactory;

public class ResourceCacheContext extends CacheContext<String, String> {
    public static final ResourceCacheContext CONTEXT = get();

    public static ResourceCacheContext get() {
        return CacheContextFactory.get(ResourceCacheContext.class);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
