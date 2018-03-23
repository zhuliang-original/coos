package top.coos.resource.cache;

import top.coos.cache.CacheContext;
import top.coos.resource.bean.ResourceBean;

public class ResourceTypeCache extends CacheContext<String, ResourceBean> {

    public static ResourceTypeCache CONTEXT = get(ResourceTypeCache.class);

    @Override
    public boolean isSingleton() {
        return true;
    }

}
