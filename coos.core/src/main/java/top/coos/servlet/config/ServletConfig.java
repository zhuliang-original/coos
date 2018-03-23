package top.coos.servlet.config;

import java.util.List;

public class ServletConfig {

	private Controller controller = new Controller();

	public static ServletConfig get() {

		return new ServletConfig();
	}

	public Controller getController() {

		return controller;
	}

	public void setController(Controller controller) {

		this.controller = controller;
	}

	public class Controller {

		private List<String> servlet_patterns;

		public List<String> getServlet_patterns() {

			return servlet_patterns;
		}

		public void setServlet_patterns(List<String> servlet_patterns) {

			this.servlet_patterns = servlet_patterns;
		}

	}
}
