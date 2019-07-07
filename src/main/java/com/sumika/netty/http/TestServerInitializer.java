package com.sumika.netty.http;

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
		pipeline.addLast("HttpServerCodec", new HttpServerCodec()) // 针对 HTTP 请求进行编解码， 包括 HttpRequestDecoder 和 HttpResponseEncoder
			.addLast("TestHttpServerHandler", new TestHttpServerHandler());
		
	}
}
