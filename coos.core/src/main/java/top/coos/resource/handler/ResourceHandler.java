package top.coos.resource.handler;

import java.lang.reflect.Field;
import java.util.List;

import javax.annotation.Resource;

import top.coos.exception.CoreException;
import top.coos.resource.bean.ResourceBean;
import top.coos.resource.cache.ResourceNameCache;
import top.coos.resource.cache.ResourceTypeCache;
import top.coos.resource.proxy.ResourceProxyHandler;
import top.coos.tool.field.FieldTool;
import top.coos.tool.string.StringHelper;

@SuppressWarnings("unchecked")
public class ResourceHandler {

    public static <T> T getBean(String name) throws CoreException {
        return getBean(null, name, null);
    }

    public static <T> T getBean(Class<?> type) throws CoreException {
        return getBean(type, null);
    }

    public static <T> T getBean(Class<?> type, Object param) throws CoreException {
        Object[] objects = null;
        if (param != null) {
            objects = new Object[] { param };
        }
        return getBean(type, objects);
    }

    public static <T> T getBean(Class<?> type, Object[] params) throws CoreException {
        return getBean(type, null, params);
    }

    public static <T> T getBean(Class<?> type, String name, Object[] params) throws CoreException {
        ResourceLoadHandler.init();
        ResourceBean resourceBean = null;
        if (!StringHelper.isEmpty(name)) {
            resourceBean = ResourceNameCache.CONTEXT.get(name);
        }
        if (resourceBean == null) {
            if (type != null) {
                resourceBean = ResourceTypeCache.CONTEXT.get(type.getName());
            } else {
                throw new CoreException(name + " bean is not find");
            }
        }
        return get(type, resourceBean, params);
    }

    private static <T> T get(Class<?> type, ResourceBean resourceBean, Object[] params) throws CoreException {
        Object bean = null;
        try {
            if (resourceBean == null) {
                bean = type.newInstance();
            } else {
                bean = resourceBean.getInstanceClass().newInstance();
            }
            if (bean.getClass().getInterfaces() != null && bean.getClass().getInterfaces().length > 0) {
                bean = ResourceProxyHandler.proxy(bean);
            }
        } catch (Exception e) {
            throw new CoreException(e);
        }
        fullBean(bean);
        return (T) bean;

    }

    private static void fullBean(Object bean) throws CoreException {
        if (bean == null) {
            return;
        }
        try {
            Class<?> clazz = bean.getClass();
            List<Field> fields = FieldTool.getClassFields(clazz);
            if (fields != null && fields.size() > 0) {
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Resource.class)) {
                        field.setAccessible(true);
                        if (field.get(bean) != null) {
                            continue;
                        }
                        Resource resource = field.getAnnotation(Resource.class);
                        Class<?> type = field.getType();
                        String name = resource.name();
                        Object value = getBean(type, name, null);
                        field.set(bean, value);
                    }
                }
            }
        } catch (CoreException e) {
            throw e;
        } catch (Exception e) {
            throw new CoreException(e);
        }
    }

}
