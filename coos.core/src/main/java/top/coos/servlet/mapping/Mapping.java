package top.coos.servlet.mapping;

import java.lang.reflect.Method;

import top.coos.exception.CoreException;
import top.coos.resource.handler.ResourceHandler;

public abstract class Mapping {

    protected Class<?> controllerClass;

    protected Method method;

    public <T> T createController() throws CoreException {
        try {

            return ResourceHandler.getBean(controllerClass);
        } catch (Exception e) {
            throw new CoreException(e);
        }
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

}
