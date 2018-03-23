package top.coos.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.coos.Coos;

public final class CoreLoggerFactory extends Coos {

	public static Logger get(Class<?> clazz) {

		Logger logger = LoggerFactory.getLogger(clazz);
		return new CoreLogger(logger);
	}

	public static Logger get(String className) {

		Logger logger = LoggerFactory.getLogger(className);
		return new CoreLogger(logger);
	}

	/**
	 * @return 获得日志，自动判定日志发出者
	 */
	public static Logger get() {

		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		Logger logger = get(stackTrace[2].getClassName());
		return new CoreLogger(logger);
	}

}
