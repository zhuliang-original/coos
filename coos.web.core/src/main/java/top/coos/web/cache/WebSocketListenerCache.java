package top.coos.web.cache;

import top.coos.cache.CacheContext;

public class WebSocketListenerCache extends CacheContext<String, Class<?>> {
    public static final WebSocketListenerCache CONTEXT = get(WebSocketListenerCache.class);

    @Override
    public boolean isSingleton() {
        return true;
    }

}
