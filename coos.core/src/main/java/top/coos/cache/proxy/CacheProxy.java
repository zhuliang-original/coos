package top.coos.cache.proxy;

import java.util.Set;

public abstract class CacheProxy {

    public abstract void set(Object key, Object value);

    public abstract void clear();

    public abstract void remove(Object key);

    public abstract Object get(Object key);

    public abstract int size();

    public abstract Set<?> keySet();
}
