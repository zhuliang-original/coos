package top.coos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.coos.config.DevServerConfig;
import top.coos.constant.Constant;
import top.coos.tool.entity.EntityTool;
import top.coos.tool.property.PropertyTool;
import top.coos.tool.resolve.ConfigResolveTool;
import top.coos.tool.string.StringHelper;

public final class Configuration {

	private final Map<String, String> properties;

	private String projectid;

	private boolean offline;

	public Configuration() {

		this(null);
	}

	public Configuration(Map<String, String> properties) {

		this(null, properties);
	}

	public Configuration(String projectid, Map<String, String> properties) {

		if (properties == null) {
			try {
				properties = PropertyTool.load(Constant.Config.CORE_CONFIG);
			} catch (Exception e) {
				properties = new HashMap<String, String>();
			}
		}
		if (StringHelper.isEmpty(projectid)) {
			projectid = properties != null ? properties.get("projectid") : null;
			if (StringHelper.isEmpty(projectid)) {
				projectid = null;
			}
		}
		this.projectid = projectid;
		this.properties = properties;
		init();
	}

	private void init() {

		this.offline = false;
		if (properties.get("offline") != null) {
			String offline = properties.get("offline");
			this.offline = offline.equals("true") || offline.equals("1");
		}
		Map<String, Object> data = ConfigResolveTool.resolve(properties);
		try {

			EntityTool.fullEntity(this, data, false);
			DevServerConfig devConfig = DevServerConfig.CONFIG;
			if (devConfig != null) {
				if (!StringHelper.isEmpty(devConfig.api_server_url)) {
					this.server.api_server_url = devConfig.api_server_url;
				}
				if (!StringHelper.isEmpty(devConfig.sso_server_url)) {
					this.server.sso_server_url = devConfig.sso_server_url;
				}
			}
			if (StringHelper.isEmpty(projectid) && !StringHelper.isEmpty(this.project.id)) {
				projectid = this.project.id;
			}
		} catch (Exception e) {
		}
	}

	public String entitypackages = null;

	public String logback_path;

	public Controller controller = new Controller();

	public class Controller {

		public List<String> servlet_patterns = new ArrayList<String>();
	}

	public Service service = new Service();

	public static class Service {

		public String file_upload;

		public String file_info;

		public String getFile_upload() {

			return file_upload;
		}

		public void setFile_upload(String file_upload) {

			this.file_upload = file_upload;
		}

		public String getFile_info() {

			return file_info;
		}

		public void setFile_info(String file_info) {

			this.file_info = file_info;
		}

	}

	public Server server = new Server();

	public static class Server {

		// 当前服务器路径 如:htttp://www.file.com/visit/
		public String this_server_url;

		// 文件服务器路径 如:htttp://www.file.com/file/
		public String file_server_url;

		public String upload_file_server_url;

		public String sso_server_url = "http://sso.coos.top/";

		public String api_server_url = "http://api.coos.top/";

		public String open_file_server_url = "http://file.coos.top/" + Constant.Config.FILE_FOLDER;

		public String open_upload_file_server_url = "http://file.coos.top/";

		public String getThis_server_url() {

			return this_server_url;
		}

		public void setThis_server_url(String this_server_url) {

			this.this_server_url = this_server_url;
		}

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

		public String getFile_server_url() {

			return file_server_url;
		}

		public void setFile_server_url(String file_server_url) {

			this.file_server_url = file_server_url;
		}

		public String getUpload_file_server_url() {

			return upload_file_server_url;
		}

		public void setUpload_file_server_url(String upload_file_server_url) {

			this.upload_file_server_url = upload_file_server_url;
		}

		public String getOpen_file_server_url() {

			return open_file_server_url;
		}

		public void setOpen_file_server_url(String open_file_server_url) {

			this.open_file_server_url = open_file_server_url;
		}

		public String getOpen_upload_file_server_url() {

			return open_upload_file_server_url;
		}

		public void setOpen_upload_file_server_url(String open_upload_file_server_url) {

			this.open_upload_file_server_url = open_upload_file_server_url;
		}

	}

	public Project project = new Project();

	public static class Project {

		public String toIndex = "core/index/toIndex.do";

		public String toLogin = "core/login/toLogin.do";

		public String doLogin = "core/login/doLogin.do";

		public String doUpload = "core/file/file.upload";

		public String to404 = "core/error/404.do";

		public String to500 = "core/error/500.do";

		public String toNoAccess = "core/error/toNoAccess.do";

		public String toNotOnline = "core/error/toNotOnline.do";

		public String id;

		public int pagesize = 10;

		public String url;

		public String internal_url;

		// 开启默认管理 路径以$manager开头
		public boolean open_default_manager;

		// 公共跳转的jsp页面
		public String public_jsp_path;

		public String getToIndex() {

			return toIndex;
		}

		public void setToIndex(String toIndex) {

			this.toIndex = toIndex;
		}

		public String getToLogin() {

			return toLogin;
		}

		public void setToLogin(String toLogin) {

			this.toLogin = toLogin;
		}

		public String getDoLogin() {

			return doLogin;
		}

		public void setDoLogin(String doLogin) {

			this.doLogin = doLogin;
		}

		public String getDoUpload() {

			return doUpload;
		}

		public void setDoUpload(String doUpload) {

			this.doUpload = doUpload;
		}

		public String getTo404() {

			return to404;
		}

		public void setTo404(String to404) {

			this.to404 = to404;
		}

		public String getTo500() {

			return to500;
		}

		public void setTo500(String to500) {

			this.to500 = to500;
		}

		public String getToNoAccess() {

			return toNoAccess;
		}

		public void setToNoAccess(String toNoAccess) {

			this.toNoAccess = toNoAccess;
		}

		public String getToNotOnline() {

			return toNotOnline;
		}

		public void setToNotOnline(String toNotOnline) {

			this.toNotOnline = toNotOnline;
		}

		public String getId() {

			return id;
		}

		public void setId(String id) {

			this.id = id;
		}

		public int getPagesize() {

			return pagesize;
		}

		public void setPagesize(int pagesize) {

			this.pagesize = pagesize;
		}

		public String getUrl() {

			return url;
		}

		public void setUrl(String url) {

			this.url = url;
		}

		public String getInternal_url() {

			return internal_url;
		}

		public void setInternal_url(String internal_url) {

			this.internal_url = internal_url;
		}

		public boolean isOpen_default_manager() {

			return open_default_manager;
		}

		public void setOpen_default_manager(boolean open_default_manager) {

			this.open_default_manager = open_default_manager;
		}

		public String getPublic_jsp_path() {

			return public_jsp_path;
		}

		public void setPublic_jsp_path(String public_jsp_path) {

			this.public_jsp_path = public_jsp_path;
		}

	}

	public Map<String, String> getProperties() {

		return properties;
	}

	public String getEntitypackages() {

		return entitypackages;
	}

	public void setEntitypackages(String entitypackages) {

		this.entitypackages = entitypackages;
	}

	public String getLogback_path() {

		return logback_path;
	}

	public void setLogback_path(String logback_path) {

		this.logback_path = logback_path;
	}

	public Controller getController() {

		return controller;
	}

	public void setController(Controller controller) {

		this.controller = controller;
	}

	public Server getServer() {

		return server;
	}

	public void setServer(Server server) {

		this.server = server;
	}

	public Project getProject() {

		return project;
	}

	public void setProject(Project project) {

		this.project = project;
	}

	public String getProjectid() {

		return projectid;
	}

	public boolean isOffline() {

		return offline;
	}

}
