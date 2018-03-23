package top.coos.servlet.model;

import top.coos.cache.CacheContext;

public class ModelMap extends CacheContext<String, Object> {

    @Override
    public boolean isSingleton() {
        return false;
    }

}
