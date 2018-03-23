package top.coos.web.constant;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * 常量参数
 * 
 */
public interface WebConstant {

	/**
	 * 页面路径常量
	 * 
	 */
	public class Page {

		public static String CORE_PUBLIC = Folder.CORE_FOLDER + "public";

		public static String CORE_PUBLIC_JSP = Folder.CORE_FOLDER + "public.jsp";

		public static String WEB_INF_VIEW_CORE_PUBLIC = Folder.WEB_INF_VIEW_CORE_FOLDER + "public";

		public static String WEB_INF_VIEW_CORE_PUBLIC_JSP = Folder.WEB_INF_VIEW_CORE_FOLDER + "public.jsp";

		public static String WEB_INF_VIEW_PUBLIC = Folder.WEB_INF_VIEW_FOLDER + "public";

		public static String WEB_INF_VIEW_PUBLIC_JSP = Folder.WEB_INF_VIEW_FOLDER + "public.jsp";

	}

	/**
	 * 目录
	 * 
	 * @author 朱亮
	 * 
	 */
	public class Folder {

		public static final String CLASSES_FOLDER = "WEB-INF/classes/";

		public static final String WEB_INF_VIEW_FOLDER = "/WEB-INF/views/";

		public static final String CORE_FOLDER = "core/";

		public static final String WEB_INF_VIEW_CORE_FOLDER = WEB_INF_VIEW_FOLDER + CORE_FOLDER;

		public static final String RESOURCE_PAGES_FOLDER = "resource/pages/";

	}

	public class Path {

		public static final String getWebServerPath(HttpServletRequest request) {

			return getWebServerPath(request.getServletContext());
		}

		public static final String getWebServerPath(ServletContext context) {

			String serverPath = context.getRealPath("");
			return serverPath;
		}

		public static final String getWebServerTempPath(HttpServletRequest request) {

			return getWebServerPath(request) + "/temp/";
		}

		public static final String getWebServerResourcePath(HttpServletRequest request) {

			return getWebServerPath(request) + "/resource/";
		}

		public static final String getWebServerViewPath(HttpServletRequest request) {

			return getWebServerPath(request) + WebConstant.Folder.WEB_INF_VIEW_CORE_FOLDER;
		}
	}

	/**
	 * 全局属性名称
	 * 
	 */
	public class Param {

		public static final String SSO_THIS_LOGIN_ACCOUNT_INFO = "SSO_THIS_LOGIN_ACCOUNT_INFO";

		public static final String SSO_LOGIN_TOKEN = "SSO_LOGIN_TOKEN";

		public static final String CORE_APP_MAIN_JS = "CORE_APP_MAIN_JS";

		public static final String CORE_FRAME_MAIN_JS = "CORE_FRAME_MAIN_JS";

		public static final String CORE_RESOURCE_JSON_PATH = "CORE_RESOURCE_JSON_PATH";

		public static final String APP_RESOURCE_JSON_PATH = "APP_RESOURCE_JSON_PATH";

		public static final String CORE_CONFIG_JSON = "CORE_CONFIG_JSON";

		public static final String CORE_CONFIG = "CORE_CONFIG";

		public static final String DEV_SERVER_CONFIG = "DEV_SERVER_CONFIG";

		public static final String CACHE_DATA_FOR_CSS = "CACHE_DATA_FOR_CSS";

		public static final String CACHE_DATA_FOR_JS = "CACHE_DATA_FOR_JS";

		public static final String THIS_PAGE_ID = "THIS_PAGE_ID";

		public static final String SERVLET_CONTEXT_PROJECT_ID = "SERVLET_CONTEXT_PROJECT_ID";

		public static final String THIS_CLIENT_ENTITY = "THIS_CLIENT_ENTITY";

		public static final String THIS_DEV_CLIENT_ENTITY = "THIS_DEV_CLIENT_ENTITY";

	}
}
