package top.coos.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import top.coos.tool.string.StringHelper;

public class PathUtil {

	public static String decode(String path) {

		try {

			if (StringHelper.isEmpty(path)) {
				return null;
			}

			return URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return path;
	}
}
