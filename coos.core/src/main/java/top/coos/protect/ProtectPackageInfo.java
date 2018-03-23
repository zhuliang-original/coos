package top.coos.protect;

public abstract class ProtectPackageInfo {

    public static String NAME = ProtectPackageInfo.class.getPackage().getName();

    public static String PATH = NAME.replaceAll("\\.", "/");
}
