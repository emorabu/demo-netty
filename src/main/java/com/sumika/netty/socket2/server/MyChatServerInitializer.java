package com.sumika.netty.socket2.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class MyChatServerInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline()
				.addLast("DelimiterBasedFrameDecoder", new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter())) // 根据分隔符进行解码
				.addLast("StringEncoder", new StringEncoder(CharsetUtil.UTF_8))
				.addLast("StringDecoder", new StringDecoder(CharsetUtil.UTF_8))
				.addLast("MyChatServerHandler", new MyChatServerHandler());
	}

}