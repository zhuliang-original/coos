package top.coos.tool.request;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import top.coos.tool.string.StringHelper;

public class RequestTool {

	public static String getServletpath(HttpServletRequest request) {

		String servletpath = request.getServletPath();
		String pathinfo = request.getPathInfo();
		if (!StringHelper.isEmpty(pathinfo)) {
			servletpath = servletpath + "/" + pathinfo;
		}
		if (StringHelper.isEmpty(servletpath)) {
			return null;
		}
		return servletpath.replaceAll("/+", "/");
	}

	public static String getStringContent(HttpServletRequest request) {

		StringBuffer buffer = new StringBuffer();
		try {

			BufferedReader br = request.getReader();
			String str = "";
			while ((str = br.readLine()) != null) {
				buffer.append(str);
			}
		} catch (Exception e) {
		}
		return buffer.toString();
	}

	/**
	 * 将request转成Map
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getRequestMap(HttpServletRequest request) {

		Map<String, String> requestmap = new HashMap<String, String>();
		Map<String, String[]> map = request.getParameterMap();
		for (String key : map.keySet()) {
			if (map.get(key) != null) {
				if (map.get(key)[0] != null) {
					String value = map.get(key)[0];
					requestmap.put(key, value);

				}
			}
		}
		return requestmap;
	}

	public static String getIP(HttpServletRequest request) {

		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		return ip;
	}

	public static byte[] getBytesContent(HttpServletRequest request) throws Exception {

		int len = request.getContentLength();
		if (len > 0) {
			byte[] buffer = new byte[len];
			ServletInputStream iii = request.getInputStream();
			iii.read(buffer, 0, len);
			return buffer;
		}
		return null;
	}

	public static String getUrl(HttpServletRequest request) {

		String url = request.getScheme() + "://";
		url += request.getHeader("host");
		url += request.getRequestURI();
		if (!StringHelper.isEmpty(request.getQueryString())) {
			url += "?" + request.getQueryString();
		}
		return url;
	}

	public static String getServerUrl(HttpServletRequest request) {

		String url = getUrl(request);
		String servletpath = getServletpath(request);
		String serverurl = url;
		int index = url.indexOf(servletpath);
		if (index > 0) {
			serverurl = serverurl.substring(0, index);
		}
		return serverurl;
	}
}
