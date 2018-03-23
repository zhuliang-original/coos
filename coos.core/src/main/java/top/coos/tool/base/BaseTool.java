package top.coos.tool.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import top.coos.tool.string.StringHelper;


/**
 * 基本工具类
 * 
 * @author 朱亮
 * 
 */
public class BaseTool {

    private static Map<String, String> RANDOM_NUMBER_MAP = new HashMap<String, String>();

    private static long RANDOM_NUMBER_TIME = System.currentTimeMillis();

    private static int RANDOMNUMBER = 9;

    /**
     * 清空非数字
     * 
     * @param str
     * @return
     */
    public static String clearNoNumber(String str) {
        if (!StringHelper.isEmpty(str)) {
            return str.replaceAll("(\\D)+", "");
        }
        return null;
    }

    /**
     * 获取永不重复的随机数 16位
     * 
     * @return
     */
    public synchronized static String getRandomNumber() {
        long thistime = System.currentTimeMillis();
        //
        if ((thistime - RANDOM_NUMBER_TIME) >= 1000) {
            RANDOM_NUMBER_MAP = new HashMap<String, String>();
            RANDOM_NUMBER_TIME = thistime;
        }
        String number;
        while (true) {
            if (RANDOMNUMBER > 98) {
                RANDOMNUMBER = 9;
            }
            RANDOMNUMBER++;
            number = thistime - 120000000000L + "" + (new Random().nextInt(10)) + "" + (new Random().nextInt(10)) + RANDOMNUMBER;
            if (RANDOM_NUMBER_MAP.get(number) == null) {
                RANDOM_NUMBER_MAP.put(number, number);
                break;
            }
        }
        return number;
    }

}
