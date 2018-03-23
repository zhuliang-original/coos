package top.coos.web.servlet.file;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.coos.constant.Constant;
import top.coos.tool.file.FileTool;
import top.coos.web.core.WebCorePackageInfo;
import top.coos.web.constant.WebConstant;

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
		if (path != null && !path.trim().equals("")) {

			try {
				String fileServerFolder = WebConstant.Path.getWebServerPath(request);
				String file_path = new File(fileServerFolder).getParentFile().getAbsolutePath() + "/"
						+ Constant.Config.FILE_FOLDER + "/" + path;
				File file = new File(file_path + ".info");
				if (file != null && file.isFile()) {
					response.getOutputStream().write(FileTool.read(file).getBytes());
					return;
				}

			} catch (Exception e) {
			}
		}
		if (url != null && !url.trim().equals("")) {
			File file = FileTool.getFile(url + ".info");
			if (file != null && file.isFile()) {
				response.getOutputStream().write(FileTool.read(file).getBytes());
				return;
			}
		}
		response.getOutputStream().write("{}".getBytes());
	}

}
