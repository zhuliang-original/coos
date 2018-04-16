package top.coos.tool.value;

import java.lang.reflect.Method;
import java.util.Map;

import top.coos.tool.string.StringHelper;

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

		ValueResolver resolver = new ValueResolver(value, false);
		resolver.setCachedata(cachedata);
		resolver.setRequestdata(requestmap);
		try {
			value = resolver.resolve();
		} catch (Exception e) {
			e.printStackTrace();
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
				if (method.getName().startsWith("get")
						&& method.getName().toUpperCase().equals(("get" + name).toUpperCase())) {
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
