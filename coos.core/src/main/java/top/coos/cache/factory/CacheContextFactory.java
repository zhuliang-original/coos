package top.coos.cache.factory;

import top.coos.cache.CacheContext;

@SuppressWarnings("unchecked")
public abstract class CacheContextFactory {

    private static final CacheClassCacheContext CACHE_CLASS_CACHE_CONTEXT = new CacheClassCacheContext();

    public synchronized static <T> T get(Class<?> cacheContextClass) {
        try {
            if (cacheContextClass.isAssignableFrom(CacheContext.class)) {
            } else {
                CacheContext<?, ?> context = CACHE_CLASS_CACHE_CONTEXT.get(cacheContextClass.getName());
                boolean isNew = false;
                if (context == null) {
                    isNew = true;
                    context = (CacheContext<?, ?>) cacheContextClass.newInstance();
                    CACHE_CLASS_CACHE_CONTEXT.put(cacheContextClass.getName(), context);
                }
                if (!context.isSingleton() && !isNew) {
                    context = (CacheContext<?, ?>) cacheContextClass.newInstance();
                }
                return (T) context;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
