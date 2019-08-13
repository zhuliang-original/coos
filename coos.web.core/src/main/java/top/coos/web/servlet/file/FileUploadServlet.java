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
import org.apache.commons.lang.StringUtils;

import top.coos.web.constant.WebConstant;
import top.coos.web.core.WebCorePackageInfo;
import top.coos.web.servlet.file.bean.FileBean;
import top.coos.web.servlet.file.ifaces.IFileUpload;
import top.coos.web.servlet.file.ifaces.impl.FileUploadImpl;

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
		if (!StringUtils.isEmpty(uploadid)
				&& request.getSession().getAttribute("file-upload-progress-listener-" + uploadid) != null) {
			progressListener = (UploadProgress) request.getSession()
					.getAttribute("file-upload-progress-listener-" + uploadid);
		}
		request.getSession().setAttribute("file-upload-progress-listener-" + uploadid, progressListener);
		response.setContentType("text/html");
		String domain = request.getParameter("domain");
		if (!StringUtils.isEmpty(domain)) {
			response.setHeader("Access-Control-Allow-Origin", domain);
		}
		try {
			UploadFileResult uploadFileResult = this.doUploadFile(request, response, progressListener);
			JSONObject json = new JSONObject();
			if (request.getParameter("ckeditor") != null) {
				json.put("uploaded", true);
				json.put("url", uploadFileResult.getPath());
				json.put("name", uploadFileResult.getName());
			} else {
				json = (JSONObject) JSONObject.fromObject(uploadFileResult);
			}
			printWriter.print(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			printWriter.close();
		}
	}

	@SuppressWarnings("rawtypes")
	public UploadFileResult doUploadFile(HttpServletRequest request, HttpServletResponse response,
			UploadProgress progressListener) {

		List<FileBean> list = new ArrayList<FileBean>();
		UploadFileResult uploadFileResult = new UploadFileResult();
		uploadFileResult.setCode(0);
		try {
			// 构造一个文件上传处理对象
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			String tempPath = WebConstant.Path.getWebServerPath(request);
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
				if (!StringUtils.isEmpty(maxfilelength)) {
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
				IFileUpload fileUploadService = new FileUploadImpl();
				// if (configuration != null && configuration.getFile() != null)
				// {
				// if
				// (!StringUtil.isEmpty(configuration.getFile().getUpload_service()))
				// {
				// try {
				// IFileUpload fus = (IFileUpload)
				// Class.forName(configuration.getFile().getUpload_service())
				// .newInstance();
				// fileUploadService = fus;
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				// }
				// }
				list = fileUploadService.upload(request, fileItems);
				if (list == null || list.size() == 0) {
					uploadFileResult.setError(-1);
					uploadFileResult.setMessage("未传入文件！");
				} else {
					uploadFileResult.setError(0);
					uploadFileResult.setUrl(list.get(0).getUrl());
					uploadFileResult.setPath(list.get(0).getPath());
					uploadFileResult.setName(list.get(0).getName());
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
