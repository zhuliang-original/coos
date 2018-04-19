package top.coos.bean.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import top.coos.Support;
import top.coos.bean.permission.PermissionMenu;
import top.coos.bean.permission.PermissionOperate;
import top.coos.tool.string.StringHelper;

public class Client extends Support {

	private static final long serialVersionUID = 7038978348899755221L;

	private String sessionid;

	private String userid;

	private Map<String, Object> user;

	private Map<String, Object> object_map;

	private String staticvalue_json;

	private String user_json;

	private String login_token;

	private boolean isLogin;

	private boolean staticvaluecache;

	private Map<String, Object> cache = new HashMap<String, Object>();

	private String cache_json;

	private List<PermissionOperate> operates = new ArrayList<PermissionOperate>();

	private Map<String, PermissionOperate> operate_map = new HashMap<String, PermissionOperate>();

	private List<PermissionOperate> all_operates = new ArrayList<PermissionOperate>();

	private Map<String, PermissionOperate> all_operate_map = new HashMap<String, PermissionOperate>();

	private List<PermissionMenu> menus = new ArrayList<PermissionMenu>();

	private String menus_json;

	public String getLogin_token() {

		return login_token;
	}

	public void setLogin_token(String login_token) {

		this.login_token = login_token;
	}

	public String getUserid() {

		return userid;
	}

	public void setUserid(String userid) {

		this.userid = userid;
	}

	public Object getObject(String key) {

		object_map = object_map == null ? new HashMap<String, Object>() : object_map;
		return object_map.get(key);
	}

	public String getCache_json() {

		return cache_json;
	}

	public void setCache_json(String cache_json) {

		this.cache_json = cache_json;
	}

	public Map<String, Object> putCache(String key, Object value) {

		cache = cache == null ? new HashMap<String, Object>() : cache;
		cache.put(key, value);
		cache_json = null;
		if (cache != null) {
			cache_json = JSONObject.fromObject(cache).toString();
		}
		return cache;
	}

	public Object getCache(String key) {

		cache = cache == null ? new HashMap<String, Object>() : cache;
		return cache.get(key);
	}

	public String getUser_json() {

		return user_json;
	}

	public void setUser_json(String user_json) {

		this.user_json = user_json;
	}

	public Map<String, Object> getUser() {

		return user;
	}

	public void setUser(Map<String, Object> user) {

		user_json = null;
		if (user != null) {
			user.remove("loginpassword");
			user_json = JSONObject.fromObject(user).toString();
			this.isLogin = true;
		}
		this.user = user;
	}

	public String getSessionid() {

		return sessionid;
	}

	public void setSessionid(String sessionid) {

		this.sessionid = sessionid;
	}

	public String getStaticvalue_json() {

		return staticvalue_json;
	}

	public void setStaticvalue_json(String staticvalue_json) {

		this.staticvalue_json = staticvalue_json;
	}

	public Map<String, Object> getCache() {

		return cache;
	}

	public void setCache(Map<String, Object> data) {

		cache_json = null;
		if (data != null) {
			cache_json = JSONObject.fromObject(data).toString();
		}
		this.cache = data;
	}

	public List<PermissionMenu> getMenus() {

		return menus;
	}

	public void setMenus(List<PermissionMenu> menus) {

		menus_json = null;
		if (menus != null) {
			menus_json = JSONArray.fromObject(menus).toString();
		}
		this.menus = menus;
	}

	public String getMenus_json() {

		return menus_json;
	}

	public void setMenus_json(String menus_json) {

		this.menus_json = menus_json;
	}

	public boolean isLogin() {

		return isLogin;
	}

	public void setLogin(boolean isLogin) {

		this.isLogin = isLogin;
	}

	public Map<String, Object> getObject_map() {

		return object_map;
	}

	public void setObject_map(Map<String, Object> object_map) {

		this.object_map = object_map;
	}

	public List<PermissionOperate> getOperates() {

		return operates;
	}

	public void setOperates(List<PermissionOperate> operates) {

		operate_map = new HashMap<String, PermissionOperate>();
		if (operates != null) {
			for (PermissionOperate operate : operates) {
				if (!StringHelper.isEmpty(operate.getServletpath())) {
					operate_map.put(operate.getServletpath(), operate);
				}

			}
		}
		this.operates = operates;
	}

	public void setAll_operates(List<PermissionOperate> all_operates) {

		all_operate_map = new HashMap<String, PermissionOperate>();
		if (all_operates != null) {
			for (PermissionOperate all_operate : all_operates) {
				if (!StringHelper.isEmpty(all_operate.getServletpath())) {
					all_operate_map.put(all_operate.getServletpath(), all_operate);
				}

			}
		}
		this.all_operates = all_operates;
	}

	public List<PermissionOperate> getAll_operates() {

		return all_operates;
	}

	public Map<String, PermissionOperate> getAll_operate_map() {

		return all_operate_map;
	}

	public Map<String, PermissionOperate> getOperate_map() {

		return operate_map;
	}

	public boolean isStaticvaluecache() {

		return staticvaluecache;
	}

	public void setStaticvaluecache(boolean staticvaluecache) {

		this.staticvaluecache = staticvaluecache;
	}

	public static long getSerialversionuid() {

		return serialVersionUID;
	}

}
