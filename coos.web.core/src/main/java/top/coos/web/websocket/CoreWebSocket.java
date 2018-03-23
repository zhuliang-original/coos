package top.coos.web.websocket;

import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;

import net.sf.json.JSONObject;
import top.coos.logger.CoreLoggerFactory;
import top.coos.tool.string.StringHelper;
import top.coos.web.cache.WebSocketListenerCache;
import top.coos.websocket.DefaultWebSocket;

@ServerEndpoint("/core/websocket")
public class CoreWebSocket extends DefaultWebSocket {

	static Logger logger = CoreLoggerFactory.get();

	@Override
	protected void onMessage(String message) {

		logger.debug(message);
		JSONObject object = null;
		try {

			object = JSONObject.fromObject(message);
			if (object.get("name") != null) {
				String name = object.getString("name");
				String content = null;
				if (object.get("content") != null) {
					content = object.getString("content");
				}
				process(name, content);
			} else {

				logger.error(message + " 没有name属性");
			}
		} catch (Exception e) {
			logger.error(message + " 解析失败");
		}

	}

	private void process(String name, String content) {

		if (!StringHelper.isEmpty(name)) {
			Class<?> clazz = WebSocketListenerCache.CONTEXT.get(name);
			if (clazz != null) {
				try {
					if (WebSocketListener.class.isAssignableFrom(clazz)) {

						WebSocketListener listener = (WebSocketListener) clazz.newInstance();
						listener.init(this);
						listener.onMessage(content);
					}
				} catch (Exception e) {
					logger.error(clazz + " 处理失败");
				}
			} else {

				logger.error(name + " 未配置监听");

			}
		}
	}

	protected void send(String name, String content) {

		JSONObject message = new JSONObject();
		message.put("name", name);
		message.put("content", content);
		super.send(message.toString());
	}

	@Override
	protected void onClose() {

		super.onClose();
	}

	@Override
	protected void onOpen() {

		super.onOpen();
	}

	@Override
	protected void onError(Throwable error) {

		super.onError(error);
	}

}
