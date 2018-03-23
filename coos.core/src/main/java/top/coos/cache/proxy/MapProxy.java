package top.coos.cache.proxy;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;



public class MapProxy extends CacheProxy {
    protected Map<Object, Object> cache = Collections.synchronizedMap(new LinkedHashMap<Object, Object>());

    @Override
    public void set(Object key, Object value) {
        cache.put(key, value);
    }

    @Override
    public Object get(Object key) {
        return cache.get(key);
    }

    @Override
    public int size() {
        return cache.size();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Set<?> keySet() {
        return new HashSet(cache.keySet());
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public void remove(Object key) {
        if (cache.containsKey(key)) {
            cache.remove(key);
        }
    }

}
