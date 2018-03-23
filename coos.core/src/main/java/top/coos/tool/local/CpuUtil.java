package top.coos.tool.local;



import java.util.Scanner;

import top.coos.tool.string.StringHelper;

public class CpuUtil {
    private static String DATA = null;
	private static Scanner sc;

    /**
     * 获取CPU序列号
     * 
     * @return
     */
    public static String getCPUSerial() {
        if (StringHelper.isEmpty(DATA)) {
            try {
                Process process = Runtime.getRuntime().exec(new String[] { "wmic", "cpu", "get", "ProcessorId" });
                process.getOutputStream().close();
                sc = new Scanner(process.getInputStream());
                sc.next();
                DATA = sc.next();
            } catch (Exception e) {
            }
            if (DATA == null || DATA.trim().length() < 1) {
                DATA = "";
            }
        }
        return DATA.trim();
    }

}
