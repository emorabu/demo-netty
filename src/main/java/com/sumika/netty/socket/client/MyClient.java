package com.sumika.netty.socket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {
	public static void main(String[] args) throws InterruptedException {
		NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			/* 服务器端可以使用 handler(), 也可以使用 childHandler()
			 * 客户端只能使用 handler()
			 * handler() 针对第一个参数进行处理, 如服务端的 bossGroup 和 这里的 eventLoopGroup, 
			 * 而 childHandler() 针对的是第二个参数, 即服务端的 workerGroup, 客户端没有第二个参数, 因此不能使用 childHandler()
			 */
			bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyClientInitializer());

			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8899).sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}
}
