package top.coos.tool.resolve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class ConfigResolveTool {

	public static void main(String[] args) {

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("user/2.0/192.168.1.115:3076:1", 12);
		data.put("user", 12);
		data.put("user/2.0", 12);
		data.put("user/2.0/192.168.1.115:3076:2", 12);

		System.out.println(JSONObject.fromObject(resolve(data, "/")));
	}

	public static Map<String, Object> resolve(Map<String, ?> param) {

		ResolveParam resolveParam = new ResolveParam(param, ".");

		return resolveParam.resolve();
	}

	public static Map<String, Object> resolve(Map<String, ?> param, String rule) {

		ResolveParam resolveParam = new ResolveParam(param, rule);

		return resolveParam.resolve();
	}

}

class ResolveParam {

	private String rule;

	private Map<String, ?> param;

	private Map<String, Object> result = new HashMap<String, Object>();

	private List<Map<String, Object>> cacheValues = new ArrayList<Map<String, Object>>();

	public ResolveParam(Map<String, ?> param, String rule) {

		this.param = param;
		this.rule = rule;
	}

	public Map<String, Object> resolve() {

		init();
		resolveResult();
		return result;
	}

	private void init() {

		result = new HashMap<String, Object>();
		param = param == null ? new HashMap<String, Object>() : param;
	}

	private void resolveResult() {

		for (String key : param.keySet()) {
			cacheValues = new ArrayList<Map<String, Object>>();
			resolveResult(key, 0, param.get(key));
		}
	}

	@SuppressWarnings("unchecked")
	private void resolveResult(String key, int index, Object value) {

		if (key.indexOf(rule) > 0) {
			String[] names = null;
			if (rule.equals(".")) {
				names = key.split("\\.");
			} else {
				names = key.split(rule);
			}
			if (index >= names.length) {
				result.put(names[0], cacheValues.get(0));
				return;
			}
			String name = names[index];
			if (index < (names.length)) {
				Map<String, Object> data = new HashMap<String, Object>();
				if (index == 0) {
					if (result.get(name) instanceof Map) {
						data = (Map<String, Object>) result.get(name);
					} else {
						data = new HashMap<String, Object>();

					}
				} else {
					Map<String, Object> lastData = cacheValues.get(index - 1);
					if (lastData.get(name) instanceof Map) {
						data = (Map<String, Object>) lastData.get(name);
					} else {
						data = new HashMap<String, Object>();

					}
				}
				if (data == null) {
					data = new HashMap<String, Object>();
				}
				cacheValues.add(data);
			}
			if (index == 0) {

			} else {

				Map<String, Object> lastData = cacheValues.get(index - 1);
				if (index == (names.length - 1)) {
					if (lastData.get(name) != null && lastData.get(name) instanceof Map) {
					} else {
						lastData.put(name, value);
					}
				} else {
					if (lastData.get(name) != null && lastData.get(name) instanceof Map) {
					} else {
						lastData.put(name, cacheValues.get(index));
					}
				}
			}
			int index_ = index + 1;
			resolveResult(key, index_, value);

		} else {
			if (result.get(key) != null && result.get(key) instanceof Map) {
			} else {
				result.put(key, value);
			}
		}
	}
}