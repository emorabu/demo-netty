package com.sumika.netty.socket.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
	/**
	 * 客户端一旦和服务端成功建立连接, 就会创建该类的实例, 并调用该方法
	 */
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline()
				.addLast("LengthFieldBasedFrameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
				.addLast("LengthFieldPrepender", new LengthFieldPrepender(4))
				.addLast("StringDecoder", new StringDecoder(CharsetUtil.UTF_8))
				.addLast("StringEncoder", new StringEncoder(CharsetUtil.UTF_8))
				.addLast("MyServerHandler", new MyServerHandler());
	}

}
