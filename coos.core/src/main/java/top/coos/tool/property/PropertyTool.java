package top.coos.tool.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import top.coos.exception.CoreException;

public class PropertyTool {

	public static Map<String, String> load(File file) throws Exception {

		return load(new FileInputStream(file));
	}

	public static Map<String, String> load(InputStream stream) throws CoreException {

		try {
			Map<String, String> data = new HashMap<String, String>();
			Properties props = new Properties();
			props.load(stream);
			for (Object key : props.keySet()) {
				String keyName = "" + key;
				String value = props.getProperty(keyName);
				data.put(keyName, value);

			}
			return data;
		} catch (IOException e) {
			throw new CoreException(e);
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (Exception e) {
			}
		}
	}

	public static Map<String, String> load(String name) throws CoreException {

		InputStream stream = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			Properties props = new Properties();
			stream = PropertyTool.class.getClassLoader().getResourceAsStream(name);
			props.load(stream);
			for (Object key : props.keySet()) {
				String keyName = "" + key;
				String value = props.getProperty(keyName);
				data.put(keyName, value);

			}
			return data;
		} catch (IOException e) {
			throw new CoreException(e);
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (Exception e) {
			}
		}
	}

	public static Properties get(String name) throws IOException {

		InputStream stream = null;
		try {

			Properties props = new Properties();
			props.load(PropertyTool.class.getClassLoader().getResourceAsStream(name));
			return props;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (Exception e) {
			}
		}
	}
}
