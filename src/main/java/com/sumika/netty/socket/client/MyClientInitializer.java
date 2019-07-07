package com.sumika.netty.socket.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * 客户端的编解码与服务端的编解码相似
 * @author emora
 *
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel>{
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline()
				.addLast("LengthFieldBasedFrameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
				.addLast("LengthFieldPrepender", new LengthFieldPrepender(4))
				.addLast("StringDecoder", new StringDecoder(CharsetUtil.UTF_8))
				.addLast("StringEncoder", new StringEncoder(CharsetUtil.UTF_8))
				.addLast("MyClientHandler", new MyClientHandler());
	}
}
