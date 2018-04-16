package top.coos.proxy;

import org.slf4j.Logger;

import top.coos.logger.LoggerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理服务端 channel.
 */
public class RealServerChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {

	Logger logger = LoggerFactory.get();

	public RealServerChannelHandler() {

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

		// 当出现异常就关闭连接
		ctx.close();
	}

	@Override
	protected void channelRead0(final ChannelHandlerContext ctx, final ByteBuf buf) throws Exception {

		logger.info("read ");
		try {
			Channel realServerChannel = ctx.channel();
			Channel channel = realServerChannel.attr(Constants.NEXT_CHANNEL).get();
			if (channel == null) {
				// 代理客户端连接断开
				ctx.channel().close();
			} else {
				byte[] bytes = new byte[buf.readableBytes()];
				buf.readBytes(bytes);
				logger.debug("write data to proxy server, size " + bytes.length);
				ByteBuf contentBuf = ctx.alloc().buffer(bytes.length);
				contentBuf.writeBytes(bytes);

				channel.writeAndFlush(contentBuf);
				logger.debug("write data to proxy server, {}, {}", realServerChannel, channel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		logger.info("channelActive ");

		super.channelActive(ctx);
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