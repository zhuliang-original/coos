package top.coos.tool.value.resolver;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateResolver extends BaseResolver {

	public DateResolver(String rule) {

		super(rule);
	}

	public String resolve() {

		String format = "yyyyMMddHHmmss";
		int first = rule.indexOf("(");
		int end = rule.indexOf(")");

		if (first > 0 && end > first) {
			format = rule.substring(first + 1, end);
		}
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format.trim());
		this.result = dateFormat.format(date);

		return result;
	}
}
