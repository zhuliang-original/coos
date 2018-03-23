package top.coos.resource.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ResourceInvocationHandler implements InvocationHandler {

    private Object bean;

    public ResourceInvocationHandler(Object bean) {
        this.bean = bean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // System.out.println(object);
        Object result = method.invoke(bean, args);
        return result;
    }

}
