package top.coos.web.servlet.file.ifaces.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;
import top.coos.ConfigurationFactory;
import top.coos.core.io.FileUtil;
import top.coos.exception.CoreException;
import top.coos.tool.entity.EntityTool;
import top.coos.web.servlet.file.bean.FileBean;
import top.coos.web.servlet.file.ifaces.IFileInfo;

public class FileInfoImpl implements IFileInfo {

	@SuppressWarnings("unchecked")
	@Override
	public FileBean get(HttpServletRequest request, String path) throws IOException {

		FileBean fileBean = null;
		if (!StringUtils.isEmpty(path)) {
			String local_folder = ConfigurationFactory.get().server.getLocal_folder();
			File file = new File(local_folder + path + ".info");
			if (file != null && file.isFile()) {
				byte[] bytes = FileUtil.readBytes(file);
				if (bytes != null) {
					JSONObject json = JSONObject.fromObject(new String(bytes));
					try {
						fileBean = (FileBean) EntityTool.getEntity(FileBean.class, json, false);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return fileBean;
	}

}
