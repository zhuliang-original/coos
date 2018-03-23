package top.coos.tool.charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import top.coos.constant.Constant;
import top.coos.tool.property.PropertyTool;

/**
 */
public class CharsetTool {

    private static final Map<Integer, String> INDEX_TO_CHARSET = new HashMap<>();

    private static final Map<String, Integer> CHARSET_TO_INDEX = new HashMap<>();
    static {

        // index_to_charset.properties
        INDEX_TO_CHARSET.put(1, "big5");
        INDEX_TO_CHARSET.put(8, "latin1");
        INDEX_TO_CHARSET.put(9, "latin2");
        INDEX_TO_CHARSET.put(14, "cp1251");
        INDEX_TO_CHARSET.put(28, "gbk");
        INDEX_TO_CHARSET.put(24, "gb2312");
        INDEX_TO_CHARSET.put(33, "utf8");
        INDEX_TO_CHARSET.put(45, "utf8mb4");

        Properties prop = new Properties();
        try {
            prop = PropertyTool.get(Constant.Config.INDEX_TO_CHARSET);
            for (Object index : prop.keySet()) {
                INDEX_TO_CHARSET.put(Integer.parseInt((String) index), prop.getProperty((String) index));
            }
        } catch (Exception e) {
        }

        // charset --> index
        for (Integer key : INDEX_TO_CHARSET.keySet()) {
            String charset = INDEX_TO_CHARSET.get(key);
            if (charset != null && CHARSET_TO_INDEX.get(charset) == null) {
                CHARSET_TO_INDEX.put(charset, key);
            }
        }

        CHARSET_TO_INDEX.put("iso-8859-1", 14);
        CHARSET_TO_INDEX.put("iso_8859_1", 14);
        CHARSET_TO_INDEX.put("utf-8", 33);
    }

    public static final String getCharset(int index) {
        return INDEX_TO_CHARSET.get(index);
    }

    public static final int getIndex(String charset) {
        if (charset == null || charset.length() == 0) {
            return 0;
        } else {
            Integer i = CHARSET_TO_INDEX.get(charset.toLowerCase());
            return (i == null) ? 0 : i;
        }
    }

}
