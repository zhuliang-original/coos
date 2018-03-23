package top.coos.tool.version;

import org.apache.commons.lang.math.NumberUtils;

public class VersionTool {

	/**
	 * 版本号累加算法
	 * 
	 * @return
	 */
	public static String newVersion(String old) {

		String old_version_tmp = old.replaceAll("\\.", "");
		if (!NumberUtils.isNumber(old_version_tmp)) {
			return old;
		}
		long sysVersionNum = Long.valueOf(old_version_tmp);
		sysVersionNum += 1;
		String sys_version = "";
		for (int i = 0; i < (sysVersionNum + "").length(); i++) {
			if (i == 0) {
				sys_version += (sysVersionNum + "").charAt(i);
			} else {
				sys_version += "." + (sysVersionNum + "").charAt(i);
			}
		}
		return sys_version;
	}
}
