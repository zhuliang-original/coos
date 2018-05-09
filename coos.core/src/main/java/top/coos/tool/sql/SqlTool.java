package top.coos.tool.sql;

import java.util.Map;
import top.coos.exception.CoreException;
import top.coos.tool.value.ValueResolver;

public class SqlTool {

	public static StringBuffer formatSqlAndParam(StringBuffer sqlBuffer, Map<String, Object> paramMap,
			Map<String, String> requestmap, Map<String, Object> cachedata) throws CoreException {

		String sql = sqlBuffer.toString();
		ValueResolver resolver = new ValueResolver(sql, true);
		resolver.setCachedata(cachedata);
		resolver.setRequestdata(requestmap);
		try {
			sql = resolver.resolve();
		} catch (Exception e) {
			throw new CoreException(e);
		}
		for (String key : resolver.getResultParam().keySet()) {
			paramMap.put(key, resolver.getResultParam().get(key));
		}
		sqlBuffer.setLength(0);
		sqlBuffer.append(sql);
		return sqlBuffer;

	}
}
