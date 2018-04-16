package top.coos.proxy.server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;

import top.coos.logger.LoggerFactory;
import top.coos.proxy.HostMapping;

public class TranslatePort {

	private transient static Logger logger = LoggerFactory.get();

	private static ServerSocket serverSocket;
	private static Map<String, HostMapping> host_map = new HashMap<String, HostMapping>();

	private static void initHostMapping() {

		HostMapping hostMapping = new HostMapping("mysql.local.coos.top", "127.0.0.1", 3306);
		host_map.put(hostMapping.getHost(), hostMapping);
		hostMapping = new HostMapping("web.local.coos.top", "127.0.0.1", 8080);
		host_map.put(hostMapping.getHost(), hostMapping);
	}

	public static void main(String[] args) {

		try {

			initHostMapping();
			serverSocket = new ServerSocket(88);
			while (true) {
				Socket clientSocket = null;
				Socket remoteServerSocket = null;
				try {
					// 获取客户端连接
					clientSocket = serverSocket.accept();
					InetAddress address = clientSocket.getInetAddress();
					String hostName = address.getCanonicalHostName();
					logger.info("accept one client : " + address);
					logger.info("accept one client : " + JSONObject.fromObject(address));
					// 建立远程连接
					HostMapping hostMapping = host_map.get(hostName);
					remoteServerSocket = new Socket(hostMapping.getTarget_host(), hostMapping.getTarget_port());

					logger.info("create remoteip and port success");
					// 启动数据转换接口
					(new TransPortData(clientSocket, remoteServerSocket, "1")).start();
					(new TransPortData(remoteServerSocket, clientSocket, "2")).start();
				} catch (Exception ex) {
					logger.error("", ex);
				}
				// 建立连接远程
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
