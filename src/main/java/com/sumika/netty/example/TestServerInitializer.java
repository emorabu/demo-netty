package com.sumika.netty.example;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
	/**
	 * 这是一个回调方法
	 */
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline(); // 管道中会有很多 ChannelHandler
		pipeline.addLast("HttpServerCodec", new HttpServerCodec())
			.addLast("TestHttpServerHandler", new TestHttpServerHandler());
		
	}
}
