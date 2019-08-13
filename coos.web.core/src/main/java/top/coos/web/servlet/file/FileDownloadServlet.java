package top.coos.web.servlet.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import top.coos.web.core.WebCorePackageInfo;
import top.coos.web.servlet.file.ifaces.IFileDownload;
import top.coos.web.servlet.file.ifaces.impl.FileDownloadImpl;

@WebServlet(urlPatterns = WebCorePackageInfo.SERVLET_FOLDER + "/file/file.download")
public class FileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 将请求，响应的编码设置为UTF-8(防止乱码)
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String path = request.getParameter("path");
		IFileDownload iFileDownload = new FileDownloadImpl();
		// if (configuration != null && configuration.getFile() != null) {
		// if
		// (!StringUtil.isEmpty(configuration.getFile().getDownload_service()))
		// {
		// try {
		// IFileDownload fds = (IFileDownload)
		// Class.forName(configuration.getFile().getDownload_service())
		// .newInstance();
		// iFileDownload = fds;
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// }

		if (!StringUtils.isEmpty(path)) {

			if (StringUtils.isEmpty(path) && path.indexOf("/") >= 0) {
				name = path.substring(path.lastIndexOf("/") + 1, path.length());
			}
			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(name, "UTF-8"));
			// response.setContentType("application/text");
			response.setCharacterEncoding("UTF-8");

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				response.getOutputStream().write(iFileDownload.download(request, path));
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			} finally {
				bos.close();
			}
		}

	}

}
