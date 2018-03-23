package top.coos.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import top.coos.constant.Constant;
import top.coos.exception.CoreException;
import top.coos.tool.entity.EntityTool;
import top.coos.tool.property.PropertyTool;
import top.coos.tool.resolve.ConfigResolveTool;
import top.coos.tool.string.StringHelper;

public class DevServerConfig extends Config {

	public final static DevServerConfig CONFIG = get();

	public static DevServerConfig get() {

		return new DevServerConfig();
	}

	public DevServerConfig() {

		init();
	}

	public void reload() {

		init();
	}

	public Map<String, String> load() throws CoreException {

		File file = new File(Constant.Config.DEV_SERVER_CONFIG_FILE_PATH);
		if (file != null && file.isFile()) {
			try {
				return PropertyTool.load(file);
			} catch (Exception e) {
			}
		}
		return PropertyTool.load(Constant.Config.DEV_SERVER_CONFIG);
	}

	public static void main(String[] args) {

		System.out.println(DevServerConfig.CONFIG.database.real.catalog);

	}

	private void init() {

		try {
			Map<String, String> properties;
			try {
				properties = load();
			} catch (Exception e) {
				properties = new HashMap<String, String>();
			}
			if (properties != null && properties.size() > 0) {
				EntityTool.fullEntity(this, properties, false);
				debug = false;
				if (properties.get("debug") != null && !StringHelper.isEmpty(properties.get("debug"))) {
					debug = properties.get("debug").equals("true") || properties.get("debug").equals("1");
				}
				open_offline_login = true;
				if (properties.get("debug") != null && !StringHelper.isEmpty(properties.get("debug"))) {
					debug = properties.get("debug").equals("true") || properties.get("debug").equals("1");
				}

				Map<String, Object> data = ConfigResolveTool.resolve(properties);
				EntityTool.fullEntity(this, data, false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean debug = false;

	public boolean open_offline_login = true;

	public boolean find_frame = false;

	public String sso_server_url;

	public String api_server_url;

	public Database database = new Database();

	public Cache cache = new Cache();

	public String getSso_server_url() {

		return sso_server_url;
	}

	public void setSso_server_url(String sso_server_url) {

		this.sso_server_url = sso_server_url;
	}

	public String getApi_server_url() {

		return api_server_url;
	}

	public void setApi_server_url(String api_server_url) {

		this.api_server_url = api_server_url;
	}

	public boolean isOpen_offline_login() {

		return open_offline_login;
	}

	public void setOpen_offline_login(boolean open_offline_login) {

		this.open_offline_login = open_offline_login;
	}

	public boolean isFind_frame() {

		return find_frame;
	}

	public void setFind_frame(boolean find_frame) {

		this.find_frame = find_frame;
	}

	public Database getDatabase() {

		return database;
	}

	public void setDatabase(Database database) {

		this.database = database;
	}

	public static class Cache {

		public String model;
		public String host;
		public int port;

		public String getModel() {

			return model;
		}

		public void setModel(String model) {

			this.model = model;
		}

		public String getHost() {

			return host;
		}

		public void setHost(String host) {

			this.host = host;
		}

		public int getPort() {

			return port;
		}

		public void setPort(int port) {

			this.port = port;
		}

	}

	public static class Database {

		public Real real = new Real();

		public Real getReal() {

			return real;
		}

		public void setReal(Real real) {

			this.real = real;
		}

		public static class Real {

			public String url;

			public String driver;

			public String catalog;

			public String username;

			public String password;

			public String getCatalog() {

				return catalog;
			}

			public void setCatalog(String catalog) {

				this.catalog = catalog;
			}

			public String getUrl() {

				return url;
			}

			public void setUrl(String url) {

				this.url = url;
			}

			public String getDriver() {

				return driver;
			}

			public void setDriver(String driver) {

				this.driver = driver;
			}

			public String getUsername() {

				return username;
			}

			public void setUsername(String username) {

				this.username = username;
			}

			public String getPassword() {

				return password;
			}

			public void setPassword(String password) {

				this.password = password;
			}
		}
	}

	public boolean isDebug() {

		return debug;
	}

	public void setDebug(boolean debug) {

		this.debug = debug;
	}

	public Cache getCache() {

		return cache;
	}

	public void setCache(Cache cache) {

		this.cache = cache;
	}

}
