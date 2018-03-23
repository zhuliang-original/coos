package top.coos.web.tool;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import top.coos.ConfigurationFactory;
import top.coos.config.DevServerConfig;
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
		request.getSession().setAttribute(WebConstant.Param.CACHE_DATA_FOR_CSS, CSSCacheContext.CONTEXT.keySet());
		request.getSession().setAttribute(WebConstant.Param.CACHE_DATA_FOR_JS, JSCacheContext.CONTEXT.keySet());
		return hasCopy;
	}
}
