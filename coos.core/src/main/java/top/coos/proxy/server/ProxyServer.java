package top.coos.proxy.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import top.coos.proxy.empty.ServerStatus;
import top.coos.tool.file.FileTool;

public class ProxyServer {

	private final int port;

	private ServerStatus status = ServerStatus.STOP;

	private final Object lock = new Object();

	private ServerSocket serverSocket = null;

	private Socket target_client;

	public ProxyServer(int port) {

		this.port = port;
	}

	public void start() {

		if (status != ServerStatus.START) {
			synchronized (lock) {
				if (status != ServerStatus.START) {
					Thread thread = new Thread() {

						@Override
						public void run() {

							try {
								serverListener();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					};
					thread.start();
					status = ServerStatus.START;
				}
			}
		}

	}

	private void serverListener() throws IOException {

		if (this.serverSocket == null) {
			this.serverSocket = new ServerSocket(this.port);
		}
		while (true) {
			try {

				final Socket client = serverSocket.accept();
				InetAddress inetAddress = client.getInetAddress();
				String ip = inetAddress.getHostAddress();
				System.out.println(ip + "连接中....");
				target_client = new Socket("127.0.0.1", 8080);
				new Thread() {

					@Override
					public void run() {

						try {
							while (true) {
								byte[] client_bytes = FileTool.readBytes(client.getInputStream());
								System.out.println("client_bytes:" + client_bytes.length);
								System.out.println(new String(client_bytes));

								// 读取数据
								target_client.getOutputStream().write(client_bytes);
								target_client.shutdownOutput();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}.start();
				new Thread() {

					@Override
					public void run() {

						try {
							while (true) {

								byte[] target_client_bytes = FileTool.readBytes(target_client.getInputStream());
								System.out.println("target_client_bytes:" + target_client_bytes.length);
								System.out.println(new String(target_client_bytes));
								client.getOutputStream().write(target_client_bytes);
								client.shutdownOutput();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}.start();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {

		ProxyServer server = new ProxyServer(88);
		server.start();
	}
}
