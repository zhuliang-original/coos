package top.coos.tool.sql;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import top.coos.exception.CoreException;
import top.coos.tool.value.ValueTool;

public class SqlTool {

    public static StringBuffer formatSqlAndParam(StringBuffer sqlBuffer, Map<String, Object> paramMap, Map<String, String> requestmap, Map<String, Object> cachedata) throws CoreException {
        String sql = sqlBuffer.toString();
        String newSql = sqlBuffer.toString();
        Pattern pattern = Pattern.compile("(:data\\{)[^\\}]+(\\})");
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            String param = matcher.group();
            param = param.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll(":data", "");
            String setParam = param;
            String value = ValueTool.getValue(param, requestmap, cachedata);

            if (value != null) {
                paramMap.put("data{" + setParam + "}", value);
            } else {
                throw new CoreException(sql + "中参数data{" + setParam + "}值不存在！");
            }
        }

        pattern = Pattern.compile("(sql\\{)[^\\}]+(\\})");
        matcher = pattern.matcher(sql);
        while (matcher.find()) {
            String param = matcher.group();
            param = param.replaceAll("\\{", "").replaceAll("\\}", "").replaceFirst("sql", "");
            String setParam = param;
            String value = ValueTool.getValue(param, requestmap, cachedata);
            if (value != null) {
                newSql = newSql.replace("sql{" + param + "}", value);
            } else {
                throw new CoreException(sql + "中参数sql{" + setParam + "}值不存在！");
            }
        }
        sqlBuffer.setLength(0);
        sqlBuffer.append(newSql);
        return sqlBuffer;
    }

}
