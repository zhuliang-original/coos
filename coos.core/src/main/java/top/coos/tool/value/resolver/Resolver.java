package top.coos.tool.value.resolver;

import java.util.HashMap;
import java.util.Map;

public abstract class Resolver {

	protected final String rule;

	protected String result;

	protected Map<String, String> requestdata = new HashMap<String, String>();

	protected Map<String, Object> sessiondata = new HashMap<String, Object>();

	protected Map<String, Object> cachedata = new HashMap<String, Object>();

	public Resolver(String rule) {

		this.rule = rule;
		this.result = rule;
	}

	public abstract String resolve();

	public Map<String, String> getRequestdata() {

		return requestdata;
	}

	public void setRequestdata(Map<String, String> requestdata) {

		this.requestdata = requestdata;
	}

	public Map<String, Object> getSessiondata() {

		return sessiondata;
	}

	public void setSessiondata(Map<String, Object> sessiondata) {

		this.sessiondata = sessiondata;
	}

	public Map<String, Object> getCachedata() {

		return cachedata;
	}

	public void setCachedata(Map<String, Object> cachedata) {

		this.cachedata = cachedata;
	}

}
