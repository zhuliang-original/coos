package top.coos.web.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;

import top.coos.bean.FileEntity;
import top.coos.constant.Constant;
import top.coos.exception.CoreException;
import top.coos.tool.file.FileTool;
import top.coos.tool.http.aid.HttpRequestAid;
import top.coos.tool.request.RequestTool;
import top.coos.tool.string.StringHelper;
import top.coos.tool.upload.FileServerTool;
import top.coos.web.constant.WebConstant;

public class FileUploadServiceImpl implements FileUploadService {

	@Override
	public List<FileEntity> upload(HttpServletRequest request, List<FileItem> fileItems) throws Exception {

		List<FileEntity> fileEntitys = new ArrayList<FileEntity>();
		String lastuploadurl = request.getParameter("LAST-UPLOAD-URL");
		String fileuploadserverurl = FileServerTool.getUploadFileServerUrl(request) + "/core/file/file.upload";

		boolean must_save_local = false;
		if (FileServerTool.getUploadFileServerUrl(request).indexOf(RequestTool.getServerUrl(request)) == 0
				|| RequestTool.getServerUrl(request).indexOf(FileServerTool.getUploadFileServerUrl(request)) == 0) {
			must_save_local = true;
		} else if (!StringHelper.isEmpty(lastuploadurl) && lastuploadurl.equals(fileuploadserverurl)) {
			must_save_local = true;
		}
		if (FileServerTool.isSaveLocal() || must_save_local) {
			String fileServerFolder = WebConstant.Path.getWebServerPath(request);
			String fileRootFolder = "/upload/file/";
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int day = calendar.get(Calendar.DATE);
			String path = "/" + year + "/" + month + "/" + day + "/";
			String fileFolderPath = fileRootFolder + path;
			String saveFolder = new File(fileServerFolder).getParentFile().getAbsolutePath() + "/"
					+ Constant.Config.FILE_FOLDER + "/" + fileFolderPath;
			File saveFolderFile = new File(saveFolder);
			if (!saveFolderFile.exists()) {
				saveFolderFile.mkdirs();
			}
			for (FileItem item : fileItems) {
				FileEntity fileEntity = new FileEntity();
				// 取出上传文件的文件名称
				String name = item.getName();
				// 取得上传文件以后的存储路径
				String filename = name.substring(name.lastIndexOf('\\') + 1, name.length());

				String fileid = new Date().getTime() + "" + (new Random().nextInt(10)) + "" + (new Random().nextInt(10))
						+ "" + (new Random().nextInt(10)) + "" + (new Random().nextInt(10));

				String savefilepath = "";
				String saveName = "";
				String filetype = "";
				if (filename.lastIndexOf(".") >= 0) {
					filetype = filename.substring(filename.lastIndexOf(".") + 1);
					saveName = fileid + "." + filetype;
					filename = filename.substring(0, filename.lastIndexOf("."));
				} else {
					saveName = fileid + "";
				}
				savefilepath = saveFolder + "/" + saveName;
				File outFile = new File(savefilepath);
				outFile.createNewFile();
				item.write(outFile);

				long length = item.getSize();

				String filePath = fileRootFolder + path + saveName;

				String url = FileServerTool.getFileServerUrl(request) + filePath;
				fileEntity.setUrl(url);
				fileEntity.setName(item.getName());
				fileEntity.setPath(filePath);
				fileEntity.setLength(length);
				fileEntity.setType(filetype);
				fileEntitys.add(fileEntity);

				String coosFileInfoPath = savefilepath + ".info";
				File coosFileInfoFile = new File(coosFileInfoPath);
				coosFileInfoFile.createNewFile();
				FileTool.save(coosFileInfoFile, JSONObject.fromObject(fileEntity).toString());
			}
		} else {
			fileuploadserverurl += "?LAST-UPLOAD-URL=" + fileuploadserverurl;

			HttpRequestAid aid = new HttpRequestAid();
			aid.setUrl(fileuploadserverurl);
			aid.setFilename("file");
			aid.setFileItems(fileItems);
			StringBuffer buffer = aid.doPost();
			if (!StringHelper.isEmpty(buffer.toString())) {
				JSONObject object = JSONObject.fromObject(buffer.toString());
				if (object.getInt("error") == 0) {
					if (object.get("value") != null) {
						JSONArray array = object.getJSONArray("value");
						for (int i = 0; i < array.size(); i++) {
							JSONObject one = array.getJSONObject(i);
							String name = one.getString("name");
							String path = one.getString("path");
							String type = one.getString("type");
							long length = one.getLong("length");
							String url = FileServerTool.getFileServerUrl(request) + path;
							FileEntity fileEntity = new FileEntity();
							fileEntity.setName(name);
							fileEntity.setPath(path);
							fileEntity.setType(type);
							fileEntity.setLength(length);
							fileEntity.setUrl(url);
							fileEntitys.add(fileEntity);
						}
					}
				} else {
					throw new CoreException(object.getString("message"));
				}
			}
		}
		return fileEntitys;
	}

}
