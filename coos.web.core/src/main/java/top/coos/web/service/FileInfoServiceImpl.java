package top.coos.web.service;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import top.coos.bean.FileEntity;
import top.coos.constant.Constant;
import top.coos.tool.entity.EntityTool;
import top.coos.tool.file.FileTool;
import top.coos.web.constant.WebConstant;

public class FileInfoServiceImpl implements FileInfoService {

	@SuppressWarnings("unchecked")
	@Override
	public FileEntity readInfo(HttpServletRequest request, String path, String url) {

		String content = null;
		if (path != null && path.length() > 0) {

			String fileServerFolder = WebConstant.Path.getWebServerPath(request);
			String file_path = new File(fileServerFolder).getParentFile().getAbsolutePath() + "/"
					+ Constant.Config.FILE_FOLDER + "/" + path;
			File file = new File(file_path + ".info");
			if (file != null && file.isFile()) {
				content = FileTool.read(file);
			}
		}
		if ((content == null) && url != null && url.length() > 0) {
			File file = FileTool.getFile(url + ".info");
			if (file != null && file.isFile()) {
				content = FileTool.read(file);
			}
		}
		FileEntity fileEntity = null;
		if (content != null) {
			try {
				JSONObject object = JSONObject.fromObject(content);
				fileEntity = EntityTool.getEntity(FileEntity.class, object, false);
			} catch (Exception e) {
			}
		}
		return fileEntity;
	}

}
