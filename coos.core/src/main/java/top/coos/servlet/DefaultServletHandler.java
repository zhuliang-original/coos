package top.coos.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.coos.tool.request.RequestTool;

public class DefaultServletHandler {

	public static void execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		if (servlet == null) {
			return;
		}
		if (request.getAttribute("SERVLET_SERVICE_EXECUTE") != null) {
			return;
		}
		ServletContext context = request.getServletContext();
		String servletpath = RequestTool.getServletpath(request);
		Map<String, ?> registration_map = context.getServletRegistrations();
		String jumpServletClass = null;
		for (String key : registration_map.keySet()) {
			if (jumpServletClass != null) {
				break;
			}
			Object object = registration_map.get(key);
			if (object instanceof ServletRegistration) {
				ServletRegistration registration = (ServletRegistration) object;
				if (!registration.getName().equals(servlet.getServletName())) {
					Collection<String> collection = registration.getMappings();
					Iterator<String> mappings = collection.iterator();
					while (mappings.hasNext()) {
						String rule = mappings.next();

						if (rule.equals(servletpath)) {
							jumpServletClass = registration.getClassName();
							break;
						} else {
							if (rule.indexOf("*") > 0) {
								if (rule.indexOf("*") == 0) {
									rule = rule.replace("*", "");
									if (servletpath.lastIndexOf(rule) == servletpath.length() - rule.length()) {

										jumpServletClass = registration.getClassName();
										break;
									}
								} else {
									rule = rule.replace("*", "");
									if (servletpath.lastIndexOf(rule) == 0) {

										jumpServletClass = registration.getClassName();
										break;
									}
								}
							}
						}

					}
				}
			}

		}
		if (request.getAttribute("SERVLET_REQUEST") != null && request.getAttribute("SERVLET_RESPONSE") != null) {

			ServletRequest req = (ServletRequest) request.getAttribute("SERVLET_REQUEST");
			ServletResponse res = (ServletResponse) request.getAttribute("SERVLET_RESPONSE");
			req.setAttribute("SERVLET_SERVICE_EXECUTE", true);
			try {
				Class<?> clazz = Class.forName(jumpServletClass);
				if (HttpServlet.class.isAssignableFrom(clazz)) {
					HttpServlet s = (HttpServlet) clazz.newInstance();
					s.service(req, res);
				}
			} catch (Exception e) {
			}
		}

	}
}
