package top.coos.classloader.cache;

import top.coos.cache.CacheContext;

public class ClassCache extends CacheContext<String, Class<?>> {

    public static ClassCache CONTEXT = get(ClassCache.class);

    @Override
    public boolean isSingleton() {
        return true;
    }

}
