package top.coos.servlet.resolve;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import top.coos.exception.CoreException;
import top.coos.servlet.annotation.RequestController;
import top.coos.tool.string.StringHelper;

public class ServletPathResolve {

	private Class<?> controlClass;

	public ServletPathResolve(Class<?> clazz) {

		this.controlClass = clazz;
	}

	private Map<String, Method> mapping = new HashMap<String, Method>();

	public Map<String, Method> resolve() throws CoreException {

		mapping = new HashMap<String, Method>();
		if (controlClass.isAnnotationPresent(WebServlet.class)) {
			WebServlet webServlet = controlClass.getAnnotation(WebServlet.class);
			String servletName = webServlet.name();
			if (!StringHelper.isEmpty(servletName)) {
				servletName = controlClass.getName();
			}
			String[] urlPatterns = webServlet.urlPatterns();
			resolveMapping(urlPatterns, controlClass.getMethods());
		} else if (controlClass.isAnnotationPresent(RequestController.class)) {
			RequestController webServlet = controlClass.getAnnotation(RequestController.class);
			String servletName = webServlet.name();
			if (!StringHelper.isEmpty(servletName)) {
			}
			String[] urlPatterns = webServlet.urlPatterns();
			resolveMapping(urlPatterns, controlClass.getMethods());
		}

		return mapping;
	}

	private synchronized void resolveMapping(String[] urlPatterns, Method[] methods)
			throws CoreException {

		if (methods != null && methods.length > 0) {
			for (Method method : methods) {
				if (method.isAnnotationPresent(top.coos.servlet.annotation.RequestMapping.class)) {
					top.coos.servlet.annotation.RequestMapping servletMethod = (top.coos.servlet.annotation.RequestMapping) method
							.getAnnotation(top.coos.servlet.annotation.RequestMapping.class);
					for (String urlPattern : urlPatterns) {
						String[] values = servletMethod.value();
						if (values != null && values.length > 0) {
							for (String value : values) {
								if (StringHelper.isEmpty(value)) {
									value = method.getName();
								}
								String path = getRule(urlPattern, value);

								mapping.put(path, method);
							}
						}

					}

				}
			}
		}
	}

	public String getRule(String urlPattern, String methodName) {

		urlPattern = urlPattern.replaceAll("/+", "/");
		methodName = methodName.replaceAll("/+", "/");
		if (urlPattern.endsWith("/*")) {
			urlPattern = urlPattern.split("/\\*")[0];
		}
		String path_info = "/" + urlPattern + "/" + methodName;
		path_info = path_info.replaceAll("/+", "/");
		return path_info;
	}
}
