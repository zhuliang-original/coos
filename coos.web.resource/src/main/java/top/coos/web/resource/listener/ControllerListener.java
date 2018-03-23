package top.coos.web.resource.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 程序初始化安装监听
 * 
 * @author 朱亮
 * 
 */
@WebListener
public class ControllerListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        // ServletContext context = event.getServletContext();

    }
}
