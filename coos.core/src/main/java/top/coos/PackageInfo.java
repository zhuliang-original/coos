package top.coos;

public abstract class PackageInfo {

    public static String NAME = PackageInfo.class.getPackage().getName();

    public static String PATH = NAME.replaceAll("\\.", "/");
}
