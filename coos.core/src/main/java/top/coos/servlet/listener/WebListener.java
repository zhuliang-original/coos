package top.coos.servlet.listener;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;

import top.coos.servlet.DefaultServlet;
import top.coos.servlet.context.ControllerContext;
import top.coos.tool.string.StringHelper;

@javax.servlet.annotation.WebListener
public class WebListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextInitialized(ServletContextEvent event) {

        ServletContext context = event.getServletContext();
        try {
            Map<String, ?> servletRegistrationMap = context.getServletRegistrations();
            if (servletRegistrationMap != null) {
                for (String key : servletRegistrationMap.keySet()) {
                    Object value = servletRegistrationMap.get(key);
                    if (value instanceof ServletRegistration) {
                        ServletRegistration registration = (ServletRegistration) value;
                        String className = registration.getClassName();
                        if (!StringHelper.isEmpty(className)) {
                            try {
                                Class<?> clazz = Class.forName(className);
                                if (DefaultServlet.class.isAssignableFrom(clazz)) {
                                    ControllerContext.get().addControl(clazz);
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }

    }

}
