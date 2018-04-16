package top.coos.logger;


import top.coos.Coos;

public final class LoggerFactory extends Coos {

	public static org.slf4j.Logger get(Class<?> clazz) {

		org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(clazz);
		return new Logger(logger);
	}

	public static org.slf4j.Logger get(String className) {

		org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(className);
		return new Logger(logger);
	}

	/**
	 * @return 获得日志，自动判定日志发出者
	 */
	public static org.slf4j.Logger get() {

		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		org.slf4j.Logger logger = get(stackTrace[2].getClassName());
		return new Logger(logger);
	}

}
