package top.coos.servlet.handle;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import top.coos.cache.factory.CacheContextFactory;
import top.coos.exception.CoreException;
import top.coos.servlet.mapping.RequestMapping;
import top.coos.servlet.model.ModelAndView;
import top.coos.servlet.model.ModelMap;
import top.coos.servlet.model.ModelAndView.Type;
import top.coos.tool.entity.EntityTool;

public class RequestMappingHandle {
    private HttpServletRequest request;

    private HttpServletResponse response;

    private List<RequestMapping> methods;

    public RequestMappingHandle(HttpServletRequest request, HttpServletResponse response, List<RequestMapping> methods) {
        this.methods = methods;
        this.response = response;
        this.request = request;
    }

    public void execute() throws CoreException {
        for (RequestMapping method : methods) {

            execute(method);
        }
    }

    private Map<String, Object> REQEUST_PARAMETER = null;

    private void initParameter() {
        if (REQEUST_PARAMETER != null) {
            return;
        }
        Map<String, String[]> reqeustparameterss = request.getParameterMap();
        REQEUST_PARAMETER = new HashMap<String, Object>();
        for (String name : reqeustparameterss.keySet()) {
            String[] values = reqeustparameterss.get(name);
            if (values != null && values.length > 0) {
                String value = values[0];
                try {
                    if (!(java.nio.charset.Charset.forName("GBK").newEncoder().canEncode(value))) {
                        try {
                            value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                }
                REQEUST_PARAMETER.put(name, value);
            }
        }
    }

    private void execute(RequestMapping handle) throws CoreException {
        try {
            if (handle.getParam_value_map() != null && handle.getParam_value_map().size() > 0) {
                for (String key : handle.getParam_value_map().keySet()) {
                    request.setAttribute(key, handle.getParam_value_map().get(key));
                }
            }
            Method method = handle.getMethod();
            ModelMap modelMap = CacheContextFactory.get(ModelMap.class);
            Class<?>[] methodparametertypes = method.getParameterTypes();
            Object[] methodparameters = new Object[methodparametertypes.length];

            for (int i = 0; i < methodparametertypes.length; i++) {
                Class<?> parametertype = methodparametertypes[i];
                if (parametertype.equals(HttpServletRequest.class)) {
                    methodparameters[i] = request;
                } else if (parametertype.equals(HttpServletResponse.class)) {
                    methodparameters[i] = response;
                } else if (parametertype.equals(ModelMap.class)) {
                    methodparameters[i] = modelMap;
                } else {
                    if (!parametertype.equals(Map.class)) {
                        try {
                            initParameter();
                            Object object = EntityTool.getEntity(parametertype, REQEUST_PARAMETER, false);
                            methodparameters[i] = object;
                        } catch (Exception e) {

                        }
                    }
                }
            }
            Object result = method.invoke(handle.createController(), methodparameters);
            if (modelMap != null && modelMap.size() > 0) {
                for (String name : modelMap.keySet()) {
                    request.setAttribute(name, modelMap.get(name));
                }
            }
            if (result != null) {
                if (result instanceof ModelAndView) {
                    ModelAndView view = (ModelAndView) result;
                    Type type = view.getType();
                    Object value = view.getValue();
                    if (type.equals(ModelAndView.Type.JSON)) {
                        PrintWriter out = response.getWriter();
                        out.println(JSONObject.fromObject(value));
                        out.close();
                    } else if (type.equals(ModelAndView.Type.CONTENT)) {
                        PrintWriter out = response.getWriter();
                        out.print(value);
                        out.close();
                    } else if (type.equals(ModelAndView.Type.PAGE)) {
                        request.getRequestDispatcher(String.valueOf(value)).forward(request, response);
                    } else if (type.equals(ModelAndView.Type.FORWARD)) {
                        request.getRequestDispatcher(String.valueOf(value)).forward(request, response);
                    } else if (type.equals(ModelAndView.Type.REDIRECT)) {
                        response.sendRedirect(String.valueOf(value));
                    }
                } else if (result instanceof String) {
                    request.getRequestDispatcher(String.valueOf(result)).forward(request, response);
                } else {
                    PrintWriter out = response.getWriter();
                    out.println(JSONObject.fromObject(result));
                    out.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
