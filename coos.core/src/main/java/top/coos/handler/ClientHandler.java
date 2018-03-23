package top.coos.handler;

import javax.servlet.http.HttpServletRequest;

import top.coos.bean.client.Client;
import top.coos.web.constant.WebConstant;

public class ClientHandler {

	public ClientHandler() {

	}

	public static String getClientParamName(boolean isDev) {

		if (isDev) {
			return WebConstant.Param.THIS_DEV_CLIENT_ENTITY;
		}
		return WebConstant.Param.THIS_CLIENT_ENTITY;
	}

	private static Client initClient(HttpServletRequest request, Class<?> clientClass, boolean isDev) {

		Client client = null;
		String clientName = getClientParamName(isDev);
		if (request.getSession().getAttribute(clientName) != null) {
			client = (Client) request.getSession().getAttribute(clientName);
		} else {
			try {
				client = (Client) clientClass.newInstance();
			} catch (Exception e) {
			}
			client.setLogin(false);
			client.setSessionid(request.getSession().getId());
		}
		request.getSession().setAttribute(clientName, client);
		return client;
	}

	public static Client getClient(HttpServletRequest request, Class<?> clientClass, boolean isDev) {

		return initClient(request, clientClass, isDev);
	}

	public static void removeClient(HttpServletRequest request, boolean isDev) {

		String clientName = getClientParamName(isDev);
		request.getSession().removeAttribute(clientName);
	}

}
