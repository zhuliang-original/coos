package top.coos.web.resource;


public class WebResourcePackageInfo {

    public static String NAME = WebResourcePackageInfo.class.getPackage().getName();

    public static String PATH = NAME.replaceAll("\\.", "/");

}
