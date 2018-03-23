package top.coos.tool.local;



import top.coos.tool.string.StringHelper;

public class InetAddressInfo {
    private static String DATA = null;

    public static String getHostName() {
        if (StringHelper.isEmpty(DATA)) {
            try {
                java.net.InetAddress a = java.net.InetAddress.getLocalHost();
                DATA = a.getHostName();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (DATA == null) {
                DATA = "";
            }
        }
        return DATA.trim();
    }
}
