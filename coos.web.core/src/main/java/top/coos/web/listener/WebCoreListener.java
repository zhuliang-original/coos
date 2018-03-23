package top.coos.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import top.coos.web.cache.ResourceCacheContext;
import top.coos.web.constant.WebConstant;
import top.coos.web.core.WebCorePackageInfo;

/**
 * 程序初始化安装监听
 * 
 * @author 朱亮
 * 
 */
@javax.servlet.annotation.WebListener
public class WebCoreListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {

		ResourceCacheContext.CONTEXT.put(WebCorePackageInfo.PATH, WebConstant.Page.WEB_INF_VIEW_CORE_PUBLIC_JSP);

	}

}
