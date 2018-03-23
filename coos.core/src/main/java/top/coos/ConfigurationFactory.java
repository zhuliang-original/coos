package top.coos;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public final class ConfigurationFactory {

	static Configuration CONFIGURATION = new Configuration();

	public static Configuration get() {

		return CONFIGURATION;
	}

	public static Configuration get(HttpServletRequest request) {

		if (request == null) {
			return get();
		}
		Configuration configuration = null;
		if (request.getSession().getAttribute("COOS_CONFIGURATION") != null) {
			configuration = (Configuration) request.getSession().getAttribute("COOS_CONFIGURATION");
		}
		if (configuration == null) {
			configuration = get();
		}
		set(request, configuration);
		return configuration;
	}

	public static void set(HttpServletRequest request, Configuration configuration) {

		if (request != null) {
			request.getSession().setAttribute("COOS_CONFIGURATION", configuration);
		}
	}

	public static Configuration get(Map<String, String> properties) {

		return new Configuration(properties);
	}

	public static Configuration get(String projectid, Map<String, String> properties) {

		return new Configuration(projectid, properties);
	}
}
