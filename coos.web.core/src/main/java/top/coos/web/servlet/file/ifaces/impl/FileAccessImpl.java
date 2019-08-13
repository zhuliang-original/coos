package top.coos.web.servlet.file.ifaces.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import top.coos.ConfigurationFactory;
import top.coos.core.io.FileUtil;
import top.coos.tool.request.RequestTool;
import top.coos.web.servlet.file.ifaces.IFileAccess;

public class FileAccessImpl implements IFileAccess {

	@Override
	public void access(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
		String upload_file_server_url = ConfigurationFactory.get().server.upload_file_server_url;
		String this_server_root = RequestTool.getServerUrl(request);

		boolean is_local = true;
		if (!StringUtils.isEmpty(upload_file_server_url)) {
			if ((upload_file_server_url.indexOf(this_server_root) == 0
					|| this_server_root.indexOf(upload_file_server_url) == 0)) {
				is_local = true;
			} else {
				is_local = false;
			}
		}
		if (is_local) {
			String local_folder = ConfigurationFactory.get().server.getLocal_folder();
			File file = new File(local_folder + path);
			if (file != null && file.isFile()) {
				response.getOutputStream().write(FileUtil.readBytes(file));
				response.getOutputStream().flush();
			}
		} else {
			response.sendRedirect(upload_file_server_url + path);
		}
	}

}
