package top.coos.servlet.context;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import top.coos.exception.CoreException;
import top.coos.servlet.annotation.RequestController;
import top.coos.servlet.cache.RequestMappingCacheContext;
import top.coos.servlet.config.ServletConfig;
import top.coos.servlet.mapping.RequestMapping;
import top.coos.tool.clazz.ClassTool;
import top.coos.tool.string.StringHelper;

public class ControllerContext {

	private static ControllerContext CONTEXT = new ControllerContext();

	private ControllerContext() {

	}

	public static ControllerContext get() {

		return CONTEXT;
	}

	public final Map<String, Class<?>> BUILED_CONTROL_CLASS_MAP = new HashMap<String, Class<?>>();

	public synchronized void build(String packagename) throws CoreException {

		if (StringHelper.isEmpty(packagename)) {
			throw new CoreException("package name can not null");
		}
		List<Class<?>> controlClasss = ClassTool.getClasses(packagename);
		for (Class<?> controlClass : controlClasss) {
			addControl(controlClass);
		}
	}

	public synchronized void addControl(Class<?> controlClass) throws CoreException {

		if (BUILED_CONTROL_CLASS_MAP.get(controlClass.getName()) != null) {
			return;
		}
		BUILED_CONTROL_CLASS_MAP.put(controlClass.getName(), controlClass);
		if (controlClass.isAnnotationPresent(WebServlet.class)) {
			WebServlet webServlet = controlClass.getAnnotation(WebServlet.class);
			String servletName = webServlet.name();
			if (!StringHelper.isEmpty(servletName)) {
				servletName = controlClass.getName();
			}
			String[] urlPatterns = webServlet.urlPatterns();
			addRequestMapping(controlClass, urlPatterns, controlClass.getMethods());
		} else if (controlClass.isAnnotationPresent(RequestController.class)) {
			RequestController webServlet = controlClass.getAnnotation(RequestController.class);
			String servletName = webServlet.name();
			if (!StringHelper.isEmpty(servletName)) {
			}
			String[] urlPatterns = webServlet.urlPatterns();
			addRequestMapping(controlClass, urlPatterns, controlClass.getMethods());
		}
	}

	private synchronized void addRequestMapping(Class<?> controlClass, String[] urlPatterns, Method[] methods)
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

								List<RequestMapping> requestMappings = RequestMappingCacheContext.CONTEXT.get(path);

								if (requestMappings == null) {
									requestMappings = new ArrayList<RequestMapping>();
								}
								boolean has = false;
								for (RequestMapping requestMapping : requestMappings) {
									if (controlClass.getName().equals(requestMapping.getControllerClass().getName())
											&& requestMapping.getMethod().getName().equals(method.getName())) {
										has = true;
										break;
									}
								}
								if (has) {
									System.out.println(controlClass.getName() + " method " + method.getName() + " existed.");
									continue;
								}
								RequestMapping requestMapping = new RequestMapping();
								requestMapping.setMethod(method);
								requestMapping.setControllerClass(controlClass);
								if (ServletConfig.get().getController() != null) {
									requestMapping.setPath(path, ServletConfig.get().getController().getServlet_patterns());
								} else {

									requestMapping.setPath(path, null);
								}
								requestMappings.add(requestMapping);
								RequestMappingCacheContext.CONTEXT.put(path, requestMappings);
							}
						}

					}

				}
			}
		}
	}

	public static String getRule(String urlPattern, String methodName) {

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
