package top.coos.tool.md5;

public class PasswordMD5Tool {

	public static final String DEFAULT_PASSWORD = "123456";

	public static String getMD5Password(String password) {

		return MD5Tool.MD5(password).toUpperCase();
	}
}
