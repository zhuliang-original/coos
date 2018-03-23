package top.coos.classloader.cache;

import top.coos.cache.CacheContext;

public class ClassByteCache extends CacheContext<String, byte[]> {

    public static ClassByteCache CONTEXT = get(ClassByteCache.class);

    @Override
    public boolean isSingleton() {
        return true;
    }

}
