package top.coos.tool.upload;

import javax.servlet.http.HttpServletRequest;

import top.coos.Configuration;
import top.coos.ConfigurationFactory;
import top.coos.constant.Constant;
import top.coos.tool.request.RequestTool;
import top.coos.tool.string.StringHelper;

public class FileServerTool {

	static Configuration configuration = ConfigurationFactory.get();

	public static boolean isSaveLocal() {

		String fileserverurl = configuration.server.file_server_url;
		if (StringHelper.isEmpty(fileserverurl)) {
			return true;
		}
		return false;

	}

	public static String getThisFileServerUrl(HttpServletRequest request) {

		String thisserverurl = RequestTool.getServerUrl(request);
		String contextPath = request.getContextPath();
		if (!StringHelper.isEmpty(contextPath)) {
			int index = thisserverurl.lastIndexOf(contextPath);
			if (index > 0) {
				thisserverurl = thisserverurl.substring(0, index);
			}
		}
		thisserverurl = thisserverurl + "/" + Constant.Config.FILE_FOLDER;
		return thisserverurl;
	}

	public static String getFileServerUrl(HttpServletRequest request) {

		String fileserverurl = configuration.server.file_server_url;
		if (StringHelper.isEmpty(fileserverurl)) {
			fileserverurl = getThisFileServerUrl(request);
		}
		return fileserverurl;

	}

	public static String getUploadFileServerUrl(HttpServletRequest request) {

		String fileserverurl = configuration.server.upload_file_server_url;
		if (StringHelper.isEmpty(fileserverurl)) {
			fileserverurl = RequestTool.getServerUrl(request);
		}
		return fileserverurl;

	}

	public static String getOpenFileServerUrl(HttpServletRequest request) {

		String fileserverurl = configuration.server.open_file_server_url;
		if (StringHelper.isEmpty(fileserverurl)) {
			fileserverurl = getThisFileServerUrl(request);
		}
		return fileserverurl;

	}

	public static String getOpenUploadFileServerUrl(HttpServletRequest request) {

		String fileserverurl = configuration.server.open_upload_file_server_url;
		if (StringHelper.isEmpty(fileserverurl)) {
			fileserverurl = RequestTool.getServerUrl(request);
		}
		return fileserverurl;

	}

}
