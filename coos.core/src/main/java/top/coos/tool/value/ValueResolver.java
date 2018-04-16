package top.coos.tool.value;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import top.coos.tool.string.StringHelper;
import top.coos.tool.value.resolver.BaseResolver;
import top.coos.tool.value.resolver.Resolver;

public class ValueResolver {

	private final String value;

	private String result;

	private boolean noValueThrowException;

	private Map<String, String> requestdata = new HashMap<String, String>();

	private Map<String, Object> sessiondata = new HashMap<String, Object>();

	private Map<String, Object> cachedata = new HashMap<String, Object>();

	private final Map<String, Object> resultParam = new HashMap<String, Object>();

	public ValueResolver(String value) {

		this(value, false);
	}

	public ValueResolver(String value, boolean noValueThrowException) {

		this.value = value;
		this.result = value;
		this.noValueThrowException = noValueThrowException;
	}

	public String resolve() throws Exception {

		this.resultParam.clear();
		if (!StringHelper.isEmpty(this.result)) {
			Pattern pattern = Pattern.compile("(#\\{)[^\\}]+(\\})");
			Matcher matcher = pattern.matcher(this.result);
			while (matcher.find()) {
				String matchParam = matcher.group();
				boolean isSqlParam = false;
				if (matcher.start() > 0) {
					if (this.value.substring(matcher.start() - 1, matcher.start()).equals(":")) {
						isSqlParam = true;
					}
				}
				String matchRule = matchParam.replace("#{", "");
				matchRule = matchRule.replace("}", "");
				String resolverResult = resolverResult(matchRule);
				if (resolverResult == null) {
					if (noValueThrowException) {
						throw new Exception(matchParam + " value is null");
					}
					if (!isSqlParam) {
						resolverResult = "";
					}
				}
				if (isSqlParam) {
					this.resultParam.put(matchParam, resolverResult);
				} else {
					this.result = this.result.replace(matchParam, resolverResult);

				}
			}

		}
		return this.result;
	}

	private String resolverResult(String matchRule) throws Exception {

		matchRule = matchRule.trim();
		Resolver resolver = null;

		for (ValueRule valueRule : ValueRule.RULES) {
			if (matchRule.toLowerCase().indexOf(valueRule.getRule().toLowerCase()) == 0) {
				Class<?> resolveClazz = valueRule.getResolverClazz();
				Constructor<?> constructor = resolveClazz.getConstructor(new Class[] { String.class });
				resolver = (Resolver) constructor.newInstance(matchRule);
				break;
			}
		}
		if (resolver == null) {
			resolver = new BaseResolver(matchRule);
		}
		resolver.setCachedata(cachedata);
		resolver.setRequestdata(requestdata);
		resolver.setSessiondata(sessiondata);
		return resolver.resolve();
	}

	public static void main(String[] args) throws Exception {

		String sql = " select * from user where userid=#{cache.userid} ";
		sql += " and starttime >= #{now_date} and starttime<= #{now_date+1d} ";
		sql += " and type=:#{request.type} ";

		ValueResolver resolver = new ValueResolver(sql);
		resolver.getCachedata().put("userid", "123123");
		resolver.getRequestdata().put("type", "1");
		System.out.println(resolver.resolve());
		System.out.println(resolver.getResultParam());

	}

	public Map<String, String> getRequestdata() {

		return requestdata;
	}

	public void setRequestdata(Map<String, String> requestdata) {

		this.requestdata = requestdata;
	}

	public Map<String, Object> getCachedata() {

		return cachedata;
	}

	public void setCachedata(Map<String, Object> cachedata) {

		this.cachedata = cachedata;
	}

	public String getValue() {

		return value;
	}

	public String getResult() {

		return result;
	}

	public Map<String, Object> getResultParam() {

		return resultParam;
	}

}
