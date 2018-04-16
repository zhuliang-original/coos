package top.coos.proxy;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import top.coos.logger.LoggerFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理服务端 channel.
 */
public class ServerChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {

	Logger logger = LoggerFactory.get();

	private Bootstrap realServerBootstrap;
	private Map<String, HostMapping> host_map = new HashMap<String, HostMapping>();

	private void initHostMapping() {

		HostMapping hostMapping = new HostMapping("mysql.local.coos.top", "127.0.0.1", 3306);
		host_map.put(hostMapping.getHost(), hostMapping);
		hostMapping = new HostMapping("web.local.coos.top", "127.0.0.1", 8080);
		host_map.put(hostMapping.getHost(), hostMapping);
	}

	public ServerChannelHandler(Bootstrap realServerBootstrap) {

		this.realServerBootstrap = realServerBootstrap;
		initHostMapping();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

		// 当出现异常就关闭连接
		ctx.close();
	}

	@Override
	protected void channelRead0(final ChannelHandlerContext ctx, final ByteBuf byteBuf) throws Exception {

		// final byte[] bytes = new byte[buf.readableBytes()];
		final Channel channel = ctx.channel();
		// InetSocketAddress address = (InetSocketAddress)
		// channel.remoteAddress();
		// InetAddress inetAddress = address.getAddress();
		// String canonicalHostName = inetAddress.getCanonicalHostName();
		HostMapping hostMapping = host_map.get("web.local.coos.top");
		// System.out.println(canonicalHostName);
		// System.out.println(JSONObject.fromObject(inetAddress));
		if (hostMapping != null) {

			try {
				realServerBootstrap.connect(hostMapping.getTarget_host(), hostMapping.getTarget_port()).addListener(
						new ChannelFutureListener() {

							@Override
							public void operationComplete(ChannelFuture future) throws Exception {

								try {
									logger.info("connect " + future.isSuccess());
									// 连接后端服务器成功
									if (future.isSuccess()) {
										// final Channel realServerChannel =
										// future.channel();
										final Channel realServerChannel = future.channel();
										logger.info("connect realserver success, realServerChannel" + realServerChannel);
										logger.info("connect realserver success, serverChannel" + channel);

										realServerChannel.config().setOption(ChannelOption.AUTO_READ, false);
										realServerChannel.attr(Constants.NEXT_CHANNEL).set(channel);
										realServerChannel.config().setOption(ChannelOption.AUTO_READ, true);
										// ByteBuf contentBuf =
										// realServerChannel.alloc().buffer(bytes.length);
										// contentBuf.writeBytes(bytes);
										realServerChannel.writeAndFlush(byteBuf);
									} else {
										System.out.println("失败");
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

						});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void channelActive(final ChannelHandlerContext context) throws Exception {

		logger.info("channelActive ");
		// final Channel channel = context.channel();
		// InetSocketAddress address = (InetSocketAddress)
		// channel.remoteAddress();
		// InetAddress inetAddress = address.getAddress();
		// String hostName = inetAddress.getHostName();
		// HostMapping hostMapping = host_map.get(hostName);
		// System.out.println(hostName);
		// System.out.println(JSONObject.fromObject(address));
		// System.out.println(JSONObject.fromObject(inetAddress));
		// SocketAddress remoteAddress = new
		// InetSocketAddress(hostMapping.getTarget_host(),
		// hostMapping.getTarget_port());
		// // SocketAddress localAddress = new InetSocketAddress(hostName, 888);
		// if (hostMapping != null) {
		// System.out.println(JSONObject.fromObject(hostMapping));
		// realServerBootstrap.connect(remoteAddress, null).addListener(
		// new ChannelFutureListener() {
		//
		// @Override
		// public void operationComplete(ChannelFuture future) throws Exception
		// {
		//
		// try {
		// logger.info("connect " + future.isSuccess());
		// // 连接后端服务器成功
		// if (future.isSuccess()) {
		// // final Channel realServerChannel =
		// // future.channel();
		// final Channel realServerChannel = future.channel();
		// logger.debug("connect realserver success, {}", realServerChannel);
		// realServerChannel.config().setOption(ChannelOption.AUTO_READ,
		// false);
		// realServerChannel.attr(Constants.NEXT_CHANNEL).set(channel);
		// realServerChannel.config().setOption(ChannelOption.AUTO_READ,
		// true);
		// realServerChannel.writeAndFlush(channel.alloc());
		// } else {
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		//
		// });
		// }
		super.channelActive(context);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

		logger.info("channelInactive ");
		// 通知代理客户端
		// Channel userChannel = ctx.channel();
		// InetSocketAddress sa = (InetSocketAddress)
		// userChannel.localAddress();
		// System.out.println("channelActive:" + sa);

		super.channelInactive(ctx);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {

		logger.info("channelWritabilityChanged ");
		// 通知代理客户端
		// Channel userChannel = ctx.channel();
		// InetSocketAddress sa = (InetSocketAddress)
		// userChannel.localAddress();
		// System.out.println("channelActive:" + sa);

		super.channelWritabilityChanged(ctx);
	}

}