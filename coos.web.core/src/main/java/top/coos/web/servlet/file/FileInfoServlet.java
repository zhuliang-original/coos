package top.coos.web.servlet.file;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import top.coos.web.core.WebCorePackageInfo;
import top.coos.web.servlet.file.bean.FileBean;
import top.coos.web.servlet.file.ifaces.IFileInfo;
import top.coos.web.servlet.file.ifaces.impl.FileInfoImpl;

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
		response.setContentType("application/json");
		String path = request.getParameter("path");

		IFileInfo iFileInfo = new FileInfoImpl();
		// if (configuration != null && configuration.getFile() != null) {
		// if (!StringUtil.isEmpty(configuration.getFile().getInfo_service())) {
		// try {
		// IFileInfo fis = (IFileInfo)
		// Class.forName(configuration.getFile().getInfo_service()).newInstance();
		// iFileInfo = fis;
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// }
		FileBean fileBean = iFileInfo.get(request, path);

		if (fileBean != null) {
			response.getOutputStream().write(JSONObject.fromObject(fileBean).toString().getBytes("UTF-8"));
			response.getOutputStream().flush();
			return;
		} else {
			response.getOutputStream().write("{}".getBytes());
			response.getOutputStream().flush();
		}

	}

}
