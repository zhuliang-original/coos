package top.coos.tool.value;

import top.coos.tool.value.resolver.DateResolver;

public enum ValueRule {

	NOW_DATE("now_date", "当前日期", "该函数可以获取当前系统时间，格式为yyyyMMddHHmmss", DateResolver.class),

	;

	public static ValueRule[] RULES = ValueRule.values();

	private ValueRule(String rule, String text, String remark, Class<?> resolverClazz) {

		this.rule = rule;
		this.text = text;
		this.remark = remark;
		this.resolverClazz = resolverClazz;
	}

	private final String rule;

	private final String text;

	private final String remark;

	private final Class<?> resolverClazz;

	public Class<?> getResolverClazz() {

		return resolverClazz;
	}

	public String getRule() {

		return rule;
	}

	public String getText() {

		return text;
	}

	public String getRemark() {

		return remark;
	}

}
