package top.coos.web.servlet.error;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import top.coos.servlet.DefaultServlet;
import top.coos.servlet.annotation.RequestMapping;
import top.coos.servlet.model.ModelMap;
import top.coos.web.constant.WebConstant;
import top.coos.web.core.WebCorePackageInfo;

/**
 * 错误处理
 * 
 * @author 朱亮
 * 
 */
@WebServlet(urlPatterns = WebCorePackageInfo.SERVLET_FOLDER + "/error/*")
public class ErrorServlet extends DefaultServlet {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * 到无权限页面
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/toNoAccess.do")
	public String toNoAccess(HttpServletRequest request, ModelMap map) {

		map.put("THIS_IS_ERROR_PAGE", true);
		map.put(WebConstant.Param.THIS_PAGE_ID, "error/noAccess.jsp");
		return WebConstant.Page.WEB_INF_VIEW_CORE_PUBLIC_JSP;
	}

	@RequestMapping("/toNotFindConfig.do")
	public String toNotFindConfig(HttpServletRequest request, ModelMap map) {

		map.put("THIS_IS_ERROR_PAGE", true);

		map.put(WebConstant.Param.THIS_PAGE_ID, "error/notFindConfig.jsp");
		return WebConstant.Page.WEB_INF_VIEW_CORE_PUBLIC_JSP;
	}

	@RequestMapping("/toNotFindCoreDatabase.do")
	public String toNotFindCoreDatabase(HttpServletRequest request, ModelMap map) {

		map.put("THIS_IS_ERROR_PAGE", true);

		map.put(WebConstant.Param.THIS_PAGE_ID, "error/notFindCoreDatabase.jsp");
		return WebConstant.Page.WEB_INF_VIEW_CORE_PUBLIC_JSP;
	}

	@RequestMapping("/toNotFindProjectConfig.do")
	public String toNotFindProjectConfig(HttpServletRequest request, ModelMap map) {

		map.put("THIS_IS_ERROR_PAGE", true);

		map.put(WebConstant.Param.THIS_PAGE_ID, "error/notFindProjectConfig.jsp");
		return WebConstant.Page.WEB_INF_VIEW_CORE_PUBLIC_JSP;
	}

	@RequestMapping("/toNotFindLoginPlugin.do")
	public String toNotFindLoginPlugin(HttpServletRequest request, ModelMap map) {

		map.put("THIS_IS_ERROR_PAGE", true);

		map.put(WebConstant.Param.THIS_PAGE_ID, "error/notFindLoginPlugin.jsp");
		return WebConstant.Page.WEB_INF_VIEW_CORE_PUBLIC_JSP;
	}

	@RequestMapping("/toNotFindProject.do")
	public String toNotFindProject(HttpServletRequest request, ModelMap map) {

		map.put("THIS_IS_ERROR_PAGE", true);

		map.put(WebConstant.Param.THIS_PAGE_ID, "error/notFindProject.jsp");
		return WebConstant.Page.WEB_INF_VIEW_CORE_PUBLIC_JSP;
	}

	@RequestMapping("/toCoreDatabaseConnectionError.do")
	public String toCoreDatabaseConnectionError(HttpServletRequest request, ModelMap map) {

		map.put("THIS_IS_ERROR_PAGE", true);

		map.put(WebConstant.Param.THIS_PAGE_ID, "error/coreDatabaseConnectionError.jsp");
		return WebConstant.Page.WEB_INF_VIEW_CORE_PUBLIC_JSP;
	}

	/**
	 * 到错误页面
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/fail.do")
	public String fail(HttpServletRequest request, ModelMap map) {

		map.put("THIS_IS_ERROR_PAGE", true);

		map.put(WebConstant.Param.THIS_PAGE_ID, "error/fail.jsp");
		return WebConstant.Page.WEB_INF_VIEW_CORE_PUBLIC_JSP;
	}

	/**
	 * 未上线页面
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/toNotOnline.do")
	public String toNotOnline(HttpServletRequest request, ModelMap map) {

		map.put("THIS_IS_ERROR_PAGE", true);

		map.put(WebConstant.Param.THIS_PAGE_ID, "error/notOnline.jsp");
		return WebConstant.Page.WEB_INF_VIEW_CORE_PUBLIC_JSP;
	}

	/**
	 * 到500页面
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/500.do")
	public String fiveZeroZero(HttpServletRequest request, ModelMap map) {

		map.put("THIS_IS_ERROR_PAGE", true);

		String msg = request.getParameter("msg");
		map.put("msg", msg);
		map.put(WebConstant.Param.THIS_PAGE_ID, "error/500.jsp");
		return WebConstant.Page.WEB_INF_VIEW_CORE_PUBLIC_JSP;
	}

	/**
	 * 到404页面
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/404.do")
	public String fourZeroFour(HttpServletRequest request, ModelMap map) {

		map.put("THIS_IS_ERROR_PAGE", true);

		String msg = request.getParameter("msg");
		map.put("msg", msg);
		map.put(WebConstant.Param.THIS_PAGE_ID, "error/404.jsp");
		return WebConstant.Page.WEB_INF_VIEW_CORE_PUBLIC_JSP;
	}

	/**
	 * 到信息页面
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/msg.do")
	public String msg(HttpServletRequest request, ModelMap map) {

		map.put("THIS_IS_ERROR_PAGE", true);

		String msg = request.getParameter("msg");
		map.put("msg", msg);
		map.put(WebConstant.Param.THIS_PAGE_ID, "error/msg.jsp");
		return WebConstant.Page.WEB_INF_VIEW_CORE_PUBLIC_JSP;
	}

}
