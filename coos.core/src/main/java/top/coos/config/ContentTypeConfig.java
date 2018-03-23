package top.coos.config;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import top.coos.constant.Constant;
import top.coos.tool.property.PropertyTool;

public class ContentTypeConfig {

    public static Properties CONTENT_TYPE_PROPERTIES;

    public static void read() {
        if (CONTENT_TYPE_PROPERTIES == null) {
            CONTENT_TYPE_PROPERTIES = new Properties();
            try {
                CONTENT_TYPE_PROPERTIES = PropertyTool.get(Constant.Config.FILE_CONTENT_TYPE);
            } catch (IOException e) {
                CONTENT_TYPE_PROPERTIES = new Properties();
            }
        }
    }

    /**
     * 获取文件对应的 http流类型
     * 
     * @param fileSuffix
     * @return
     */
    public static String getFileContentType(String fileSuffix) {
        read();
        return (String) CONTENT_TYPE_PROPERTIES.get(fileSuffix);
    }

    /**
     * 获取文件对应的 http流类型
     * 
     * @param fileSuffix
     * @return
     */
    public static String getFileSuffix(String contentType) {
        read();
        String fileSuffix = "";
        Set<?> type = CONTENT_TYPE_PROPERTIES.entrySet();
        Iterator<?> it = type.iterator();
        while (it.hasNext()) {
            Map.Entry<?, ?> entity = (Entry<?, ?>) it.next();
            if (entity.getValue().equals(contentType)) {
                fileSuffix = (String) entity.getKey();
                break;
            }
        }
        return fileSuffix;
    }
}
