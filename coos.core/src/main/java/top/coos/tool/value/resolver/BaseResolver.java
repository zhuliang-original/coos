package top.coos.tool.value.resolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import top.coos.tool.string.StringExecute;
import top.coos.tool.string.StringHelper;

public class BaseResolver extends Resolver {

	public BaseResolver(String rule) {

		super(rule);
	}

	public String resolve() {

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("session", sessiondata);
		data.put("request", requestdata);
		data.put("cache", cachedata);

		if (!StringHelper.isEmpty(this.result)) {
			String rule = this.rule;
			rule = rule.replace("#{", "");
			rule = rule.replace("}", "");
			Object ruleResult = StringExecute.invokeMethod(rule, data);
			String ruleValue = null;
			if (ruleResult != null) {
				if (ruleResult instanceof Map) {
					ruleValue = JSONObject.fromObject(ruleResult).toString();
				} else if (ruleResult instanceof Set || ruleResult instanceof List || ruleResult instanceof Object[]) {
					ruleValue = JSONArray.fromObject(ruleResult).toString();
				} else {
					ruleValue = "" + ruleResult;
				}
			}
			this.result = ruleValue;

		}
		return this.result;
	}
}
