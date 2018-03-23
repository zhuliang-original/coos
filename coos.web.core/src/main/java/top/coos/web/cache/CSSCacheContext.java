package top.coos.web.cache;

import top.coos.cache.CacheContext;
import top.coos.cache.factory.CacheContextFactory;

public class CSSCacheContext extends CacheContext<String, String> {
    public static final CSSCacheContext CONTEXT = get();

    public static CSSCacheContext get() {
        return CacheContextFactory.get(CSSCacheContext.class);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
