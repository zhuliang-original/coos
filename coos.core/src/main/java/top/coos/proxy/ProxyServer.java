package top.coos.proxy;

import org.slf4j.Logger;

import top.coos.logger.CoreLoggerFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ProxyServer {

	public static ProxyServer server = new ProxyServer();
	Logger logger = CoreLoggerFactory.get();

	public Object lock = new Object();

	private NioEventLoopGroup serverWorkerGroup;

	private NioEventLoopGroup serverBossGroup;

	public Bootstrap realServerBootstrap;
	private NioEventLoopGroup realWorkerGroup;
	public ServerBootstrap serverBootstrap;

	public ProxyServer() {

		realWorkerGroup = new NioEventLoopGroup();
		realServerBootstrap = new Bootstrap();
		realServerBootstrap.group(realWorkerGroup);
		realServerBootstrap.channel(NioSocketChannel.class);
		realServerBootstrap.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			public void initChannel(SocketChannel ch) throws Exception {

				ch.pipeline().addLast(new RealServerChannelHandler());
			}
		});
		serverBossGroup = new NioEventLoopGroup();
		serverWorkerGroup = new NioEventLoopGroup();
		serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(serverBossGroup, serverWorkerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					public void initChannel(SocketChannel ch) throws Exception {

						ch.pipeline().addLast(new ServerChannelHandler(realServerBootstrap));
					}
				});
	}

	public static void main(String[] args) {

		new ProxyServer().startup();

	}

	public void startup() {

		startServer();

	}

	private void startServer() {

		serverBootstrap.bind(88);
	}
}
