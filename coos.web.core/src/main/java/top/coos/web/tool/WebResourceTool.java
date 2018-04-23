package top.coos.web.tool;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import top.coos.ConfigurationFactory;
import top.coos.config.DevServerConfig;
import top.coos.tool.string.StringHelper;
import top.coos.tool.upload.FileServerTool;
import top.coos.web.cache.CSSCacheContext;
import top.coos.web.cache.JSCacheContext;
import top.coos.web.cache.ResourceCacheContext;
import top.coos.web.constant.WebConstant;
import top.coos.web.resource.WebResourcePackageInfo;

public class WebResourceTool {

	private static boolean COPY = false;

	public synchronized static boolean copy(HttpServletRequest request) {

		boolean hasCopy = false;
		if (DevServerConfig.CONFIG.debug) {
			hasCopy = true;
			try {
				for (String resource : ResourceCacheContext.CONTEXT.keySet()) {
					WebResourcesCopyTool.copyByPath(request, resource);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				for (String resource : ResourceCacheContext.CONTEXT.keySet()) {
					String filePath = ResourceCacheContext.CONTEXT.get(resource);
					File file = new File(WebConstant.Path.getWebServerPath(request) + filePath);
					if (!COPY || file == null || !file.exists()) {
						try {
							hasCopy = true;
							WebResourcesCopyTool.copyByPath(request, resource);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			COPY = true;

		}
		if (hasCopy) {
			try {
				String filePath = WebConstant.Path.getWebServerPath(request) + "/resource/web-resource";
				File file = new File(filePath);
				if (!COPY || file == null || !file.exists()) {
					WebResourcesCopyTool.copyByPath(request, WebResourcePackageInfo.PATH);
				}
			} catch (Exception e) {
			}
		}

		request.getSession().setAttribute(WebConstant.Param.CORE_CONFIG, ConfigurationFactory.get(request));
		request.getSession().setAttribute(WebConstant.Param.CORE_CONFIG_JSON,
				JSONObject.fromObject(ConfigurationFactory.get(request)));
		request.getSession().setAttribute(WebConstant.Param.DEV_SERVER_CONFIG, DevServerConfig.get());
		request.getSession().setAttribute(WebConstant.Param.CACHE_DATA_FOR_CSS, CSSCacheContext.CONTEXT.keySet());
		request.getSession().setAttribute(WebConstant.Param.CACHE_DATA_FOR_JS, JSCacheContext.CONTEXT.keySet());

		// 这里解析frame框架地址
		if (DevServerConfig.CONFIG.debug) {
			String this_server_path = WebConstant.Path.getWebServerPath(request);
			if (!StringHelper.isEmpty(this_server_path)) {
				File server_folder = new File(this_server_path);
				if (server_folder != null && server_folder.isDirectory()) {
					File coos_js_file = new File(server_folder.getParentFile().getAbsolutePath()
							+ "/frame/resource/coos/js/coos.js");
					if (coos_js_file != null && coos_js_file.isFile()) {
						String FRAME_SERVER_URL = FileServerTool.getThisFileServerUrl(request)
								.replace("/coos.folder", "/frame/");

						request.getSession().setAttribute("FIND_FRAME_SERVER", true);
						String FRAME_COOS_CSS_PATH = FRAME_SERVER_URL + "/resource/coos/css/coos.css";
						String FRAME_COOS_JS_PATH = FRAME_SERVER_URL + "/resource/coos/merge/coos.js";

						String FRAME_COOS_FRAME_CSS_PATH = FRAME_SERVER_URL + "/resource/coos/css/coos.frame.css";
						String FRAME_COOS_FRAME_JS_PATH = FRAME_SERVER_URL + "/resource/coos/merge/coos.frame.js";

						String FRAME_COOS_PAGE_CSS_PATH = FRAME_SERVER_URL + "/resource/coos/css/coos.page.css";
						String FRAME_COOS_PAGE_JS_PATH = FRAME_SERVER_URL + "/resource/coos/merge/coos.page.js";

						request.getSession().setAttribute("FRAME_COOS_CSS_PATH", FRAME_COOS_CSS_PATH);
						request.getSession().setAttribute("FRAME_COOS_JS_PATH", FRAME_COOS_JS_PATH);

						request.getSession().setAttribute("FRAME_COOS_FRAME_CSS_PATH", FRAME_COOS_FRAME_CSS_PATH);
						request.getSession().setAttribute("FRAME_COOS_FRAME_JS_PATH", FRAME_COOS_FRAME_JS_PATH);

						request.getSession().setAttribute("FRAME_COOS_PAGE_CSS_PATH", FRAME_COOS_PAGE_CSS_PATH);
						request.getSession().setAttribute("FRAME_COOS_PAGE_JS_PATH", FRAME_COOS_PAGE_JS_PATH);

					}
				}
			}
		}

		return hasCopy;
	}
}
