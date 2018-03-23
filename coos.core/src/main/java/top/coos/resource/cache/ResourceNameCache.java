package top.coos.resource.cache;

import top.coos.cache.CacheContext;
import top.coos.resource.bean.ResourceBean;

public class ResourceNameCache extends CacheContext<String, ResourceBean> {

    public static ResourceNameCache CONTEXT = get(ResourceNameCache.class);

    @Override
    public boolean isSingleton() {
        return true;
    }

}
