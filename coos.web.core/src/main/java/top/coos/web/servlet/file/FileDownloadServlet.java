package top.coos.web.servlet.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.coos.constant.Constant;
import top.coos.web.core.WebCorePackageInfo;
import top.coos.web.constant.WebConstant;

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
		if (name != null && !name.trim().equals("") && path != null && !path.trim().equals("")) {

			try {

				String fileServerFolder = WebConstant.Path.getWebServerPath(request);
				String sourceFile = new File(fileServerFolder).getParentFile().getAbsolutePath() + "/"
						+ Constant.Config.FILE_FOLDER + "/" + path;

				File file = new File(sourceFile);
				if (file != null && file.isFile()) {

					String filename = URLEncoder.encode(name, "UTF-8");
					response.addHeader("Content-Disposition", "attachment; filename=" + filename);
					// response.setContentType("application/text");
					response.setCharacterEncoding("UTF-8");

					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					InputStream in = null;
					try {
						in = new FileInputStream(file);
						int buf_size = 1024;
						byte[] buffer = new byte[buf_size];
						int len = 0;
						while (-1 != (len = in.read(buffer, 0, buf_size))) {
							bos.write(buffer, 0, len);
						}
						response.getOutputStream().write(bos.toByteArray());
					} catch (IOException e) {
						e.printStackTrace();
						throw e;
					} finally {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						bos.close();
					}
				}

			} catch (Exception e) {
			}
		}
	}

}
