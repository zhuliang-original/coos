package top.coos.protect.resource;


public class ResourcePackageInfo {

    public static String NAME = ResourcePackageInfo.class.getPackage().getName();

    public static String PATH = NAME.replaceAll("\\.", "/");
}
