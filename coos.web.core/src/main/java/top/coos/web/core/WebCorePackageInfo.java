package top.coos.web.core;

public class WebCorePackageInfo {

	public static String NAME = WebCorePackageInfo.class.getPackage().getName();

	public static String PATH = NAME.replaceAll("\\.", "/");

	public static final String SERVLET_FOLDER = "/core";

	public static final String PAGE_FOLDER = "";
}
