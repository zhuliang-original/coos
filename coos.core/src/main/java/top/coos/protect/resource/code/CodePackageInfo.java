package top.coos.protect.resource.code;


public class CodePackageInfo {

    public static String NAME = CodePackageInfo.class.getPackage().getName();

    public static String PATH = NAME.replaceAll("\\.", "/");
}
