package top.coos.web.servlet.file;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import top.coos.Configuration;
import top.coos.ConfigurationFactory;
import top.coos.bean.FileEntity;
import top.coos.tool.string.StringHelper;
import top.coos.web.core.WebCorePackageInfo;
import top.coos.web.service.FileInfoService;
import top.coos.web.service.FileInfoServiceImpl;

@WebServlet(urlPatterns = WebCorePackageInfo.SERVLET_FOLDER + "/file/file.info")
public class FileInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 将请求，响应的编码设置为UTF-8(防止乱码)
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		String path = request.getParameter("path");
		String url = request.getParameter("url");

		Configuration configuration = ConfigurationFactory.get(request);
		FileInfoService fileInfoService = new FileInfoServiceImpl();
		if (configuration != null && configuration.service != null) {
			if (!StringHelper.isEmpty(configuration.service.file_info)) {
				try {
					FileInfoService fis = (FileInfoService) Class.forName(configuration.service.file_info)
							.newInstance();
					fileInfoService = fis;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		FileEntity fileEntity = fileInfoService.readInfo(request, path, url);

		if (fileEntity != null) {

			response.getOutputStream().write(JSONObject.fromObject(fileEntity).toString().getBytes());
			return;
		} else {
			response.getOutputStream().write("{}".getBytes());
		}

	}

}
