package top.coos.web.servlet.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.coos.bean.Status;
import top.coos.common.service.QRCodeService;
import top.coos.exception.CoreException;
import top.coos.servlet.DefaultServlet;
import top.coos.servlet.annotation.RequestMapping;
import top.coos.tool.base.BaseTool;
import top.coos.tool.string.StringHelper;
import top.coos.web.constant.WebConstant;
import top.coos.web.core.WebCorePackageInfo;

@WebServlet(urlPatterns = WebCorePackageInfo.SERVLET_FOLDER + "/data/*")
public class CoreDataServlet extends DefaultServlet {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@RequestMapping("/createQRCode.data")
	public void createQRCode(HttpServletRequest request, HttpServletResponse response) {

		Status status = Status.SUCCESS();
		try {
			String content = request.getParameter("content");
			if (StringHelper.isEmpty(content)) {
				throw new CoreException("content is null");
			}
			int width = 300;
			int height = 300;
			if (!StringHelper.isEmpty(request.getParameter("width"))) {
				width = Integer.valueOf(request.getParameter("width"));
			}
			if (!StringHelper.isEmpty(request.getParameter("height"))) {
				height = Integer.valueOf(request.getParameter("height"));
			}
			String name = request.getParameter("name");
			if (StringHelper.isEmpty(name)) {
				name = BaseTool.getRandomNumber() + ".png";
			}
			if (name.indexOf(".") == -1) {
				name = name + ".png";
			}
			String path = "/file/qrcode/" + name;
			String savePath = WebConstant.Path.getWebServerPath(request) + path;
			boolean needcreate = true;
			if (new File(savePath).exists()) {
				if (StringHelper.isEmpty(request.getParameter("createnew"))) {
					needcreate = false;
				}
			}
			if (needcreate) {
				QRCodeService qrCodeService = new QRCodeService();
				qrCodeService.create(savePath, content, width, height);
			}
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("path", path);
			status.setResult(result);
		} catch (Exception e) {
			status.setErrcode(-1);
			status.setErrmsg(e.getMessage());
		}
		outJSON(response, status);
	}

	@RequestMapping("/getServerInfo.data")
	public void getServerInfo(HttpServletRequest request, HttpServletResponse response) {

		Status status = Status.SUCCESS();
		try {
			String servername = request.getServerName();
			String protocol = request.getProtocol();
			int serverport = request.getServerPort();
			String contextpath = request.getContextPath();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("servername", servername);
			result.put("serverport", serverport);
			result.put("protocol", protocol);
			result.put("contextpath", contextpath);
			status.setResult(result);
		} catch (Exception e) {
			status.setErrcode(-1);
			status.setErrmsg(e.getMessage());
		}
		outJSON(response, status);
	}

	@RequestMapping("/getRandomNumber.data")
	public void getRandomNumber(HttpServletRequest request, HttpServletResponse response) {

		outJSON(response, BaseTool.getRandomNumber());
	}

}