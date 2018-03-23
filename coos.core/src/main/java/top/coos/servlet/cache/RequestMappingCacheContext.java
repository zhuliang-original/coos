package top.coos.servlet.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import top.coos.cache.CacheContext;
import top.coos.servlet.mapping.RequestMapping;

public class RequestMappingCacheContext extends CacheContext<String, List<RequestMapping>> {

    public static RequestMappingCacheContext CONTEXT = get(RequestMappingCacheContext.class);

    public List<RequestMapping> getMappings(String pathinfo) {
        List<RequestMapping> methods = new ArrayList<RequestMapping>();
        List<RequestMapping> servletMethods = get(pathinfo);
        if (servletMethods != null) {
            methods.addAll(servletMethods);
        }
        if (methods != null && methods.size() > 0) {
        } else {
            Set<String> keySet = keySet();
            for (String key : keySet) {
                servletMethods = get(key);
                for (int i = 0; i < servletMethods.size(); i++) {
                    RequestMapping methodRule = servletMethods.get(i);
                    if (methodRule.validata(pathinfo)) {
                        methods.add(methodRule);
                    }
                }
            }
            put(pathinfo, methods);
        }
        return methods;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
