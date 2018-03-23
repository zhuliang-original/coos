package top.coos.web.servlet.index;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.coos.servlet.DefaultServlet;
import top.coos.servlet.annotation.RequestMapping;
import top.coos.servlet.model.ModelMap;
import top.coos.web.core.WebCorePackageInfo;
import top.coos.web.constant.WebConstant;

@WebServlet(urlPatterns = WebCorePackageInfo.SERVLET_FOLDER + "/index/*")
public class IndexServlet extends DefaultServlet {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@RequestMapping("/toIndex.do")
	public String toIndex(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {

		map.put(WebConstant.Param.THIS_PAGE_ID, WebCorePackageInfo.PAGE_FOLDER + "index/index.jsp");
		return WebConstant.Page.WEB_INF_VIEW_CORE_PUBLIC_JSP;
	}

}
