package top.coos.tool.value;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.coos.tool.md5.MD5Tool;
import top.coos.tool.string.StringExecute;
import top.coos.tool.string.StringHelper;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ValueTool {

    /**
     * 清空非数字
     * 
     * @param str
     * @return
     */
    public static String clearNoNumber(String str) {
        if (!StringHelper.isEmpty(str)) {
            return str.replaceAll("(\\D)+", "");
        }
        return null;
    }

    public static String formatFunctionValue(String string) {

        if (StringHelper.isEmpty(string)) {
            return string;
        }
        if (string.indexOf("$") >= 0) {

            if (string.indexOf("$nowdatetime") >= 0) {
                String value = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                string = string.replaceAll("\\$nowdatetime", value);

            }
            if (string.indexOf("$nowdate") >= 0) {
                String value = new SimpleDateFormat("yyyyMMdd").format(new Date());
                string = string.replaceAll("\\$nowdate", value);

            }
            if (string.equals("$nowtime")) {
                String value = new SimpleDateFormat("HHmmss").format(new Date());
                string = string.replaceAll("\\$nowtime", value);
            }
            if (string.equals("$nowhour")) {
                String value = new SimpleDateFormat("HH").format(new Date());
                string = string.replaceAll("\\$nowhour", value);
            }
            if (string.equals("$nowminute")) {
                String value = new SimpleDateFormat("mm").format(new Date());
                string = string.replaceAll("\\$nowminute", value);
            }
        }
        return string;
    }

    /**
     * 根据参数字符串获取值
     * 
     * @param oneparameterstr
     * @param requestmap
     * @param sessionmap
     * @return pname参数名称 pvaluename取值的参数名称 value值
     */
    public static String getValue(String value, Map<String, String> requestmap, Map<String, Object> cachedata) {
        if (StringHelper.isEmpty(value)) {
            return null;
        }
        boolean needmd5 = false;
        if (value.indexOf("$md5-") >= 0) {
            value = value.replaceAll("\\$md5\\-", "");
            needmd5 = true;
        }
        value = formatFunctionValue(value);
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("r", requestmap);
            param.put("request", requestmap);
            param.put("requestmap", requestmap);
            param.put("cache", cachedata);
            param.put("cachedata", cachedata);
            param.put("s", cachedata);
            param.put("sesstion", cachedata);
            Object result = StringExecute.invokeMethod(value, param);
            if (result != null) {
                if (result instanceof Map) {
                    value = JSONObject.fromObject(result).toString();
                } else if (result instanceof List || result instanceof Object[]) {
                    value = JSONArray.fromObject(result).toString();
                } else {
                    value = "" + result;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
        }
        if (needmd5 && !StringHelper.isEmpty(value)) {
            value = MD5Tool.MD5(value);
        }
        return value;

    }

    /**
     * 获取request值
     * 
     * @param requestmap
     * @param name
     * @return
     */
    public static String getRequestValue(Map<String, String> requestmap, String name) {
        if (requestmap != null && requestmap.get(name) != null) {
            return requestmap.get(name);
        }
        return null;
    }

    public static Object getValue(Object object, String name) {
        try {
            String[] names = new String[] { name };
            if (name.indexOf(".") > 0) {
                names = name.split("\\.");
            }
            return getValue(object, names, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private static Object getValue(Object object, String[] names, int index) {
        try {
            if (object == null) {
                return null;
            }
            if (names == null || names.length < 1) {
                return null;
            }
            if (index >= names.length) {
                return null;
            }
            String name = names[index];
            Method[] methods = object.getClass().getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if (method.getName().startsWith("get") && method.getName().toUpperCase().equals(("get" + name).toUpperCase())) {
                    Object objectvalue = method.invoke(object);
                    if (objectvalue == null) {
                        return null;
                    }
                    if (index < (names.length - 1)) {
                        return getValue(objectvalue, names, index + 1);
                    } else {
                        return objectvalue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}
