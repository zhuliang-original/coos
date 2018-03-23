package top.coos.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import top.coos.servlet.cache.RequestMappingCacheContext;
import top.coos.servlet.context.ControllerContext;
import top.coos.servlet.handle.RequestMappingHandle;
import top.coos.servlet.mapping.RequestMapping;
import top.coos.servlet.model.ModelAndView;
import top.coos.tool.request.RequestTool;
import top.coos.tool.string.StringHelper;

public class DefaultServlet extends HttpServlet {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// TODO Auto-generated method stub
		super.doDelete(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// TODO Auto-generated method stub
		super.doHead(req, resp);
	}

	@Override
	protected void doOptions(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {

		// TODO Auto-generated method stub
		super.doOptions(arg0, arg1);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		super.doPost(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// TODO Auto-generated method stub
		super.doPut(req, resp);
	}

	@Override
	protected void doTrace(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {

		// TODO Auto-generated method stub
		super.doTrace(arg0, arg1);
	}

	@Override
	protected long getLastModified(HttpServletRequest req) {

		// TODO Auto-generated method stub
		return super.getLastModified(req);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		super.service(request, response);
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

		try {
			doDispatch((HttpServletRequest) req, (HttpServletResponse) res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pathinfo = RequestTool.getServletpath(request);
		if (this.getClass().isAnnotationPresent(WebServlet.class)) {
			ControllerContext.get().addControl(this.getClass());
		}
		List<RequestMapping> methods = RequestMappingCacheContext.CONTEXT.getMappings(pathinfo);

		RequestMappingHandle handle = new RequestMappingHandle(request, response, methods);
		handle.execute();
		if (methods == null || methods.size() == 0) {
			DefaultServletHandler.execute(this, request, response);
			return;
		}
	}

	public ModelAndView getContent(String content) {

		return new ModelAndView(ModelAndView.Type.CONTENT, content);
	}

	public ModelAndView getJSON(Object data) {

		JSONObject object = new JSONObject();
		object.put("data", data);
		return new ModelAndView(ModelAndView.Type.JSON, object);
	}

	public void outJS(HttpServletResponse response, String content) {

		PrintWriter printWriter;
		response.setCharacterEncoding("UTF-8");
		printWriter = null;
		response.setContentType("application/x-javascript");
		response.setHeader("Cache-Control", "no-cache");
		try {
			printWriter = response.getWriter();
			printWriter.print(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (printWriter != null)
				printWriter.close();
		}

	}

	public void outCSS(HttpServletResponse response, String content) {

		PrintWriter printWriter;
		response.setCharacterEncoding("UTF-8");
		printWriter = null;
		response.setContentType("text/css");
		response.setHeader("Cache-Control", "no-cache");
		try {
			printWriter = response.getWriter();
			printWriter.print(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (printWriter != null)
				printWriter.close();
		}

	}

	public void outHTML(HttpServletResponse response, String content) {

		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter printWriter;
		response.setCharacterEncoding("UTF-8");
		printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.print(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (printWriter != null)
				printWriter.close();
		}
	}

	public void out(HttpServletResponse response, String content) {

		response.setHeader("Content-type", "text/json;charset=UTF-8");
		PrintWriter printWriter;
		response.setCharacterEncoding("UTF-8");
		printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.print(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (printWriter != null)
				printWriter.close();
		}
	}

	public void outJSON(HttpServletResponse response, Object data) {

		JSONObject content = new JSONObject();
		content.put("data", data);
		out(response, content.toString());
	}

	public void outJSONP(HttpServletRequest request, HttpServletResponse response, Object data) {

		String jsonpcallback = request.getParameter("jsonpcallback");
		if (StringHelper.isEmpty(jsonpcallback)) {
			jsonpcallback = request.getParameter("callback");
		}
		// * 表示允许任何域名跨域访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject content = new JSONObject();
		content.put("data", data);
		String result = content.toString();
		if (!StringHelper.isEmpty(jsonpcallback)) {
			result = jsonpcallback + "('" + (result) + "')";
		}
		out(response, result);
	}
}
