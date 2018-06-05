package top.coos.web.servlet.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import top.coos.Configuration;
import top.coos.ConfigurationFactory;
import top.coos.bean.FileEntity;
import top.coos.tool.string.StringHelper;
import top.coos.web.core.WebCorePackageInfo;
import top.coos.web.service.FileUploadService;
import top.coos.web.service.FileUploadServiceImpl;

@WebServlet(urlPatterns = WebCorePackageInfo.SERVLET_FOLDER + "/file/file.upload")
public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 将请求，响应的编码设置为UTF-8(防止乱码)
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter printWriter = response.getWriter();
		String uploadid = request.getParameter("uploadid");
		UploadProgress progressListener = new UploadProgress();
		if (!StringHelper.isEmpty(uploadid)
				&& request.getSession().getAttribute("file-upload-progress-listener-" + uploadid) != null) {
			progressListener = (UploadProgress) request.getSession().getAttribute(
					"file-upload-progress-listener-" + uploadid);
		}
		request.getSession().setAttribute("file-upload-progress-listener-" + uploadid, progressListener);
		response.setContentType("text/html");
		try {
			UploadFileResult uploadFileResult = this.doUploadFile(request, response, progressListener);
			printWriter.print(JSONObject.fromObject(uploadFileResult));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			printWriter.close();
		}
	}

	@SuppressWarnings("rawtypes")
	public UploadFileResult doUploadFile(HttpServletRequest request, HttpServletResponse response,
			UploadProgress progressListener) {

		List<FileEntity> list = new ArrayList<FileEntity>();
		UploadFileResult uploadFileResult = new UploadFileResult();
		uploadFileResult.setCode(0);
		try {
			// 构造一个文件上传处理对象
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			String tempPath = this.getClass().getClassLoader().getResource("/").getPath().replace("WEB-INF/classes/", "");
			tempPath = tempPath + "upload/tmp/";
			File fp1 = new File(tempPath);
			if (!fp1.exists())
				fp1.mkdirs();
			factory.setRepository(new File(tempPath));
			factory.setSizeThreshold(10240);
			// 添加文件字节流读取监听
			upload.setProgressListener(progressListener);

			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				Iterator items = upload.parseRequest(request).iterator();
				int length = request.getContentLength();

				String maxfilelength = request.getParameter("maxfilelength");
				if (!StringHelper.isEmpty(maxfilelength)) {
					int maxlength = (int) (Double.valueOf(maxfilelength) * 1024 * 1024);
					if (length > maxlength) {
						uploadFileResult.setCode(-1);
						uploadFileResult.setMessage("文件不能大于" + maxfilelength + "M");
					}
				}

				List<FileItem> fileItems = new ArrayList<FileItem>();
				while (items.hasNext()) {
					FileItem item = (FileItem) items.next();

					if (!item.isFormField()) {
						fileItems.add(item);
					}
				}
				Configuration configuration = ConfigurationFactory.get(request);
				FileUploadService fileUploadService = new FileUploadServiceImpl();
				if (configuration != null && configuration.service != null) {
					if (!StringHelper.isEmpty(configuration.service.file_upload)) {
						try {
							FileUploadService fus = (FileUploadService) Class.forName(configuration.service.file_upload)
									.newInstance();
							fileUploadService = fus;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				list = fileUploadService.upload(request, fileItems);
				if (list == null || list.size() == 0) {
					uploadFileResult.setError(-1);
					uploadFileResult.setMessage("未传入文件！");
				} else {
					uploadFileResult.setError(0);
					uploadFileResult.setUrl(list.get(0).getUrl());
					uploadFileResult.setPath(list.get(0).getPath());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			uploadFileResult.setCode(-1);
			uploadFileResult.setError(-1);
			uploadFileResult.setMessage(e.getMessage());
		}
		uploadFileResult.setValue(list);
		return uploadFileResult;
	}

}
