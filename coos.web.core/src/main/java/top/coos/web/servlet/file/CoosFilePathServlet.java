package top.coos.web.servlet.file;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.coos.tool.request.RequestTool;
import top.coos.web.servlet.file.ifaces.IFileAccess;
import top.coos.web.servlet.file.ifaces.impl.FileAccessImpl;

@WebServlet("/upload/file/*")
public class CoosFilePathServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 将请求，响应的编码设置为UTF-8(防止乱码)
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType(null);
		String path = RequestTool.getServletpath(request);
		IFileAccess iFileDownload = new FileAccessImpl();
		// if (configuration != null && configuration.getFile() != null) {
		// if
		// (!StringUtil.isEmpty(configuration.getFile().getAccess_service()))
		// {
		// try {
		// IFileAccess fds = (IFileAccess)
		// Class.forName(configuration.getFile().getAccess_service())
		// .newInstance();
		// iFileDownload = fds;
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// }
		iFileDownload.access(request, response, path);

	}

}
