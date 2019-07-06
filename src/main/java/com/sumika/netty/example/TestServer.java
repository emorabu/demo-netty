package com.sumika.netty.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {
	/**
	 * 启动该类, curl 'http://localhost:8899' 返回 hello world
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// 定义两个线程组(基于NIO的事件循环组)
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {

			ServerBootstrap serverBootstrap = new ServerBootstrap(); // 用于启动服务端
			serverBootstrap.group(bossGroup, workerGroup) // 第一个参数用于接收请求, 第二个参数用于处理请求
					.channel(NioServerSocketChannel.class) // 反射创建实例
					.childHandler(new TestServerInitializer()); // 用户自己定义的子处理器
			
			ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			// 优雅关闭
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
