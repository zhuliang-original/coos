package top.coos.web.servlet.file.ifaces.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import top.coos.ConfigurationFactory;
import top.coos.core.io.FileUtil;
import top.coos.exception.CoreException;
import top.coos.tool.entity.EntityTool;
import top.coos.tool.file.FileTool;
import top.coos.tool.http.aid.HttpRequestAid;
import top.coos.tool.request.RequestTool;
import top.coos.tool.string.StringHelper;
import top.coos.web.servlet.file.bean.FileBean;
import top.coos.web.servlet.file.bean.FileByteBean;
import top.coos.web.servlet.file.ifaces.IFileUpload;

public class FileUploadImpl implements IFileUpload {

	@SuppressWarnings("unchecked")
	@Override
	public List<FileBean> upload(HttpServletRequest request, List<FileItem> fileItems) throws Exception {

		List<FileBean> fileBeans = new ArrayList<FileBean>();
		String lastuploadurl = request.getParameter("LAST-UPLOAD-URL");
		String upload_file_server_url = ConfigurationFactory.get().server.upload_file_server_url;
		String this_server_root = RequestTool.getServerUrl(request);

		boolean must_save_local = false;
		if (StringUtils.isEmpty(upload_file_server_url)) {
			must_save_local = true;
		} else if ((upload_file_server_url.indexOf(this_server_root) == 0
				|| this_server_root.indexOf(upload_file_server_url) == 0)) {
			must_save_local = true;
		} else if (!StringUtils.isEmpty(lastuploadurl) && lastuploadurl.equals(this_server_root)) {
			must_save_local = true;
		}

		if (must_save_local) {
			List<FileByteBean> fileByteBeans = new ArrayList<FileByteBean>();
			for (FileItem item : fileItems) {
				// 取出上传文件的文件名称
				String name = item.getName();
				byte[] bytes = FileTool.readBytes(item.getInputStream());
				FileByteBean fileByteBean = new FileByteBean();
				fileByteBean.setBytes(bytes);
				fileByteBean.setName(name);

				fileByteBeans.add(fileByteBean);
			}
			fileBeans = upload(this_server_root, fileByteBeans);
		} else {
			upload_file_server_url += "?LAST-UPLOAD-URL=" + upload_file_server_url;

			HttpRequestAid aid = new HttpRequestAid();
			aid.setUrl(upload_file_server_url);
			aid.setFilename("file");
			aid.setFileItems(fileItems);
			StringBuffer buffer = aid.doPost();
			if (!StringHelper.isEmpty(buffer.toString())) {
				JSONObject object = JSONObject.fromObject(buffer.toString());
				if (object.get("value") != null) {
					JSONArray array = object.getJSONArray("value");
					for (int i = 0; i < array.size(); i++) {
						JSONObject one = array.getJSONObject(i);
						try {
							FileBean fileBean = (FileBean) EntityTool.getEntity(FileBean.class, one, false);
							fileBeans.add(fileBean);
						} catch (CoreException e) {
							e.printStackTrace();
						}
					}
				}
			}

		}
		return fileBeans;
	}

	@Override
	public List<FileBean> upload(String this_server_root, List<FileByteBean> fileByteBeans) throws Exception {
		List<FileBean> fileBeans = new ArrayList<FileBean>();
		String local_folder = ConfigurationFactory.get().server.getLocal_folder();
		String fileRootFolder = "/coos/file/path/";
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		String path = "/" + year + "/" + month + "/" + day + "/";
		String fileFolderPath = fileRootFolder + path;
		String saveFolder = local_folder + fileFolderPath;
		File saveFolderFile = new File(saveFolder);
		if (!saveFolderFile.exists()) {
			saveFolderFile.mkdirs();
		}

		for (FileByteBean fileByteBean : fileByteBeans) {
			// 取出上传文件的文件名称
			String name = fileByteBean.getName();
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
			FileUtil.writeBytes(fileByteBean.getBytes(), outFile);

			String filePath = fileRootFolder + path + saveName;

			String url = this_server_root + filePath;
			FileBean fileBean = new FileBean();
			fileBean.setUrl(url);
			fileBean.setName(filename);
			fileBean.setPath(filePath);
			fileBean.setLength(outFile.length());
			fileBean.setType(filetype);
			fileBeans.add(fileBean);

			String coosFileInfoPath = savefilepath + ".info";
			File coosFileInfoFile = new File(coosFileInfoPath);
			coosFileInfoFile.createNewFile();
			FileUtil.writeBytes(JSONObject.fromObject(fileBean).toString().getBytes(), coosFileInfoFile);

		}
		return fileBeans;
	}

}
