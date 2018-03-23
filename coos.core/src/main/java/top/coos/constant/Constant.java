package top.coos.constant;

import top.coos.classloader.CoreClassLoader;

/**
 * 常量参数
 * 
 */
public interface Constant {

	public static final String NAME = "COOS";

	public class Class {

		public static ClassLoader CLASS_LOADER = Constant.class.getClassLoader();

		public static final CoreClassLoader BASEPM_CLASS_LOADER = new CoreClassLoader();
	}

	public class Config {

		public static final String LOGBACK = "config/coos/logback.xml";

		public static final String FILE_CONTENT_TYPE = "config/coos/fie-content-type.properties";

		public static final String CORE_CONFIG = "config/coos/core-config.properties";

		public static final String PROXY_CONFIG = "config/coos/proxy.properties";

		public static final String DEV_SERVER_CONFIG_FILE_PATH = "C:/coos/dev-config.properties";

		public static final String DEV_SERVER_CONFIG = "config/coos/dev-config.properties";

		public static final String CORE_DATABASE = "config/coos/core-database.properties";

		public static final String CORE_DATABASE_BY_SYSTEM = Path.USER_DIR_PATH + "/conf/coos-core-database.properties";

		public static final String OTHER_DATABASE_FOLDER = "config/coos/databases/";

		public static final String CORE_TASK_INITIALIZE_FOLDER = "config/coos/task/initialize/";

		public static final String PROJECTS_FOLDER = "config/coos/projects/";

		public static final String OTHER_DATABASES = OTHER_DATABASE_FOLDER + "${name}.properties";

		public static final String INDEX_TO_CHARSET = "config/coos/index_to_charset.properties";

		public static final String SERVER_DATABASE = "config/coos/server/database.properties";


		public static final String FILE_FOLDER = "coos.folder";
	}

	/**
	 * 目录
	 * 
	 * @author 朱亮
	 * 
	 */
	public class Path {

		public static final String USER_DIR_PATH = System.getProperty("user.dir");

		public static final String TEMP_PATH = getTempPath();

		private static final String getTempPath() {

			String path = null;
			if (Class.CLASS_LOADER.getResource("/") != null) {
				path = Class.CLASS_LOADER.getResource("/").getPath();
			}
			if (Class.CLASS_LOADER.getResource("") != null) {
				path = Class.CLASS_LOADER.getResource("").getPath();
			}
			path = path.replace("WEB-INF/classes/", "");
			path = path.replace("target/classes/", "");
			path = path.replace("bin/classes/", "");

			path = path + "temp/";

			return path;
		}
	}

}
