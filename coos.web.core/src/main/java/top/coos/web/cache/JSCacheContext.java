package top.coos.web.cache;

import top.coos.cache.CacheContext;
import top.coos.cache.factory.CacheContextFactory;

public class JSCacheContext extends CacheContext<String, String> {
    public static final JSCacheContext CONTEXT = get();

    public static JSCacheContext get() {
        return CacheContextFactory.get(JSCacheContext.class);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
