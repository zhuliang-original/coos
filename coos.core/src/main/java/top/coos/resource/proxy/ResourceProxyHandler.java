package top.coos.resource.proxy;

import top.coos.exception.CoreException;

@SuppressWarnings("unchecked")
public class ResourceProxyHandler {

    public static <T> T proxy(Object bean) throws CoreException {
        if (bean == null) {
            throw new CoreException("bean is null");
        }
        return (T) bean;
        // Class<?> clazz = bean.getClass();
        // InvocationHandler handler = new ResourceInvocationHandler(bean);
        // try {
        // Object object = Proxy.newProxyInstance(clazz.getClassLoader(),
        // clazz.getInterfaces(), handler);
        // return (T) object;
        // } catch (Exception e) {
        // throw new CoreException(e);
        // }
    }
}
